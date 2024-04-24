package com.example.tasksappskeleton.components.taskTile

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TaskDetailsFilterBar(
    modifier: Modifier,
    filterTags: List<String>,
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {

    LazyRow (
        modifier= Modifier.fillMaxWidth()
    ){

        item {

            filterTags.forEach { tag ->
                TextButton(onClick = { onTabSelected(tag) }) {
                    Text(
                        text = tag,
                        fontSize = 18.sp,
                        color = if (
                            tag == selectedTab
                        ) {
                            Color.Blue
                        } else {
                            Color.Gray
                        }

                    )
                }
            }   }

    }
}