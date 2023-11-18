package com.example.aftermathandroid.presentation.screens.dictionary.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.component.button.BackButton
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigation
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigationViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryScreenSource
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.presentation.theme.Dimens

@Composable
fun DictionarySelectScreen(
    rootNavigationViewModel: RootNavigationViewModel = rootViewModel(),
    dictionaryNavigationViewModel: DictionaryNavigationViewModel = rootViewModel(),
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.md),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton(onClick = { dictionaryNavigationViewModel.back() || rootNavigationViewModel.back() })
                Gap.Md()
                Text(text = stringResource(R.string.selectDictionary), style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.fillMaxWidth())
                IconButton(onClick = { rootNavigationViewModel.back() }) {
                    Icon(imageVector = Icons.Outlined.Close, contentDescription = stringResource(id = R.string.close))
                }
            }
            DictionaryNavigation(dictionaryScreenSource = DictionaryScreenSource.Select)
        }
    }
}