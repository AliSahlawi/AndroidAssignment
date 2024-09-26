package com.example.androidassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androidassignment.features.assignment.presentation.view.AssignmentScreen
import com.example.androidassignment.features.assignment.presentation.viewmodel.AssignmentViewModel
import com.example.androidassignment.ui.theme.AndroidAssignmentTheme

class MainActivity : ComponentActivity() {

    private val assignmentViewModel: AssignmentViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showBottomSheet by remember { mutableStateOf(false) }
            val sheetState = rememberModalBottomSheetState()
            AndroidAssignmentTheme {
                Scaffold(floatingActionButton = {
                    FloatingActionButton(content = {
                        Icon(
                            Icons.Filled.MoreVert,
                            contentDescription = ""
                        )
                    }, onClick = { showBottomSheet = true })
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->

                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet = false
                            },
                            sheetState = sheetState
                        ) {
                            Column(Modifier.padding(8.dp)) {
                                Text("Top 3 Characters: ", fontWeight = FontWeight.Bold)
                                Text(assignmentViewModel.getTop3Characters())
                            }
                        }
                    }
                    AssignmentScreen(
                        Modifier.padding(innerPadding),
                        viewModel = assignmentViewModel
                    )
                }
            }
        }
    }
}