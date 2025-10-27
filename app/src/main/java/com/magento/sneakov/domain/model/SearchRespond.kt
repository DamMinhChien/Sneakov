package com.magento.sneakov.domain.model

data class SearchRespond(
    val totalCount: Int,
    val items: List<Product>
)