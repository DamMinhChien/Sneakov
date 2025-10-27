package com.magento.sneakov.domain.model

data class SearchRequest(
    val filters: List<SearchFilter> = emptyList(),
    val sorts: List<SearchSort> = emptyList(),
    val currentPage: Int = 1,
    val pageSize: Int = 20,
    val fields: String? = null
)

data class SearchFilter(
    val field: String,
    val value: String,
    val conditionType: String = "eq" // eq, like, gt, lt, in, etc.
)

data class SearchSort(
    val field: String,
    val direction: String = "ASC" // ASC hoặc DESC
)