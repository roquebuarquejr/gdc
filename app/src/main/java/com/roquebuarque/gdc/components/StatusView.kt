package com.roquebuarque.gdc.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.roquebuarque.gdc.R
import com.roquebuarque.gdc.feature.task.data.entity.Status

class StatusView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    fun setStatus(status: Status) {
        val bg = when (status) {
            Status.TODO, Status.UNDEFINED-> R.drawable.rounded_blue
            Status.PROGRESS -> R.drawable.rounded_orange
            Status.DONE -> R.drawable.rounded_green
        }
        setBackgroundResource(bg)
    }
}