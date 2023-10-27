package com.example.handa_huang_myruns5

//import android.R

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider


class ManualEntryDialog:DialogFragment(),DialogInterface.OnClickListener {

    companion object{

        const val defaultEntry = "nothing"
    }



    //accepts dialog type from ManualEntry class, and create dialogs according to accepted dialog type
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        lateinit var ret: Dialog
        val bundle = arguments

        val dialog_type = bundle?.getString(ManualEntry.DIALOG_KEY)
        val activity_type=bundle?.getString(ManualEntry.DIALOG_KEY)

        if (dialog_type != defaultEntry) {
            val currentText:EditText= EditText(requireActivity())
            val builder = AlertDialog.Builder(requireActivity())
            val view: View =
                requireActivity().layoutInflater.inflate(R.layout.manual_entry_dialog, null)

            val dialog_edit_text:EditText=view.findViewById(R.id.dialogEditText)


            if (dialog_type == "Duration"){
                builder.setTitle("Duration")
                dialog_edit_text.inputType=InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }

            else if (dialog_type == "Distance"){
                builder.setTitle("Distance")
                dialog_edit_text.inputType=InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }

            else if (dialog_type == "Calories"){
                builder.setTitle("Calories")
                dialog_edit_text.inputType=InputType.TYPE_CLASS_NUMBER
            }

            else if (dialog_type == "Heart Rates"){
                builder.setTitle("Heart Rates")
                dialog_edit_text.inputType=InputType.TYPE_CLASS_NUMBER
            }

            else{
                builder.setTitle("Comment")
                dialog_edit_text.hint = "How did it go? Notes here."
                dialog_edit_text.inputType=InputType.TYPE_CLASS_TEXT
            }
//
            builder.setView(view)
            builder.setPositiveButton("OK", this)
            builder.setNegativeButton("CANCEL", this)


            //prepare the input texts to be saved
            builder.setPositiveButton("OK",  DialogInterface.OnClickListener { dialog, which ->
                if (dialog_type != null) {
                    when (dialog_type){
                        "Duration"-> ManualEntry.currentDuration=dialog_edit_text.text.toString().toFloat()
                        "Distance"->ManualEntry.currentDistance=dialog_edit_text.text.toString().toFloat()
                        "Calories"->ManualEntry.currentCalories=dialog_edit_text.text.toString().toInt()
                        "Heart Rates"->ManualEntry.currentHeartRate=dialog_edit_text.text.toString().toInt()
                        "Comment"->ManualEntry.currentComment=dialog_edit_text.text.toString()

                    }

                }





            })





            ret = builder.create()

        }

        return ret
    }




    //the two buttons
    override fun onClick(dialog: DialogInterface?, which: Int) {

        if (which == DialogInterface.BUTTON_POSITIVE){

            Toast.makeText(activity, "ok clicked", Toast.LENGTH_LONG).show()
        }
        else if (which == DialogInterface.BUTTON_NEGATIVE){
            Toast.makeText(activity, "cancel clicked", Toast.LENGTH_LONG).show()
        }
    }
}