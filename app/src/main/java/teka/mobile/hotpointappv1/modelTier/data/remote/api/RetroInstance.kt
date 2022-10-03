package teka.mobile.hotpointappv1.modelTier.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import teka.mobile.hotpointappv1.utils.Credentials

class RetroInstance {

    companion object{
        //https://api.themoviedb.org/3/trending/all/day?api_key=f301ed61db69469574a304ade92a8551
        //val BASE_URL = "https://api.themoviedb.org/3/"

        //returns an instance of retrofit with the base url
        fun getRetrofit(): RetroServiceInterface{
            return Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetroServiceInterface::class.java)
        }
    }
}