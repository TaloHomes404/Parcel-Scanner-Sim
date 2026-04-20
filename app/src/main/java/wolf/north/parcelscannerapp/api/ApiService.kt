package wolf.north.parcelscannerapp.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import wolf.north.parcelscannerapp.mvvm.model.files.Form
import wolf.north.parcelscannerapp.mvvm.model.files.Package
import wolf.north.parcelscannerapp.mvvm.model.login.LoginRequest
import wolf.north.parcelscannerapp.mvvm.model.login.LoginResponse
import wolf.north.parcelscannerapp.mvvm.model.stats.UserStatsResponse


interface LogisticsApiService {

    // GET Packages List Api Call
    @GET("history/packages")
    suspend fun getPackages(): Response<List<Package>>

    // Get Forms List Api Call
    @GET("history/forms")
    suspend fun getForms(): Response<List<Form>>

    // POST - Sending scanned packages into Api
    @POST("history/packages")
    suspend fun savePackage(@Body packageData: Package): Response<Package>

    // POST - Sending scanned forms into Api
    @POST("history/forms")
    suspend fun saveForm(@Body form: Form): Response<Form>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("user/stats")
    suspend fun getUserStats(): Response<UserStatsResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>


}