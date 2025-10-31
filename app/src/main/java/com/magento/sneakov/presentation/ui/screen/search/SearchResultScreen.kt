package com.magento.sneakov.presentation.ui.screen.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.magento.sneakov.domain.model.Product
import com.magento.sneakov.presentation.ui.compose.ProductCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchResultScreen(
    keyword: String,
    sortField: String = "created_at",
    sortDirection: String = "DESC",
    page: Int = 1,
    pageSize: Int = 20,
    onProductClick: (Product) -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    /**
     * ✅ Gọi API khi các tham số thay đổi (keyword, sortField, sortDirection, page, pageSize)
     */
    LaunchedEffect(keyword, sortField, sortDirection, page, pageSize) {
        Log.d("SearchResultScreen", "Searching for: $keyword, sort=$sortField $sortDirection")
        viewModel.search(
            keyword = keyword,
            sortField = sortField,
            sortDirection = sortDirection,
            page = page,
            pageSize = pageSize
        )
    }

    val products = uiState.result?.items.orEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            uiState.errorMessage != null -> {
                Text(
                    text = "Lỗi: ${uiState.errorMessage}",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            products.isEmpty() -> {
                Text(
                    text = "Không có sản phẩm nào!",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(products) { product ->
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product) }
                        )
                    }
                }
            }
        }
    }
}
