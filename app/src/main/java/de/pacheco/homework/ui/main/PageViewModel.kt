package de.pacheco.homework.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.pacheco.homework.model.Task
import java.time.DayOfWeek

class PageViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val text: LiveData<String> = Transformations.map(_tasks) {
        val sb = StringBuilder()
        it.forEach { task -> sb.append("$task\n\n") }
        sb.toString()
    }

    fun setIndex(index: Int) {
        //TODO load Tasks
        _tasks.value = listOf(
            Task("Saugen", true, listOf(DayOfWeek.THURSDAY.toString()), "Alex", "Desciption text")
        )
    }
}