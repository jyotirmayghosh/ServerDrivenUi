package com.jyotirmayg.ezelib.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.jyotirmayg.ezelib.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getResult = registerForActivityResult(StartActivityForResult()) { result ->
            if (result != null && result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data?.getStringExtra(CustomUi.UI_DATA) != null) {
                    val ui = data.getStringExtra(CustomUi.UI_DATA)
                    Log.d("demo", ui.toString())
                }
            }
        }

        findViewById<Button>(R.id.btnGet).setOnClickListener {
            val intent = Intent(this, CustomUi::class.java)
            getResult.launch(intent)
        }
    }
}