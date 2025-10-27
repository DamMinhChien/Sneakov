package com.magento.sneakov.data.remote.api

import com.magento.sneakov.data.remote.dto.CategoryDto
import com.magento.sneakov.data.remote.dto.ChildProductDto
import com.magento.sneakov.data.remote.dto.LoginRequestDto
import com.magento.sneakov.data.remote.dto.ProductDto
import com.magento.sneakov.data.remote.dto.RegisterRequestDto
import com.magento.sneakov.data.remote.dto.SearchRespondDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface SneakovApiService {
    @POST("integration/customer/token")
    suspend fun login(@Body request: LoginRequestDto) : Response<String>
    @POST("customers")
    suspend fun register(@Body request: RegisterRequestDto)

    @GET("products")
    suspend fun  searchProducts(@QueryMap(encoded = true) query: Map<String, String>): SearchRespondDto
    @GET("/products/{sku}")
    suspend fun getProductById(
        @Path("sku") sku: String
    ): ProductDto

    @GET("configurable-products/{sku}/children")
    suspend fun getChildProducts(
        @Path("sku") sku: String
    ): List<ChildProductDto>

    @GET("categories")
    suspend fun getCategoryTree(): CategoryDto

    @GET("categories/{id}")
    suspend fun getCategoryDetail(@Path("id") id: Int): CategoryDto

}