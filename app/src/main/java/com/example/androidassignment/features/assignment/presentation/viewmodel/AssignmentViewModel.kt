package com.example.androidassignment.features.assignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidassignment.R
import com.example.androidassignment.features.assignment.presentation.model.AssignmentViewState
import com.example.androidassignment.features.assignment.presentation.model.ListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class AssignmentViewModel : ViewModel() {


    private val imageItems = listOf(
        ListItem(R.drawable.apple, "Apple", "Fruit"),
        ListItem(R.drawable.banana, "Banana", "Fruit"),
        ListItem(R.drawable.strawberry, "Strawberry", "Fruit"),
        ListItem(R.drawable.onion, "Onion", "Vegetable"),
        ListItem(R.drawable.tomato, "Tomato", "Vegetable"),
        ListItem(R.drawable.carrorts, "Carrots", "Vegetable"),

    )

    private val images = listOf(
        R.drawable.apple,
        R.drawable.banana,
        R.drawable.strawberry,
        R.drawable.onion,
        R.drawable.tomato,
        R.drawable.carrorts
    )

    private val items = buildMap {
        images.forEachIndexed { _, image ->
            put(image,getRandomItems())
        }
    }

    private fun getRandomItems() : List<ListItem> {
        val listItems = mutableListOf<ListItem>()
        repeat(Random.nextInt(from = 1, until = 20)) { listItems.add(imageItems[Random.nextInt(6)]) }
        return listItems
    }


    private val _uiState = MutableStateFlow(AssignmentViewState(items = items))
    val uiState = _uiState.asStateFlow()


    fun onEvent(event: AssignmentUiEvent) {
        when (event) {
            is AssignmentUiEvent.OnImageChanged -> {
                _uiState.update { state ->
                    state.copy(selectedImage = images[event.index])
                }
            }

            is AssignmentUiEvent.OnQueryChanged -> {
                _uiState.update { state ->
                    state.copy(
                        query = event.query,
                        items =  filterItems(event.query,state.selectedImage)
                    )
                }
            }
        }
    }

    private fun filterItems(query: String, selectedImage: Int):  Map<Int, List<ListItem>> {
        return if (query.isEmpty()) {
            items
        } else {
            val filteredItems = items[selectedImage]?.filter { it.title.contains(query, ignoreCase = true) }
            if (filteredItems != null) {
                items.toMutableMap().apply {
                    this[selectedImage] = filteredItems
                }
            } else {
                items
            }
        }
    }


    fun getTop3Characters() : String {
        val listOfTitlesCombined = items[_uiState.value.selectedImage]?.map { it.title }
            ?.joinToString("")

        val charFrequency = listOfTitlesCombined?.groupingBy { it }?.eachCount()

        val top3Characters = charFrequency?.entries
            ?.sortedByDescending { it.value }
            ?.take(3)

        var  result = ""

        top3Characters?.forEach { (char, count) ->
            result +="$char = $count\n"
        }
        return result
    }

    sealed interface AssignmentUiEvent {

        class OnQueryChanged(val query: String) : AssignmentUiEvent
        class OnImageChanged(val index: Int) : AssignmentUiEvent
    }

}