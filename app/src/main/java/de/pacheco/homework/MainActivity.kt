package de.pacheco.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import de.pacheco.homework.databinding.ActivityMainBinding
import de.pacheco.homework.ui.dialog.NewTaskDialogFragment
import de.pacheco.homework.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity(), NewTaskDialogFragment.NewTaskDialogListener {

    private var isLargeLayout: Boolean = false
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isLargeLayout = resources.getBoolean(R.bool.large_layout)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Clicked", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            showDialog()
//            MaterialAlertDialogBuilder(this).show()

//            val db =Firebase.firestore
//            val task = Task("test", false, listOf("Monday"),"Pache","do something")
//            db.collection("Tasks")
//                .document()
//                .set(task)
//                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
//                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

        }
    }

    fun showDialog() {
        val fragmentManager = supportFragmentManager
        val newFragment = NewTaskDialogFragment()
        if (isLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "NewTaskDialog")
        } else {
            // The device is smaller, so show the fragment fullscreen
            val transaction = fragmentManager.beginTransaction()
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
    }
}