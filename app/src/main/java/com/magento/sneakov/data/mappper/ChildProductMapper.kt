package com.magento.sneakov.data.mappper

import com.magento.sneakov.data.remote.dto.ChildProductDto
import com.magento.sneakov.domain.model.ProductVariant

fun ChildProductDto.toDomain(): ProductVariant {
    val attrs = custom_attributes?.associate { it.attribute_code to it.value.toString() } ?: emptyMap()

    return ProductVariant(
        id = id,
        sku = sku,
        name = name ?: "",
        price = price ?: 0.0,
        imageUrl = media_gallery_entries?.firstOrNull()?.file ?: "",
        size = attrs["size"],
        color = attrs["colour"]
    )
}