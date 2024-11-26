package com.example.aplicacion12.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.aplicacion12.viewmodel.*
import com.example.aplicacion12.data.AppDatabase
import com.example.aplicacion12.viewmodel.Task.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val taskDao = database.taskDao()
        val taskTypeDao = database.taskTypeDao()
        val viewModelFactory = TaskViewModelFactory(taskDao, taskTypeDao)
        val taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        setContent {
            TaskApp(taskViewModel)
        }
    }
}