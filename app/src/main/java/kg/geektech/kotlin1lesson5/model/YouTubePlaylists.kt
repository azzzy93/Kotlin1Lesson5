package kg.geektech.kotlin1lesson5.model

data class YouTubePlaylists(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val prevPageToken: String,
    val pageInfo: PageInfo
)