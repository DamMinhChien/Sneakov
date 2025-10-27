package com.magento.sneakov.presentation.ui.screen.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.magento.sneakov.domain.model.Product
import com.magento.sneakov.presentation.ui.compose.ProductCard
import com.magento.sneakov.presentation.ui.theme.SneakovTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchResultScreen(viewModel: SearchViewModel = koinViewModel(), onProductClick: (Product) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val products = uiState.result?.items

    Log.d("SearchViewModel", "View: ${uiState}")
    LaunchedEffect(uiState) {
        Log.d("SearchViewModel", "View updated: $uiState")
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Column {
            if (!products.isNullOrEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    items(items = products) { product ->
                        ProductCard(
                            product = product,
                            onClick = onProductClick
                        )
                    }

                }
            }
            else{
                Text(text = "Không có sản phẩm nào!", textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SearchResultScreenPreView() {
    SneakovTheme {
        SearchResultScreen(koinViewModel(), onProductClick = {})
    }
}