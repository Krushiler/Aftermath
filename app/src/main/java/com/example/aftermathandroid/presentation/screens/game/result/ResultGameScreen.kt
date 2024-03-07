package com.example.aftermathandroid.presentation.screens.game.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.component.animation.animateBoolAsFloatState
import com.example.aftermathandroid.presentation.common.component.button.ListSectionButton
import com.example.aftermathandroid.presentation.common.component.container.ExpandableColumnSection
import com.example.aftermathandroid.presentation.navigation.root.RootNavigation
import com.example.aftermathandroid.presentation.navigation.root.createRootNavigation
import com.example.aftermathandroid.presentation.screens.game.result.component.QuestionSummaryItem
import com.example.aftermathandroid.presentation.screens.game.result.component.ResultGameInfoView
import com.example.aftermathandroid.presentation.theme.Dimens

@Composable
fun ResultGameScreen(
    rootNavigation: RootNavigation = createRootNavigation(),
    viewModel: ResultGameViewModel = hiltViewModel()
) {
    val state = viewModel.stateFlow.collectAsState()

    val showSummaryAnimation = animateBoolAsFloatState(targetValue = state.value.showSummary)

    BackHandler {
        rootNavigation.back()
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                contentPadding = PaddingValues(Dimens.md)
            ) {
                item {
                    ResultGameInfoView(
                        time = state.value.time,
                        score = state.value.score,
                        totalQuestions = state.value.totalQuestions
                    )
                    Gap.Md()
                    ExpandableColumnSection(
                        items = state.value.questions,
                        expanded = state.value.showSummary,
                        header = {
                            ListSectionButton(title = "Summary", isSelected = state.value.showSummary, onClick = {
                                viewModel.switchSummary()
                            })
                        }
                    ) { question ->
                        Box(
                            modifier = Modifier
                                .padding(top = Dimens.md)
                                .scale(scaleX = 1f, scaleY = showSummaryAnimation.value)
                        ) {
                            QuestionSummaryItem(question)
                        }
                    }
                }
                item {
                    Gap.Md()
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { rootNavigation.back() },
                    ) {
                        Text(text = stringResource(id = R.string.back))
                    }
                }
            }
        }
    }
}