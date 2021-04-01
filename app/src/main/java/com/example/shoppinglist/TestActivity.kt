package com.example.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.shoppinglist.customviews.HorizontalProgressView

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val horizontalProgressView = findViewById<HorizontalProgressView>(R.id.horizontalProgressView)
        val button = findViewById<Button>(R.id.button)


        button.setOnClickListener {

            horizontalProgressView.progress = 70
        }
    }
}