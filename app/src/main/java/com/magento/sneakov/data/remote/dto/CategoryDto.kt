package com.magento.sneakov.data.remote.dto

data class CategoryDto(
    val id: Int,
    val parent_id: Int?,
    val name: String?,
    val level: Int?,
    val children_data: List<CategoryDto>?,
    val custom_attributes: List<CustomAttributeCategoryDto>?
)

data class CustomAttributeCategoryDto(
    val attribute_code: String,
    val value: String?
)
