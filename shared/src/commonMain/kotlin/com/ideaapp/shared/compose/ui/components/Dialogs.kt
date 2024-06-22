package com.ideaapp.shared.compose.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ideasapp.shared.generated.resources.Res
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ideasapp.shared.generated.resources.cancel
import ideasapp.shared.generated.resources.confirm
import ideasapp.shared.generated.resources.date
import ideasapp.shared.generated.resources.dismiss
import ideasapp.shared.generated.resources.error_invalid_datetime
import ideasapp.shared.generated.resources.reminder
import ideasapp.shared.generated.resources.select_time
import ideasapp.shared.generated.resources.time
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource


@Composable
fun ShowDialogConfirmation(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogText: String,
) {
    AlertDialog(
        text = {
            Text(text = dialogText, style = MaterialTheme.typography.titleSmall)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(Res.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(Res.string.dismiss))
            }
        }
    )
}


/*
Time Picker
 */
@Composable
fun TimePickerDialog(
    title: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text(stringResource(Res.string.cancel)) }
                    TextButton(
                        onClick = onConfirm
                    ) { Text(stringResource(Res.string.confirm)) }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeDialog(
    onCancel: () -> Unit,
    onConfirm: (Long) -> Unit,
    context: Context,
    modifier: Modifier = Modifier,
) {
    //date
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    val currentDateTime = Clock.System.now()
    val currentLocalDateTime = currentDateTime.toLocalDateTime(TimeZone.currentSystemDefault())
    val date = remember {
        currentLocalDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date,
        yearRange = 2020..2060
    )

    var selectedDate by remember {
        mutableStateOf(
            DateTimeConvertor.convertLongToDate(date)
        )
    }

    //time
    var showTimePicker by remember {
        mutableStateOf(false)
    }

    var selectedTime by remember {
        mutableStateOf(
            DateTimeConvertor.convertIntToTime(
                currentLocalDateTime.hour,
                currentLocalDateTime.minute
            )
        )
    }

    val timePickerState = rememberTimePickerState(
        initialHour = currentLocalDateTime.hour,
        initialMinute = currentLocalDateTime.minute,
        is24Hour = true
    )
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        //Соединение даты и времени
        val combinedDateTime = datePickerState.selectedDateMillis?.let { dateMillis ->
            val selectedInstant = Instant.fromEpochMilliseconds(dateMillis)
            val selectedLocalDateTime =
                selectedInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            LocalDateTime(
                selectedLocalDateTime.year,
                selectedLocalDateTime.month,
                selectedLocalDateTime.dayOfMonth,
                timePickerState.hour,
                timePickerState.minute,
            ).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        } ?: 0L

        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text(
                text = stringResource(Res.string.reminder),
                style = MaterialTheme.typography.displaySmall,
                modifier = modifier.padding(8.dp),
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface
            )
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                                        selectedDate =
                                            DateTimeConvertor.convertLongToDate(selectedDateMillis)
                                    }
                                    showDatePicker = false
                                }
                            ) { Text(stringResource(Res.string.confirm)) }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showDatePicker = false
                                }
                            ) { Text(stringResource(Res.string.cancel)) }
                        },
                        content = {
                            DatePicker(
                                state = datePickerState,
                                showModeToggle = false,
                            )

                        }
                    )
                }

                TextComponentButton(
                    content = stringResource(Res.string.date),
                    secondText = selectedDate,
                    onClick = {
                        showDatePicker = true
                    },
                )

                TextComponentButton(
                    content = stringResource(Res.string.time),
                    secondText = selectedTime,
                    onClick = {
                        showTimePicker = true
                    },
                )
                if (showTimePicker) {
                    TimePickerDialog(
                        title = stringResource(Res.string.select_time),
                        onCancel = { showTimePicker = false },
                        onConfirm = {
                            timePickerState.let { time ->
                                selectedTime =
                                    DateTimeConvertor.convertIntToTime(time.hour, time.minute)
                            }
                            showTimePicker = false
                        },
                        content = {
                            TimePicker(state = timePickerState)
                        }
                    )
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onCancel
                    ) { Text(stringResource(Res.string.cancel)) }

                    Button(
                        onClick = {
                            if (DateTimeConvertor.isValidDateTime(combinedDateTime)) {
                                onConfirm(combinedDateTime)
                            } else {
                                mToast(
                                    context,
                                    context.getString(Res.string.error_invalid_datetime)
                                )
                            }
                        }
                    ) { Text(stringResource(Res.string.confirm)) }
                }

            }
        }
    }
}




