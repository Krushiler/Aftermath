package com.example.aftermathandroid.presentation.screens.dictionary.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.button.ProfileButton
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigationViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryScreenSource
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.presentation.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionariesMenuScreen(
    dictionaryScreenSource: DictionaryScreenSource,
    dictionaryNavigation: DictionaryNavigationViewModel = rootViewModel(),
    rootNavigation: RootNavigationViewModel = rootViewModel()
) {
    Scaffold(
        topBar = {
            if (dictionaryScreenSource == DictionaryScreenSource.Main)
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.dictionaries)) },
                    actions = {
                        ProfileButton(
                            profilePressed = { rootNavigation.navigateToProfile() },
                            logoutPressed = { rootNavigation.logout() }
                        )
                    }
                )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.md),
                verticalArrangement = Arrangement.spacedBy(Dimens.md),
            ) {
                item {
                    DictionaryMenuItem(
                        name = stringResource(id = R.string.myDictionaries),
                        icon = Icons.Outlined.Home,
                        onPressed = { dictionaryNavigation.navigateToMyDictionaries() }
                    )
                }
                item {
                    DictionaryMenuItem(
                        name = stringResource(id = R.string.search),
                        icon = Icons.Outlined.Search,
                        onPressed = { dictionaryNavigation.navigateToSearch() }
                    )
                }
            }
        }
    }
}