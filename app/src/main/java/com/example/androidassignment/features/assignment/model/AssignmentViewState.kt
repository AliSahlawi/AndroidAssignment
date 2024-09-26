package com.example.androidassignment.features.assignment.model

import com.example.androidassignment.features.assignment.adapter.ListItem

data class AssignmentViewState(
    val query : String = "",
    val items: Map<Int,List<ListItem>> = emptyMap(),
    val selectedImage: Int = 0
    )
