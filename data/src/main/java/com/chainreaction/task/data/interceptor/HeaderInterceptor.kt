package com.chainreaction.task.data.interceptor

import com.chainreaction.task.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader(HEADER_AUTHORIZATION, getAuthorization())
            .build()
        return chain.proceed(request)
    }

    private fun getAuthorization() = "Bearer ${BuildConfig.MOVIE_API_KEY}"

    companion object{
        const val HEADER_AUTHORIZATION = "Authorization"
    }
}