package com.jyotirmayg.ezelib.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.jyotirmayg.ezelib.R
import com.jyotirmayg.ezelib.data.apiModel.DynamicUiResponse
import com.jyotirmayg.ezelib.data.repository.UiRepo
import com.jyotirmayg.ezelib.network.ApiBuilder
import com.jyotirmayg.ezelib.network.Status
import com.jyotirmayg.ezelib.util.Coroutines
import kotlinx.coroutines.*


class CustomUi : AppCompatActivity() {

    val uiRepo = UiRepo(ApiBuilder.API_SERVICE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_ui)

        getUi()
    }

    fun getUi() {
        CoroutineScope(Dispatchers.Main).launch {
            val apiResult = uiRepo.getCustomUi()
            when (apiResult.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    Log.d("demo", "Ui fetched")
                    val response = apiResult.data
                    if (response != null) {
                        sendResult(response)
                    } else {
                        sendError(apiResult.message)
                    }
                }
                Status.ERROR -> {
                    Log.e("demo","Error Message: ${apiResult.message}")
                    sendError(apiResult.message)
                }
            }
        }
    }

    private fun sendError(message: String?) {
        val i = Intent()
        i.putExtra(ERROR, message)
        this.setResult(Activity.RESULT_CANCELED, i)
        this.finish()
    }

    private fun sendResult(response: DynamicUiResponse) {
        val data = Gson().toJson(response)
        val i = Intent()
        i.putExtra(UI_DATA, data)

        this@CustomUi.setResult(Activity.RESULT_OK, i)
        this@CustomUi.finish()
    }

    companion object {
        const val UI_DATA = "ui-data"
        const val ERROR = "error"
    }
}