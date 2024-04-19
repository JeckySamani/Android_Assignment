package com.app.assignment.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ImageModel (
    @SerializedName("id")
    var id : String = "",
    @SerializedName("urls")
    var urls: Urls = Urls(),
): Parcelable {

    @Serializable
    @Parcelize
    data class Urls(
        @SerializedName("raw") var raw: String = "",
        @SerializedName("full") var full: String = "",
        @SerializedName("regular") var regular: String = "",
        @SerializedName("small") var small: String = "",
        @SerializedName("thumb") var thumb: String = "",
        @SerializedName("small_s3") var small_s3: String = "",
    ) : Parcelable

}