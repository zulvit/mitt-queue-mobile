package ru.zulvit.mittqueuemobile.ui.screens

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ru.zulvit.mittqueuemobile.models.Queue
import ru.zulvit.mittqueuemobile.models.Student


@Composable
fun QueueScreen(
    queues: List<Queue>,
    onQueueCreated: (String) -> Unit,
    onQueueJoined: (Queue) -> Unit
) {
    var selectedQueue by remember { mutableStateOf<Queue?>(null) }
    var isQueueOpened by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Available Queues", style = MaterialTheme.typography.h6)

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(queues) { queue ->
                QueueItem(queue = queue, onClick = {
                    selectedQueue = queue
                    isQueueOpened = true
                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedQueue != null && isQueueOpened) {
            QueueDetailsScreen(queue = selectedQueue!!, onClose = { isQueueOpened = false })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QueueItem(queue: Queue, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = queue.name, style = MaterialTheme.typography.h6)
        }
    }
}

@Composable
fun QueueDetailsScreen(queue: Queue, onClose: () -> Unit) {
    var isQueueOpen by remember { mutableStateOf(true) }
    var studentName by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Queue: ${queue.name}", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Students in queue:", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(queue.students) { student ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${student.firstName} ${student.lastName}", style = MaterialTheme.typography.body1)
                    IconButton(onClick = {
                        queue.students.remove(student)
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add new student
        OutlinedTextField(
            value = studentName,
            onValueChange = { studentName = it },
            label = { Text("Enter student name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (studentName.text.isNotEmpty()) {
                    val studentParts = studentName.text.split(" ")
                    if (studentParts.size >= 2) {
                        val student = Student(studentParts[0], studentParts[1])
                        queue.students.add(student)
                        studentName = TextFieldValue("") // Очистить поле после добавления
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Student")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Close Queue", color = Color.White)
        }
    }
}
