package com.example.composesubmission.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.composesubmission.R
import com.example.composesubmission.di.Injection
import com.example.composesubmission.ui.ViewModelFactory
import com.example.composesubmission.ui.common.UiState

@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { state ->
        when (state) {
            is UiState.Loading -> {
                viewModel.getAgentById(id)
            }
            is UiState.Success -> {
                val data = state.data
                DetailContent(
                    photoUrl = data.photoUrl,
                    name = data.name,
                    role = data.role,
                    biography = data.biography,
                    qSkill = data.qSkillIcon,
                    eSkill = data.eSkillIcon,
                    cSkill = data.cSkillIcon,
                    xSkill = data.xSkillIcon,
                    isFavorite = data.isFavorite,
                    onBackClick = navigateBack,
                    onFavClick = {
                        viewModel.addToFavorite(data.id, !data.isFavorite)
                    }
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun DetailContent(
    photoUrl: String,
    name: String,
    role: String,
    biography: String,
    qSkill: String,
    eSkill: String,
    cSkill: String,
    xSkill: String,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = modifier) {
            AsyncImage(
                model = photoUrl,
                contentDescription = name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
            )
            IconButton(
                onClick = {
                    onFavClick()
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite),
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = modifier
            )
            Text(
                text = role,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = modifier
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.skills),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = qSkill,
                contentDescription = qSkill,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(40.dp)
            )
            AsyncImage(
                model = eSkill,
                contentDescription = eSkill,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(40.dp)
            )
            AsyncImage(
                model = cSkill,
                contentDescription = cSkill,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(40.dp)
            )
            AsyncImage(
                model = xSkill,
                contentDescription = name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.biography),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = biography,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}