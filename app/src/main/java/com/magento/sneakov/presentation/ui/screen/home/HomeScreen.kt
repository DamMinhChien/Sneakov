package com.magento.sneakov.presentation.ui.screen.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.BaseCard
import com.magento.sneakov.R
import com.magento.sneakov.domain.model.Product
import com.magento.sneakov.presentation.ui.compose.LoadingOverlay
import com.magento.sneakov.presentation.ui.compose.ProductCard
import com.magento.sneakov.presentation.ui.screen.category.CategoryViewModel
import com.magento.sneakov.presentation.ui.screen.search.SearchViewModel
import com.magento.sneakov.presentation.ui.theme.SneakovTheme
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Search
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    categoryViewModel: CategoryViewModel = koinViewModel(),
    navigateToSearchScreen: () -> Unit,
    newProductViewModel: SearchViewModel = koinViewModel(),
    onProductClick: (Product) -> Unit
) {
    val categoryState by categoryViewModel.uiState.collectAsState()
    val newProductState by newProductViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val products = newProductState.result?.items
    LaunchedEffect(Unit) {
        Log.d("Check", "Category LaunchedEffect chạy")
        categoryViewModel.getCategories()
    }
    LaunchedEffect(Unit) {
        Log.d("Check", "Search LaunchedEffect chạy")
        newProductViewModel.search(keyword = "", sortField = "created_at", sortDirection = "DESC", page = 1, pageSize = 10)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                    .clickable { navigateToSearchScreen() }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        FontAwesomeIcons.Solid.Search,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Tìm kiếm...",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
//            Text("abcwwkfhs: ${uiState.result}")
            Text("Danh mục sản phẩm", style = MaterialTheme.typography.bodyLarge)
            // Danh sách danh mục
            if (categoryState.result.isNotEmpty()) {
                val categories = categoryState.result
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(categories) { category ->
                        BaseCard {
                            AsyncImage(
                                model = category.imageUrl,
                                contentDescription = category.name,
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(8.dp),
                                contentScale = ContentScale.Crop,
                                error = painterResource(R.drawable.img_place_error),
                                placeholder = painterResource(R.drawable.img_place_holder)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            // Sản phẩm mới
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Hàng mới về", style = MaterialTheme.typography.bodyLarge)
                Text("Xem tất cả", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
            if (!products.isNullOrEmpty()) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = products) { product ->
                        Log.d("Check", "product: $product")
                        ProductCard(
                            modifier = Modifier.fillMaxWidth(1f / 3f).aspectRatio(0.75f),
                            product = product,
                            onClick = onProductClick
                        )
                    }

                }
            } else {
                Text(text = "Không có sản phẩm nào!", textAlign = TextAlign.Center)
            }
        }
    }
    when {
        categoryState.isLoading -> LoadingOverlay(isLoading = true)

        categoryState.errorMessage != null -> {
            Toast.makeText(context, categoryState.errorMessage, Toast.LENGTH_LONG).show()
            categoryViewModel.resetError()
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomePreViewNice() {
    SneakovTheme {
        HomeScreen(
            navigateToSearchScreen = {},
            onProductClick = {},
        )
    }
}