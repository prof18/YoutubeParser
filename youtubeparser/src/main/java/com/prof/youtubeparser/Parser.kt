package com.prof.youtubeparser

class Parser {

    private lateinit var onComplete: OnTaskCompleted

    /**
     * This method generates the url that retrieves Youtube video data
     *
     * @param channelID The ID of the desired channel. Ex: https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg
     * channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
     * @param maxResult The number of video to get
     * @param key       Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @return The url required to get data
     */
    @Deprecated(
            message = "This method is deprecated. Please use the new version that allows to choose the type of order of the videos: {@link #generateRequest(String, int, int, String)}",
            replaceWith = ReplaceWith("generateRequest(channelID, maxResult, ORDER_DATE ,key)", "com.prof.youtubeparser.Parser.Companion.ORDER_DATE")
    )
    fun generateRequest(channelID: String, maxResult: Int, key: String): String {
        return "$BASE_ADDRESS$channelID&maxResults=$maxResult&order=date&key=$key"
    }

    /**
     * This method generates the url to retrieve Youtube Video data
     *
     * @param channelID The ID of the desired channel. Ex: https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg
     * channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
     * @param maxResult The number of video to get. The maximum value is 50
     * @param orderType The type of ordering. You can choose an order by date: [.ORDER_DATE] and by view count [.ORDER_VIEW_COUNT].
     * @param key       Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @return The url required to get data
     */
    fun generateRequest(channelID: String, maxResult: Int, orderType: Int, key: String): String {
        val order =
                when (orderType) {
                    ORDER_DATE -> "date"
                    ORDER_VIEW_COUNT -> "viewcount"
                    else -> ""
                }
        return "$BASE_ADDRESS$channelID&maxResults=$maxResult&order=$order&key=$key"
    }

    /**
     * This method generates the url to retrieve more video data. Remember that first you have to call
     * [.generateRequest] to get the next page token
     *
     * @param channelID The ID of the desired channel. Ex: https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg
     * channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
     * @param maxResult The number of video to get. The maximum value is 50
     * @param orderType The type of ordering. You can choose an order by date: [.ORDER_DATE] and by view count [.ORDER_VIEW_COUNT].
     * @param key       Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @param nextToken The token necessary to load more data
     * @return The url required to get more data
     */
    fun generateMoreDataRequest(channelID: String, maxResult: Int, orderType: Int, key: String, nextToken: String): String {
        val urlString = "https://www.googleapis.com/youtube/v3/search?pageToken="
        val order =
                when (orderType) {
                    ORDER_DATE -> "date"
                    ORDER_VIEW_COUNT -> "viewcount"
                    else -> ""
                }
        return "$urlString$nextToken&part=snippet&channelId=$channelID&maxResults=$maxResult&order=$order&key=$key"
    }

    fun onFinish(onComplete: OnTaskCompleted) {
        this.onComplete = onComplete
    }

    // TODO: java compatibility
    fun execute() {

    }

    // TODO: java method

    companion object {
        const val BASE_ADDRESS = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId="
        const val ORDER_DATE = 1
        const val ORDER_VIEW_COUNT = 2
    }
}