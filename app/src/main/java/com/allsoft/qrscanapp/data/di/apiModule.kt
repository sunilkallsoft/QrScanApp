package com.allsoft.qrscanapp.data.di

import android.content.Context
import android.util.Log
import com.allsoft.qrscanapp.Urls
import com.allsoft.qrscanapp.data.api.AuthApi
import com.allsoft.qrscanapp.data.api.MasterApi
import com.allsoft.qrscanapp.utils.AppConstants
import com.allsoft.qrscanapp.utils.AppPreferences
import com.allsoft.qrscanapp.utils.ResponseHandler
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule  = module {
    factory { AuthInterceptor(get()) }
    factory { NetworkInterceptor(get()) }
    factory { provideOkHttpClient(get(), get(),get()) }

    single { provideBaseRetrofit(get()) }

    single { ResponseHandler() }

    single { provideBaseRetrofit(get()).create(MasterApi::class.java) }
    single { provideBaseRetrofit(get()).create(AuthApi::class.java) }
}


/**
 * Use the [providerGSON] to provide GSON
 * @return GSONBuilder*/
fun providerGSON(): Gson =
    GsonBuilder()
        .setLenient()
        .serializeNulls()
        .create()

/**
 * Use the [provideInterceptor] to provide a HttpLoggingInterceptor
 * @return HttpLoggingInterceptor*/
fun provideInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    return interceptor
}

class AuthInterceptor(sharedPrefs : AppPreferences) : Interceptor {

    private var sharedPrefsLocal: AppPreferences = sharedPrefs

    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url = req.url



        Log.d("urlAttacked1",url.toString());


        val accessToken = sharedPrefsLocal.getString(AppConstants.KEY_ACCESS_TOKEN, "")

//        val otpToken = sharedPrefsLocal.otpToken


        req = if (accessToken != "") {
            req.newBuilder()
                .header("token", accessToken)
                .url(url).build()
        } else {

            req.newBuilder().url(url).build()


        }


        val response = chain.proceed(req)


        try {
//            Timber.d("response: %s", response.peekBody(2048).string())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }
}






class NetworkInterceptor(sharedPrefs : AppPreferences) : Interceptor {

    private var sharedPrefsLocal: AppPreferences = sharedPrefs
    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request());
        val otpToken = response.headers["otp-token"]

        val accessToken = response.headers["token"]

        if(otpToken!=null){


            sharedPrefsLocal.putString(AppConstants.OTP_TOKEN, otpToken)
        }

        if(accessToken!=null){

            sharedPrefsLocal.putString(AppConstants.OTP_TOKEN, accessToken)

            sharedPrefsLocal.putString(AppConstants.KEY_ACCESS_TOKEN, accessToken)
        }


        return response
    }


}






/**
 * Use the [provide Client] to provide a OkHttpClient
 * @return OkHttpClient*/
fun provideOkHttpClient(appContext: Context,
                        authInterceptor: AuthInterceptor, networkInterceptor: NetworkInterceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(provideInterceptor())
        .addInterceptor(networkInterceptor)
//        .addInterceptor(ChuckerInterceptor(appContext))
        .connectTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)

//        .authenticator(TokenAuthenticator())
        .build()

/**
 * Use the [provideBaseRetrofit] to provide a Retrofit with WITH_BASE_URL instance
 * @return Retrofit*/
fun provideBaseRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(Urls.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(providerGSON()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()