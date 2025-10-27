package com.magento.sneakov.data.mappper

import android.util.Log
import com.magento.sneakov.Constants
import com.magento.sneakov.data.remote.dto.CategoryDto
import com.magento.sneakov.domain.model.Category

fun CategoryDto.toDomain(): Category{
    val baseMediaUrl = Constants.DOMAIN_NAME
    Log.d("abc", baseMediaUrl)
    val imageUrl = custom_attributes
        ?.find { it.attribute_code == "image" }
        ?.value
        ?.let { baseMediaUrl + it }

    return Category(
        id = id,
        name = name?: "",
        imageUrl = imageUrl
    )
}