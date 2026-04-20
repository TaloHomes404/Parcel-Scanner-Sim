package wolf.north.parcelscannerapp.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import wolf.north.parcelscannerapp.repository.UserSessionRepository
import java.util.concurrent.TimeUnit
import kotlin.getValue

object RetrofitInstance {

    // our api key
    private const val BASE_URL = "API_KEY"

    // OkHttp loggin implementation
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = Interceptor { chain ->
       val user = UserSessionRepository.currentUser.value // get current user data
       val requestBuilder = chain.request().newBuilder()

        if (user != null) {
            requestBuilder.addHeader("X-User-ID", user.id)
        }

        chain.proceed(requestBuilder.build())
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: LogisticsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LogisticsApiService::class.java)
    }
}