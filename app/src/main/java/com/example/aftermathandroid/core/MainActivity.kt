package com.example.aftermathandroid.core

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.aftermathandroid.presentation.common.provider.LocalRootSnackbarState
import com.example.aftermathandroid.presentation.common.provider.LocalRootStoreOwner
import com.example.aftermathandroid.presentation.navigation.root.RootNavigator
import com.example.aftermathandroid.presentation.theme.AftermathAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        setContent {
            AftermathAndroidTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                CompositionLocalProvider(
                    LocalRootStoreOwner provides this,
                    LocalRootSnackbarState provides snackbarHostState
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background,
                    ) {
                        Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
                            Box(modifier = Modifier.padding(it)) {
                                RootNavigator()
                            }
                        }
                    }
                }
            }
        }
    }
}
