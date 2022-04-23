package com.jyotirmayg.ezedemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.jyotirmayg.ezedemo.databinding.ActivityMainBinding
import com.jyotirmayg.ezelib.data.apiModel.DynamicUiResponse
import com.jyotirmayg.ezelib.view.CustomUi


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data?.getStringExtra(CustomUi.UI_DATA) != null) {
                val ui = data.getStringExtra(CustomUi.UI_DATA)
                Log.d("ant", ui.toString())

                val uiResponse: DynamicUiResponse = Gson().fromJson(
                    ui.toString(),
                    DynamicUiResponse::class.java
                )

                drawUi(uiResponse)
            }
        } else {
            val data = result.data
            if (data?.getStringExtra(CustomUi.ERROR) != null) {
                val message = data.getStringExtra(CustomUi.ERROR)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val idMap: HashMap<String, EditText> = HashMap()

    private fun drawUi(uiResponse: DynamicUiResponse) {
        Glide.with(this).load(uiResponse.logoUrl).into(mBinding.appCompatImageView)
        mBinding.headingText.text = uiResponse.headingText

        for (element in uiResponse.uiData){
            when(element.uiType){
                "label" -> {
                    val textView = TextView(this).apply { text = element.value }
                    mBinding.rootLinearLayout.addView(textView)
                }
                "edittext" -> {
                    val editText = EditText(this).apply {
                        hint = element.hint
                    }
                    idMap[element.key] = editText
                    mBinding.executePendingBindings()
                    mBinding.rootLinearLayout.addView(editText)
                }
                "button" -> {
                    val button = Button(this).apply {
                        hint = element.value
                        setOnClickListener {
                            val resultMap: HashMap<String, String> = HashMap()
                            idMap.forEach { (key, value) ->
                                Log.d("ant", "[Key] : $key [Value] : ${value.text}")
                                resultMap[key] = "${value.text}"
                            }
                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            intent.putExtra("result", resultMap)
                            intent.putExtra("heading", uiResponse.headingText)
                            startActivity(intent)
                        }
                    }
                    mBinding.rootLinearLayout.addView(button)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        val intent = Intent(this, CustomUi::class.java)
        getResult.launch(intent)
    }
}