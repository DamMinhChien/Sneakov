package com.magento.sneakov.data.mappper

import com.magento.sneakov.data.remote.dto.ProductDto
import com.magento.sneakov.domain.model.Product

fun ProductDto.toDomain(): Product {
    val imageUrl = custom_attributes
        ?.find { it.attribute_code == "image" }
        ?.value
        ?.let {
            when (it) {
                is String -> it
                is List<*> -> it.firstOrNull()?.toString() // lấy phần tử đầu nếu là list
                else -> null
            }
        }

    return Product(
        name = name,
        id = id,
        price = price,
        sku = sku,
        imageUrl = imageUrl
    )
}
