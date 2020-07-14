package com.marie.mutinga.kyetting.api


import com.litecode.synchroniseurapp.models.Constracts
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface PubService {

    @FormUrlEncoded
    @POST("/api/v1/pub")
    fun sendDataOrSync(
        @Field("title")title: String,
        @Field("content")content: String): Call<PubModels>

    /*
    @FormUrlEncoded
    @POST("/api/v1/pub/search/")
    fun sendRequestSearching(@Field("query")query: String): Call<List<PubModels>>

    @GET("/api/v1/pub/search/best/")
    fun getBestPub(): Call<List<PubModels>>

    @GET("/api/v1/pub/detail/{pub_id}/")
    fun getDetailsAttached(@Path("pub_id") pub_id: String): Call<List<DetailModels>>

    @FormUrlEncoded
    @POST("/api/v1/comment/add/")
    fun sendComentsToServer(
        @Field("user")user: String,
        @Field("pub")pub: String,
        @Field("comment")comment: String): Call<DetailModels>


    @FormUrlEncoded
    @POST("/api/v1/comment/like/")
    fun sendLikeComments(
        @Field("like_author")like_author: String,
        @Field("like_comment")like_comment: String): Call<LikeModels>


    @GET("/api/v1/response/detail/{comment_id}/")
    fun getAllResponseAttached(@Path("comment_id") comment_id: String): Call<List<ResponseModels>>


    @FormUrlEncoded
    @POST("/api/v1/response/add/")
    fun sendResponseToServer(
        @Field("user")user: String,
        @Field("comment")comment: String,
        @Field("response")response: String): Call<ResponseModels>

    */
    companion object {
        fun create() : PubService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constracts.baseUrl)
                .build()
            return retrofit.create(PubService::class.java)

        }
    }
}