package teka.mobile.hotpointappv1.viewTier

import teka.mobile.hotpointappv1.modelTier.models.MovieModelItem

interface MovieListInterface {
    fun onMoviesListAdded():List<MovieModelItem>

}