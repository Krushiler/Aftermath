package com.example.aftermathandroid.presentation.screens.dictionary.select

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.button.BackButton
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigator
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryScreenSource
import com.example.aftermathandroid.presentation.navigation.dictionary.LocalDictionaryNavController
import com.example.aftermathandroid.presentation.navigation.dictionary.createDictionaryNavigation
import com.example.aftermathandroid.presentation.navigation.root.RootNavigation
import com.example.aftermathandroid.presentation.navigation.root.createRootNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionarySelectScreen(
    navController: NavHostController = rememberNavController(),
    rootNavigation: RootNavigation = createRootNavigation()
) {
    CompositionLocalProvider(
        LocalDictionaryNavController provides navController
    ) {
        val dictionaryNavigation = createDictionaryNavigation()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.selectDictionary))
                    },
                    navigationIcon = {
                        BackButton(onClick = { dictionaryNavigation.back() || rootNavigation.back() })
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier.padding(padding)
            ) {
                DictionaryNavigator(
                    dictionaryScreenSource = DictionaryScreenSource.Select,
                    navController = navController,
                )
            }
        }
    }
}