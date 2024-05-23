package kedaiapps.projeku.testandroidkedatech.services.rest

import kedaiapps.projeku.testandroidkedatech.services.Response
import kedaiapps.projeku.testandroidkedatech.services.entity.*
import retrofit2.http.*

interface MainRest {

    //home
    @GET("games")
    suspend fun home(
        @Query("key") key: String,
    ) : Response<List<ResponseHome>>

    //home
    @GET("games")
    suspend fun paggination(
        @Query("page") page: Int,
        @Query("page_size") page_size: Int,
        @Query("search") search: String,
        @Query("key") key: String,
    ) : Response<List<ResponseHome>>

    //search
    @GET("games")
    suspend fun search(
        @Query("search") search: String,
        @Query("key") key: String,
    ) : Response<List<ResponseHome>>

    //search
    @GET("games/{id}")
    suspend fun homeDetail(
        @Path("id") id: String,
        @Query("key") key: String,
    ) : ResponseHomeDetail
}