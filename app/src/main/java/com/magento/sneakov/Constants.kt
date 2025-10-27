package com.magento.sneakov

import java.text.NumberFormat
import java.util.Locale

object Constants {
    const val DOMAIN_NAME = "https://overcontented-berniece-congressionally.ngrok-free.dev"
    const val API_PATH = "/rest/default/V1/"
    const val MEDIA_PRODUCT_PATH = "/pub/media/catalog/product"
    const val MEDIA_CATEGORY_PATH = "/pub/media/catalog/category"
    //const val CATEGORY_URL = DOMAIN_NAME + MEDIA_CATEGORY_PATH
    const val IMAGE_URL = DOMAIN_NAME + MEDIA_PRODUCT_PATH
    const val BASE_URL = DOMAIN_NAME + API_PATH
}

fun formatMoney(amount: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"))
    return formatter.format(amount)
}