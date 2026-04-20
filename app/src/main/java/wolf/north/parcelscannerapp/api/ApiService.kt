package wolf.north.parcelscannerapp.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import wolf.north.parcelscannerapp.mvvm.model.files.Form
import wolf.north.parcelscannerapp.mvvm.model.files.Package


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
}