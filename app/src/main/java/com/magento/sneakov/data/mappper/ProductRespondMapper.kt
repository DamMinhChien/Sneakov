package com.magento.sneakov.data.mappper

import com.magento.sneakov.Constants
import com.magento.sneakov.data.remote.dto.ProductDto
import com.magento.sneakov.domain.model.Product

fun ProductDto.toDomain(): Product {
    val baseMedia = Constants.IMAGE_URL
    val imageUrl = custom_attributes
        ?.find { it.attribute_code == "image" }
        ?.value
        ?.let {
            when (it) {
                is String -> baseMedia + it
                is List<*> -> baseMedia + it.firstOrNull()?.toString() // lấy phần tử đầu nếu là list
                else -> null
            }
        }

    val childIds = extension_attributes?.configurable_product_links ?: emptyList()

    return Product(
        name = name,
        id = id,
        price = price,
        sku = sku,
        imageUrl = imageUrl,
        childIds = childIds,
    )
}
