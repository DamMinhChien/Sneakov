package com.magento.sneakov.domain.model

class SearchRequestBuilder {
    private val filters = mutableListOf<SearchFilter>()
    private val sorts = mutableListOf<SearchSort>()
    private var currentPage = 1
    private var pageSize = 20

    fun filter(field: String, value: String, conditionType: String = "eq") = apply {
        filters.add(SearchFilter(field, value, conditionType))
    }

    fun sort(field: String, direction: String = "ASC") = apply {
        sorts.add(SearchSort(field, direction))
    }

    fun page(page: Int, size: Int) = apply {
        currentPage = page
        pageSize = size
    }

    fun build(): SearchRequest{
        return SearchRequest(filters, sorts, currentPage, pageSize)
    }
}