package com.example.tdd

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etCelsius = findViewById<EditText>(R.id.acet_celsius)
        val etFahrenheit = findViewById<EditText>(R.id.acet_fahrenheit)
        val btnConvert = findViewById<Button>(R.id.acbtn_convert)

        btnConvert.setOnClickListener {
            val c = etCelsius.text.toString().toInt()
            etFahrenheit.setText("${TemperatureConverter.celsiusToFahrenheit(c)}")
            etCelsius.text.clear()
        }
    }
}