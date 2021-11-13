/*
 *   Copyright 2019 Marco Gomiero
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.prof.youtubeparser.sample.kotlin

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: VideoAdapter
    private val viewModel: MainViewModel by viewModels()
    private val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val progressBar = findViewById<ContentLoadingProgressBar>(R.id.progressBar)
        val swipeLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_layout)
        val rootLayout = findViewById<RelativeLayout>(R.id.root_layout)
        val list = findViewById<RecyclerView>(R.id.list)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        setSupportActionBar(toolbar)

        viewModel.videoList.observe(this, { videos ->
            videos?.let {
                adapter.items = videos
                adapter.notifyDataSetChanged()
                progressBar.hide()
                swipeLayout.isRefreshing = false
            }
        })

        viewModel.stats.observe(this, { stats ->
            stats?.let {
                val body = "Views: " + stats.viewCount + "\n" +
                        "Like: " + stats.likeCount + "\n" +
                        "Dislike: " + stats.dislikeCount + "\n" +
                        "Number of comment: " + stats.commentCount + "\n" +
                        "Number of favourite: " + stats.favoriteCount

                val dialogBuilder = AlertDialog.Builder(this@MainActivity)
                dialogBuilder.setTitle("Stats")
                dialogBuilder.setMessage(body)
                dialogBuilder.setCancelable(false)
                dialogBuilder.setNegativeButton(android.R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                dialogBuilder.show()
            }
        })

        viewModel.snackbar.observe(this, { value ->
            value?.let {
                Snackbar.make(rootLayout, value, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShowed()
            }
        })

        adapter = VideoAdapter(mutableListOf(), this@MainActivity)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter

        swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
        swipeLayout.canChildScrollUp()
        swipeLayout.setOnRefreshListener {
            adapter.items.clear()
            adapter.notifyDataSetChanged()
            swipeLayout.isRefreshing = true
            viewModel.fetchVideos()
        }

        //show the fab on the bottom of recycler view
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val lastVisible = layoutManager!!.findLastVisibleItemPosition()

                if (lastVisible == adapter.itemCount - 1) {
                    fab.show()
                } else {
                    fab.hide()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        fab.setOnClickListener {
            fab.hide()
            viewModel.fetchMoreVideos()
        }

        if (!isNetworkAvailable) {

            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.alert_message)
                    .setTitle(R.string.alert_title)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_positive
                    ) { _, _ -> finish() }

            val alert = builder.create()
            alert.show()

        } else if (isNetworkAvailable) {
            viewModel.fetchVideos()
        }
    }

    fun getVideoStats(videoId: String) {
        viewModel.fetchStatistics(videoId)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.action_settings) {
            val alertDialog = AlertDialog.Builder(this@MainActivity).create()
            alertDialog.setTitle(R.string.app_name)
            alertDialog.setMessage(Html.fromHtml(Html.fromHtml(this@MainActivity.getString(R.string.info_text) +
                    " <a href='http://github.com/prof18/YoutubeParser'>GitHub.</a>" +
                    this@MainActivity.getString(R.string.author)).toString()))
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()

            (alertDialog.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()
        }

        return super.onOptionsItemSelected(item)
    }
}
