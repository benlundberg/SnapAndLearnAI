package com.app.snaplearnai.features.quiz.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.app.snaplearnai.R
import com.app.snaplearnai.features.quiz.ui.model.QuizUiState
import com.app.snaplearnai.features.quiz.ui.model.UiQuizQuestion
import com.app.snaplearnai.shared.ui.component.button.ButtonStyle
import com.app.snaplearnai.shared.ui.component.button.CustomButton
import com.app.snaplearnai.shared.ui.component.dialog.CustomDialog
import com.app.snaplearnai.shared.ui.component.loading.CustomLoading
import com.app.snaplearnai.shared.ui.component.page.CustomScaffold
import com.app.snaplearnai.shared.ui.theme.AppTheme
import com.app.snaplearnai.shared.ui.theme.semiBold

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.uiStateFlow.collectAsState(initial = QuizUiState(isLoading = true))

    if (state.isLoading) {
        // Show loading indicator
        CustomLoading()
    } else {
        CustomScaffold {
            QuizContent(
                state = state,
                onAnswerSelected = viewModel::onAnswerSelected,
                onBack = onBack,
                onNextQuestion = viewModel::onNextQuestion
            )
        }
    }
}

@Composable
private fun QuizContent(
    state: QuizUiState,
    onAnswerSelected: (Boolean) -> Unit,
    onBack: () -> Unit,
    onNextQuestion: () -> Unit
) {
    val currentQuestion = state.currentQuestion

    var isDialogVisible by remember { mutableStateOf(false) }

    if (isDialogVisible) {
        CustomDialog(
            title = stringResource(R.string.cancel_dialog_title),
            text = stringResource(R.string.cancel_quiz_dialog_msg),
            confirmText = stringResource(R.string.gen_yes),
            onConfirm = {
                isDialogVisible = false
                onBack()
            },
            dismissText = stringResource(R.string.gen_no),
            onDismiss = {
                isDialogVisible = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = AppTheme.spacings.m, horizontal = AppTheme.spacings.l)
    ) {
        if (currentQuestion != null) {
            IconButton(
                onClick = {
                    isDialogVisible = true
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .align(Alignment.End),
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        }

        if (currentQuestion == null) {
            QuestionCompletedView(
                correctAnswers = state.correctAnswers,
                totalQuestions = state.allQuestions.size,
                onBack = onBack
            )
        } else {
            QuestionView(
                question = currentQuestion,
                onAnswerSelected = onAnswerSelected,
                onNextQuestion = onNextQuestion,
                isLastQuestion = state.isLastQuestion
            )
        }
    }
}

@Composable
fun QuestionCompletedView(correctAnswers: Int, totalQuestions: Int, onBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(R.string.quiz_complete_title),
                style = AppTheme.typography.large.semiBold
            )
            Spacer(modifier = Modifier.height(AppTheme.spacings.s))
            Text(
                text = stringResource(R.string.quiz_complete_msg, correctAnswers, totalQuestions),
                style = AppTheme.typography.default
            )

            Spacer(modifier = Modifier.height(AppTheme.spacings.l))
            CustomButton(
                onClick = onBack,
                text = stringResource(R.string.back_to_home_btn),
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.SECONDARY
            )
        }
    }
}

@Composable
private fun QuestionView(
    question: UiQuizQuestion,
    isLastQuestion: Boolean,
    onAnswerSelected: (Boolean) -> Unit,
    onNextQuestion: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacings.m)
        ) {
            Text(
                text = question.question,
                style = AppTheme.typography.large.semiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(AppTheme.spacings.l))

            var answeredIndex by remember { mutableStateOf<Int?>(null) }

            question.options.forEachIndexed { index, option ->

                val buttonColor by animateColorAsState(
                    targetValue = when {
                        answeredIndex == null -> {
                            AppTheme.color.secondary
                        }

                        index == question.correctAnswerIndex -> {
                            AppTheme.color.grassGreen
                        }

                        answeredIndex != question.correctAnswerIndex -> {
                            AppTheme.color.brickRed
                        }

                        else -> {
                            AppTheme.color.gray400
                        }
                    },
                    label = "ButtonColorChange"
                )

                CustomButton(
                    onClick = {
                        answeredIndex = index
                        onAnswerSelected(index == question.correctAnswerIndex)
                    },
                    buttonColors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    text = option
                )
            }

            AnimatedVisibility(visible = answeredIndex != null) {
                Spacer(modifier = Modifier.height(AppTheme.spacings.l))
                CustomButton(
                    onClick = {
                        answeredIndex = null
                        onNextQuestion()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    text = if (isLastQuestion) stringResource(R.string.gen_continue) else stringResource(
                        R.string.next_question_btn
                    )
                )
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
)
private fun QuizPreview() {
    AppTheme(darkTheme = true) {
        Surface {
            QuizContent(
                state = QuizUiState(
                    isLoading = false, allQuestions = listOf(
                        UiQuizQuestion(
                            question = "What is the capital of France?",
                            options = listOf("Paris", "London", "Berlin", "Madrid"),
                            correctAnswerIndex = 1
                        )
                    )
                ),
                onAnswerSelected = { },
                onNextQuestion = { },
                onBack = { }
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
)
private fun QuizCompletedPreview() {
    AppTheme {
        QuizContent(
            state = QuizUiState(
                isLoading = false,
                allQuestions = listOf(
                    UiQuizQuestion(
                        question = "What is the capital of France?",
                        options = listOf("Paris", "London", "Berlin", "Madrid"),
                        correctAnswerIndex = 1
                    )
                ),
                currentQuestionIndex = 1
            ),
            onAnswerSelected = { },
            onNextQuestion = { },
            onBack = { }
        )
    }
}