package com.example.androidassignment.features.assignment.presentation.model

data class AssignmentViewState(
    val query : String = "",
    val items: Map<Int,List<ListItem>> = emptyMap(),
    val selectedImage: Int = 0
    )
