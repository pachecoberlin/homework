package de.pacheco.homework.model

import com.google.firebase.firestore.IgnoreExtraProperties
import java.time.DayOfWeek

@IgnoreExtraProperties
data class Task(var name: String="", var periodic: Boolean=false, var weekDays: List<String>?=emptyList(), var owner: String="", var description: String="")
