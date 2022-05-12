package de.pacheco.homework.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.pacheco.homework.adapter.TaskAdapter
import de.pacheco.homework.databinding.FragmentTaskOverviewBinding

/**
 * A placeholder fragment containing a simple view.
 */
class TaskOverviewFragment : Fragment(), TaskAdapter.OnTaskSelectedListener {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentTaskOverviewBinding? = null

    lateinit var firestore: FirebaseFirestore
    lateinit var query: Query

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var adapter: TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this)[PageViewModel::class.java].apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTaskOverviewBinding.inflate(inflater, container, false)
        val root = binding.root

        val textView: TextView = binding.sectionLabel
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true)

        // Firestore
        firestore = Firebase.firestore

        // Get ${LIMIT} restaurants
        query = firestore.collection("Tasks")

        // RecyclerView
        adapter = object : TaskAdapter(query, this@TaskOverviewFragment) {
            override fun onDataChanged() {
                // Show/hide content if the query returns empty.
                if (itemCount == 0) {
                    binding.recyclerTasks.visibility = View.GONE
                    binding.viewEmpty.visibility = View.VISIBLE
                } else {
                    binding.recyclerTasks.visibility = View.VISIBLE
                    binding.viewEmpty.visibility = View.GONE
                }
            }

            override fun onError(e: FirebaseFirestoreException) {
                // Show a snackbar on errors
                Snackbar.make(binding.root,
                    "Error: check logs for info.", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.recyclerTasks.layoutManager = LinearLayoutManager(context)
        binding.recyclerTasks.adapter = adapter
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): TaskOverviewFragment {
            return TaskOverviewFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        // Start sign in if necessary
//        if (shouldStartSignIn()) {
//            startSignIn()
//            return
//        }

        // Apply filters
//        onFilter(viewModel.filters)

        // Start listening for Firestore updates
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onTaskSelected(restaurant: DocumentSnapshot) {
        TODO("Not yet implemented")
    }
}