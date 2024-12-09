package ru.zulvit.mittqueuemobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.zulvit.mittqueuemobile.models.Queue
import ru.zulvit.mittqueuemobile.models.Student
import ru.zulvit.mittqueuemobile.ui.screens.LoginScreen
import ru.zulvit.mittqueuemobile.ui.screens.QueueScreen
import ru.zulvit.mittqueuemobile.ui.screens.RegistrationScreen
import ru.zulvit.mittqueuemobile.ui.theme.MittQueueMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MittQueueMobileTheme {
                val navController = rememberNavController()

                val queues = mutableListOf(
                    Queue(
                        name = "Queue 1",
                        students = mutableListOf(
                            Student("Ivan", "Petrov"),
                            Student("Maria", "Ivanova"),
                            Student("Pavel", "Sidorov")
                        )
                    ),
                    Queue(
                        name = "Queue 2",
                        students = mutableListOf(
                            Student("Olga", "Kuznetsova"),
                            Student("Alexey", "Fedorov")
                        )
                    )
                )

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = { navController.navigate("queue") },
                            onRegisterClick = { navController.navigate("register") }
                        )
                    }
                    composable("register") {
                        RegistrationScreen(
                            onRegisterSuccess = { navController.navigate("login") },
                            onBackToLogin = { navController.navigate("login") }
                        )
                    }
                    composable("queue") {
                        QueueScreen(
                            queues = queues,
                            onQueueCreated = { queueName ->
                                queues.add(Queue(name = queueName, students = mutableListOf()))
                            },
                            onQueueJoined = { queue ->
                                // Логика присоединения к очереди
                            }
                        )
                    }
                }
            }
        }
    }
}
