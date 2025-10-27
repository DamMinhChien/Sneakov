package com.magento.sneakov.presentation.ui.screen.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.magento.sneakov.presentation.ui.compose.LoadingOverlay
import com.magento.sneakov.presentation.ui.theme.SneakovTheme
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.History
import compose.icons.fontawesomeicons.solid.Search
import compose.icons.fontawesomeicons.solid.Times
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel = koinViewModel(), onSearch: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        var query by remember { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }

        Column {

            LaunchedEffect(uiState.result){
                if (uiState.result != null){
                    Toast.makeText(context, "Data: ${uiState.result}", Toast.LENGTH_LONG).show()
                    onSearch()
                }
            }
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = { query = it },
                        onSearch = {
                            viewModel.search(query)
                            active = false
                        },
                        expanded = active,
                        onExpandedChange = { active = it },
                        placeholder = { Text("Tìm kiếm...") },
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                Row {
                                    IconButton(onClick = { query = "" }) {
                                        Icon(
                                            imageVector = FontAwesomeIcons.Solid.Times,
                                            contentDescription = "Clear",
                                            modifier = Modifier.size(24.dp),
                                        )
                                    }
                                    IconButton(onClick = {
                                        viewModel.search(query)
                                        active = false
                                    }) {
                                        Icon(
                                            imageVector = FontAwesomeIcons.Solid.Search,
                                            contentDescription = "Search",
                                            modifier = Modifier.size(24.dp),
                                        )
                                    }
                                }
                            }
                        }
                    )
                },
                expanded = active,
                onExpandedChange = { active = it }
            ) {
                listOf("Nike", "Adidas", "Puma").forEach { item ->
                    ListItem(
                        headlineContent = { Text(item) },
                        leadingContent = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.History,
                                contentDescription = "History",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier.clickable {
                            query = item
                            onSearch()
                            active = false
                        }
                    )
                }
            }

        }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

    }
    when {
        uiState.isLoading -> LoadingOverlay(isLoading = true)

        uiState.errorMessage != null -> {
            Toast.makeText(context, uiState.errorMessage,Toast.LENGTH_LONG).show()
            viewModel.resetError()
        }

        uiState.result != null  ->  {}
    }


}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomePreView() {
    SneakovTheme {
        SearchScreen(koinViewModel(), onSearch = {})
    }
}
