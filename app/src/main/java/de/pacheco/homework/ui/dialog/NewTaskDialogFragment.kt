package de.pacheco.homework.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import de.pacheco.homework.R

class NewTaskDialogFragment : DialogFragment() {
    private lateinit var listener: NewTaskDialogListener

    interface NewTaskDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NewTaskDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(("$context must implement NewTaskDialogListener"))
        }
    }
    /** The system calls this to get the DialogFragment's layout, regardless
    of whether it's being displayed as a dialog or an embedded fragment. */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.edit_task, container, false)
    }

    /** The system calls this only when creating the layout in a dialog. */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
//    val inputText = outlinedTextField.editText?.text.toString()
//
//    outlinedTextField.editText?.doOnTextChanged { inputText, _, _, _ ->
//        // Respond to input text change
//    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            // Build the dialog and set up the button click handlers
//            val builder = AlertDialog.Builder(it)
//
//            builder.setMessage("New Task")
//                .setPositiveButton("Save") { dialog, id ->
//                    listener.onDialogPositiveClick(this)
//                }
//                .setNegativeButton(
//                    "X"
//                ) { dialog, id ->
//                    listener.onDialogNegativeClick(this)
//                }
//
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }
}
