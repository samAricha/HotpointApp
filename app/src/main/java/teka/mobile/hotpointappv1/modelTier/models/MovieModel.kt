package teka.mobile.hotpointappv1.modelTier.models

data class MovieModel(
    val page: Int,
    val results: List<MovieModelItem>,
    val total_pages: Int,
    val total_results: Int,


)