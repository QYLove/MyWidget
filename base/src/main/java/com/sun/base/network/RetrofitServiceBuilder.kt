package com.sun.base.network

import com.sun.base.base.BaseApi
import com.sun.base.expand.getToken
import com.sun.base.expand.logD
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServiceBuilder {

    //初始化缓存个数
    private const val INIT_SIZE = 5

    //存储创建的对象
    var httpHashMap = object : LinkedHashMap<String,Any>(INIT_SIZE,1f,true){
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, Any>?): Boolean {
            return size > INIT_SIZE
        }
    }

    fun <T> createService(
        clazz: Class<T>,
        baseApi:String = BaseApi.MAIN_API
    ):T?{
        //网络未连接 情况处理

        //若缓存中有去除缓存
        if (httpHashMap.containsKey(clazz.name)){
            return httpHashMap[clazz.name] as T
        }

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseApi)
            .client(okClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(clazz)
        httpHashMap[clazz.name] = service as Any
        return retrofit.create(clazz)
    }

    private val okClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        val log = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                message.logD()
            }
        })
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request = chain.request().newBuilder().addHeader(
                    "Authorization",
                    getToken()
                ).build()
                chain.proceed(request)
            }
            .addInterceptor(log.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

}