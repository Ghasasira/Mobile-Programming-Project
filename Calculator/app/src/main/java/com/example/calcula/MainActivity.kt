package com.example.calcula

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calcula.ui.theme.CalculaTheme
import com.example.calcula.ui.theme.CalculaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculaTheme{
                val viewMode = viewModel<CalcluatorViewModel>()
                val state = viewMode.state
                val answerState by remember { derivedStateOf { viewMode.answer.answer } }

                Calculator(
                    state = state,
                    answer=answerState,
                    onAction = viewMode::onAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray)
                        .padding(16.dp)

                )

            }
        }
    }
}

