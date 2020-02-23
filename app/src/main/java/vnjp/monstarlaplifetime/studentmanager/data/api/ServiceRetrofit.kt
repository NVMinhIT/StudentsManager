package vnjp.monstarlaplifetime.studentmanager.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ServiceRetrofit {

    companion object {

        @Volatile
        private var INSTANCE: ApiService? = null

        private const val BASE_URL = "http://app11.lifetimetech.vn/student/api/"
    }

    private val gson = GsonBuilder()
        .setLenient()
        .create()
    private val httpLoggingInterceptor = run {
        val httpLoggingInterceptor1 = HttpLoggingInterceptor()
        httpLoggingInterceptor1.apply {
            httpLoggingInterceptor1.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .readTimeout(10000, TimeUnit.MILLISECONDS)
        .writeTimeout(10000, TimeUnit.MILLISECONDS)
        .connectTimeout(10000, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        .protocols(listOf(Protocol.HTTP_1_1))
        .addInterceptor(httpLoggingInterceptor)
        .build()


    fun getService(): ApiService {
        return INSTANCE ?: synchronized(this) {
            val instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
            INSTANCE = instance
            instance
        }
    }

}