package tech.devscast.devsquotes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuotesFile(
    @SerializedName("download_url")
    val download_url: String,

    @SerializedName("encoding")
    val encoding: String,

    @SerializedName("git_url")
    val git_url: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("path")
    val path: String,

    @SerializedName("size")
    val size: Int,

    @SerializedName("url")
    val url: String
) : Parcelable
