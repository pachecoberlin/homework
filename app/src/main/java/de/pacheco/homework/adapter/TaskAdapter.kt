package de.pacheco.homework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import de.pacheco.homework.databinding.ItemTaskBinding
import de.pacheco.homework.model.Task

open class TaskAdapter(query: Query, private val listener: OnTaskSelectedListener) :
    FirestoreAdapter<TaskAdapter.ViewHolder>(query) {

    interface OnTaskSelectedListener {

        fun onTaskSelected(restaurant: DocumentSnapshot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position), listener)
    }

    class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            snapshot: DocumentSnapshot,
            listener: OnTaskSelectedListener?
        ) {

            val task = snapshot.toObject<Task>() ?: return

            val resources = binding.root.resources

            binding.taskName.text = task.name
            binding.taskPeriodic.text = if (task.periodic) "periodic" else "single"
            binding.taskDescription.text = "${task.weekDays}\n${task.description}"
            binding.taskOwner.text = task.owner

            // Click listener
            binding.root.setOnClickListener {
                listener?.onTaskSelected(snapshot)
            }
        }
    }
}