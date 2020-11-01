package com.pradip.core.networking

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.pradip.core.exceptions.ApiException
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class BaseResponseConverterFactory(
    private val gsonConverterFactory: GsonConverterFactory
) : Converter.Factory() {

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return gsonConverterFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, Any?>? {
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
            override fun getOwnerType(): Type? = null
            override fun getRawType(): Type = JsonObject::class.java
        }
        val gsonConverter: Converter<ResponseBody, *>? =
            gsonConverterFactory.responseBodyConverter(wrappedType, annotations, retrofit)
        return ResponseBodyConverter(gsonConverter as Converter<ResponseBody, JsonObject>)
    }

    class ResponseBodyConverter<T>(
        private val converter: Converter<ResponseBody, JsonObject>
    ) : Converter<ResponseBody, T> {

        override fun convert(value: ResponseBody): T? {
            val response = converter.convert(value)
            response?.let {
                if (!it.isJsonNull) {
                    return it as T
                } else
                    throw ApiException("Unexpected error occurred", "failure")
            }
            throw IOException("Error converting ResponseBody to BaseResponse<T>: response is null")
        }
    }
}