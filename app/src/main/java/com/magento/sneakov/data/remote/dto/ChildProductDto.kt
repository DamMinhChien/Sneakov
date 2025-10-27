package com.magento.sneakov.data.remote.dto

data class ChildProductDto(
    val id: Int,
    val sku: String,
    val name: String?,
    val price: Double?,
    val media_gallery_entries: List<MediaEntryDto>?,
    val custom_attributes: List<CustomAttributeDto>?
)

data class MediaEntryDto(
    val file: String
)

data class CustomAttributeDto(
    val attribute_code: String,
    val value: Any
)
