package com.magento.sneakov.presentation.ui.screen.search

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.util.CoilUtils.result
import com.magento.sneakov.presentation.ui.theme.SneakovTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchResultScreen(viewModel: SearchViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Log.d("SearchViewModel", "View: ${uiState}")
    LaunchedEffect(uiState) {
        Log.d("SearchViewModel", "View updated: $uiState")
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Column {
            if (uiState.result?.items?.isNotEmpty() == true) {
                Toast.makeText(context, "Có kết quả", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "k có", Toast.LENGTH_LONG).show()
            }

        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SearchResultScreenPreView() {
    SneakovTheme {
        SearchResultScreen(koinViewModel())
    }
}