package com.jyotirmayg.ezedemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jyotirmayg.ezedemo.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        mBinding = ActivityResultBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        mBinding.headingText.text = intent.getStringExtra("heading")

        val hashMap = intent.getSerializableExtra("result") as HashMap<*, *>
        hashMap.forEach { (key, value) ->
            Log.d("ant", "[Key] : $key [Value] : $value")
            val labelText = TextView(this).apply { text = "$key:" }
            val valueText = EditText(this).apply {
                setText("$value", TextView.BufferType.EDITABLE)
                isEnabled = false
            }

            mBinding.rootLinearLayout.addView(labelText)
            mBinding.rootLinearLayout.addView(valueText)
        }

        mBinding.backImageView.setOnClickListener {
            finish()
        }
    }
}