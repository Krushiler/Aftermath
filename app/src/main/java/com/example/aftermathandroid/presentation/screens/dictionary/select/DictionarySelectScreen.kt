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
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.button.BackButton
import com.example.aftermathandroid.presentation.common.provider.LocalDictionaryNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.LocalRootNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.storeViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigation
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigationViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryScreenSource
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.util.rememberViewModelStoreOwner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionarySelectScreen(
    rootNavigationViewModel: RootNavigationViewModel = storeViewModel(LocalRootNavigationOwner)
) {
    val store = rememberViewModelStoreOwner()
    CompositionLocalProvider(
        LocalDictionaryNavigationOwner provides store,
    ) {
        val dictionaryNavigationViewModel: DictionaryNavigationViewModel =
            storeViewModel(LocalDictionaryNavigationOwner)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.selectDictionary))
                    },
                    navigationIcon = {
                        BackButton(onClick = { dictionaryNavigationViewModel.back() })
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier.padding(padding)
            ) {
                DictionaryNavigation(dictionaryScreenSource = DictionaryScreenSource.Select)
            }
        }
    }
}