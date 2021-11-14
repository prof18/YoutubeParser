package com.prof.youtubeparser

object FakeDataFactory {

    val listResponse = """
        {
          "kind": "youtube#searchListResponse",
          "etag": "jQfvHzWTvDXgowxV9WgA22pJIoM",
          "nextPageToken": "CAIQAA",
          "regionCode": "DE",
          "pageInfo": {
            "totalResults": 1694,
            "resultsPerPage": 2
          },
          "items": [
            {
              "kind": "youtube#searchResult",
              "etag": "Ocm0Jzs7tNzkItSFjDq3et6YkTM",
              "id": {
                "kind": "youtube#video",
                "videoId": "8i6vrlbIVCc"
              },
              "snippet": {
                "publishedAt": "2021-11-11T18:56:09Z",
                "channelId": "UCVHFbqXqoYvEWM1Ddxl0QDg",
                "title": "Paging: Live Q&amp;A - MAD Skills",
                "description": "Welcome to the live Q&A for the Paging series for MAD Skills, hosted by Android Developer Relations Engineer Florina Muntenescu. This time, Florina is joined ...",
                "thumbnails": {
                  "default": {
                    "url": "https://i.ytimg.com/vi/8i6vrlbIVCc/default.jpg",
                    "width": 120,
                    "height": 90
                  },
                  "medium": {
                    "url": "https://i.ytimg.com/vi/8i6vrlbIVCc/mqdefault.jpg",
                    "width": 320,
                    "height": 180
                  },
                  "high": {
                    "url": "https://i.ytimg.com/vi/8i6vrlbIVCc/hqdefault.jpg",
                    "width": 480,
                    "height": 360
                  }
                },
                "channelTitle": "Android Developers",
                "liveBroadcastContent": "none",
                "publishTime": "2021-11-11T18:56:09Z"
              }
            },
            {
              "kind": "youtube#searchResult",
              "etag": "Fb9a78wWkrw73rRencla_Lsp1Nc",
              "id": {
                "kind": "youtube#video",
                "videoId": "cR3e_dhy-sQ"
              },
              "snippet": {
                "publishedAt": "2021-11-11T00:00:11Z",
                "channelId": "UCVHFbqXqoYvEWM1Ddxl0QDg",
                "title": "Now in Android: 51 - Android Developer Summit 2021 recap (part 2)",
                "description": "Welcome to Now in Android, your ongoing guide to what's new and notable in the world of Android development. Today, we're covering our second recap from ...",
                "thumbnails": {
                  "default": {
                    "url": "https://i.ytimg.com/vi/cR3e_dhy-sQ/default.jpg",
                    "width": 120,
                    "height": 90
                  },
                  "medium": {
                    "url": "https://i.ytimg.com/vi/cR3e_dhy-sQ/mqdefault.jpg",
                    "width": 320,
                    "height": 180
                  },
                  "high": {
                    "url": "https://i.ytimg.com/vi/cR3e_dhy-sQ/hqdefault.jpg",
                    "width": 480,
                    "height": 360
                  }
                },
                "channelTitle": "Android Developers",
                "liveBroadcastContent": "none",
                "publishTime": "2021-11-11T00:00:11Z"
              }
            }
          ]
        }
    """.trimIndent()

    val statsResponse = """
        {
          "kind": "youtube#videoListResponse",
          "etag": "89tsWMmv_c7IWgg6vqs30MPZ-2c",
          "items": [
            {
              "kind": "youtube#video",
              "etag": "mNsuEWPHxE5_2MGWnuhg27GL7bY",
              "id": "cR3e_dhy-sQ",
              "statistics": {
                "viewCount": "3007",
                "likeCount": "96",
                "dislikeCount": "2",
                "favoriteCount": "0",
                "commentCount": "2"
              }
            }
          ],
          "pageInfo": {
            "totalResults": 1,
            "resultsPerPage": 1
          }
        }
    """.trimIndent()

}