package com.jyotirmayg.ezelib.data.apiModel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DynamicUiResponse(
    @SerializedName("logo-url") val logoUrl: String,
    @SerializedName("heading-text") val headingText: String,
    @SerializedName("uidata") val uiData: ArrayList<UiData>
): Parcelable

@Parcelize
data class UiData(
    @SerializedName("uitype") val uiType: String,
    @SerializedName("value") val value: String,
    @SerializedName("key") val key: String,
    @SerializedName("hint") val hint: String,
): Parcelable