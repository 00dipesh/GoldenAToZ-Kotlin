package com.goldendigitech.goldenatoz.serverConnect

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import okhttp3.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object Constant {
    private const val WEB_API_BASE_URL = "http://192.168.29.12:60302/"

    private const val TIMEOUT_SECONDS = 30
    private var retrofit: WebService? = null

    fun getClient(): WebService {
        if (retrofit == null) {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .addInterceptor(HeaderInterceptor()) // Adding custom header interceptor if needed
                .build()

            retrofit = Retrofit.Builder() // Retrofit client.
                .baseUrl(WEB_API_BASE_URL) // Base domain URL.
                .addConverterFactory(GsonConverterFactory.create()) // Added converter factory.
                .client(okHttpClient)
                .build() // Build client.
                .create(WebService::class.java)
        }
        return retrofit!!
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        if (connectivityManager != null) {

                val network = connectivityManager.activeNetwork
                if (network != null) {
                    val capabilities = connectivityManager.getNetworkCapabilities(network)
                    return capabilities != null &&
                            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                }
             else {
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            }
        }
        return false
    }

    private class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest: Request = chain.request()
            // Add headers if needed
            val newRequest: Request = originalRequest.newBuilder()
                .header("Authorization", "Bearer YourTokenHere")
                .build()
            return chain.proceed(newRequest)
        }
    }
}