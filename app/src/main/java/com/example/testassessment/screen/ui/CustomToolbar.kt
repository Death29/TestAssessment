package com.example.testassessment.screen.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.testassessment.R
import com.example.testassessment.databinding.CustomToolbarBinding

class CustomToolbar : ConstraintLayout {

    /**
     * Variables
     */
    //State
    private lateinit var binding: CustomToolbarBinding

    /**
     * Constructors
     */
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    /**
     * Initialization
     */

    private fun init(context: Context, attributeSet: AttributeSet?) {
        binding = CustomToolbarBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this, true)
        )
    }

    fun toolbar():Toolbar{
        return binding.toolbar
    }

    fun setTitle(title: String){
        binding.tvTitleToolbar.text = title
    }
}