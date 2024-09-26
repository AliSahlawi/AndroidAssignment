package com.example.androidassignment.features.assignment.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidassignment.features.assignment.presentation.model.ListItem
import com.example.androidassignment.features.assignment.presentation.viewmodel.AssignmentViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AssignmentScreen(modifier: Modifier = Modifier, viewModel: AssignmentViewModel) {
    val state by viewModel.uiState.collectAsState()

    LazyColumn(modifier = modifier) {
        item {
            ImageCarousel(state.items.keys.toList()) { imageIndex ->
                viewModel.onEvent(AssignmentViewModel.AssignmentUiEvent.OnImageChanged(imageIndex))
            }
        }
        stickyHeader {
            SearchBar(state.query) { query ->
                viewModel.onEvent(AssignmentViewModel.AssignmentUiEvent.OnQueryChanged(query))
            }
        }

        state.items[state.selectedImage]?.let { listOfItems ->
            items(
                 count = listOfItems.size,
            ) { index ->
                ListItemCard(listOfItems[index])
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel(listOfImages: List<Int>, onImageChanged: (Int) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { listOfImages.size })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onImageChanged(page)
        }
    }

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(240.dp)
        ) { page ->
            Image(
                painter = painterResource(id = listOfImages[page]),
                contentDescription = "Image $page",
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(listOfImages.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.Cyan else Color.Gray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        shape = RoundedCornerShape(50),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.LightGray,
        )
    )
}

@Composable
fun ListItemCard(item: ListItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = "item image",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .border(BorderStroke(width = 2.dp, Color.Transparent), shape = RoundedCornerShape(2.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = item.subtitle,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
