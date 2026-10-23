package com.tianmen.ngz.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerDialog(initial: Long, onDismiss: () -> Unit, onConfirm: (Long) -> Unit) {
    val initCal = remember(initial) { Calendar.getInstance().apply { timeInMillis = initial } }
    val dateState = rememberDatePickerState(initialSelectedDateMillis = initial)
    val timeState = rememberTimePickerState(
        initialHour = initCal.get(Calendar.HOUR_OF_DAY),
        initialMinute = initCal.get(Calendar.MINUTE),
        is24Hour = true,
    )
    var showTime by remember { mutableStateOf(false) }
    var pickedDate by remember { mutableStateOf(initial) }

    if (!showTime) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    pickedDate = dateState.selectedDateMillis ?: initial
                    showTime = true
                }) { Text("下一步", color = MaterialTheme.colorScheme.primary) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text("取消") }
            },
            shape = RoundedCornerShape(20.dp),
        ) {
            DatePicker(state = dateState)
        }
    } else {
        Dialog(onDismissRequest = onDismiss) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(24.dp),
            ) {
                Text("选择时间", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.width(0.dp))
                TimePicker(state = timeState, modifier = Modifier.padding(top = 16.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = { showTime = false }) { Text("返回") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        val cal = Calendar.getInstance().apply {
                            timeInMillis = pickedDate
                            set(Calendar.HOUR_OF_DAY, timeState.hour)
                            set(Calendar.MINUTE, timeState.minute)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        onConfirm(cal.timeInMillis)
                    }) { Text("确定") }
                }
            }
        }
    }
}
