package com.example.tasksappskeleton.pages
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.webkit.WebSettings
//import android.webkit.WebView
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.viewinterop.AndroidView
//
//class WebViewActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            WebViewScreen("https://example.com/sample.pdf")
//        }
//    }
//}
//
//@SuppressLint("SetJavaScriptEnabled")
//@Composable
//fun WebViewScreen(url: String) {
//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = { context ->
//            WebView(context).apply {
//                settings.javaScriptEnabled = true
//                //settings.pluginState = WebSettings.PluginState.ON
//                loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
//            }
//        }
//    )
//}
