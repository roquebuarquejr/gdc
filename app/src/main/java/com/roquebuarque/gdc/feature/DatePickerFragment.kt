package com.roquebuarque.gdc.feature

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickerFragment : DialogFragment(){

    lateinit var dateListener: DatePickerDialog.OnDateSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        // Create a new instance of DatePickerDialog and return it.
        return DatePickerDialog(activity!!, dateListener, year, month, day)
    }

    fun setDateSetListener(listener: DatePickerDialog.OnDateSetListener){
        dateListener = listener
    }

    companion object{
        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerFragment {
            val instance = DatePickerFragment()
            instance.setDateSetListener(listener)
            return instance
        }
    }
}