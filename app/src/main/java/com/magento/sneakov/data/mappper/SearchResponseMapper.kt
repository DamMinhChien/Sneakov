package com.magento.sneakov.data.mappper

import com.magento.sneakov.data.remote.dto.SearchRespondDto
import com.magento.sneakov.domain.model.SearchRespond

fun SearchRespondDto.toDomain(): SearchRespond{
    return SearchRespond(totalCount = total_count, items = items.map { it.toDomain() })
}