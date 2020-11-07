package com.roquebuarque.gdc.feature

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import java.util.*


class TimePickerFragment : DialogFragment() {

    lateinit var timeListener: TimePickerDialog.OnTimeSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(
            activity, timeListener, hour, minute, true
        )
    }


    fun setTimeSetListener(listener: TimePickerDialog.OnTimeSetListener) {
        timeListener = listener
    }

    companion object {
        fun newInstance(listener: TimePickerDialog.OnTimeSetListener): TimePickerFragment {
            val instance = TimePickerFragment()
            instance.setTimeSetListener(listener)
            return instance
        }
    }
}