package com.example.composesubmission.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composesubmission.R
import com.example.composesubmission.di.Injection
import com.example.composesubmission.model.Agent
import com.example.composesubmission.ui.ViewModelFactory
import com.example.composesubmission.ui.common.UiState
import com.example.composesubmission.ui.components.AgentCard
import com.example.composesubmission.ui.components.Search

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { state ->
        when (state) {
            is UiState.Loading -> {
                viewModel.getAllAgent()
            }

            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::searchAgent,
                    listAgent = state.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listAgent: List<Agent>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    val listState = rememberLazyListState()
    Scaffold(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = {
            Search(
                query = query,
                onQueryChange = onQueryChange
            )
        },
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (listAgent.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.no_data),
                            fontSize = 20.sp
                        )
                    }
                }
            } else {
                items(listAgent, key = { it.id }) { data ->
                    AgentCard(
                        name = data.name,
                        biography = data.biography,
                        photoUrl = data.photoUrl,
                        modifier = Modifier
                            .clickable {
                                navigateToDetail(data.id)
                            }
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            }
        }
    }
}