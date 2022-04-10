package tech.devscast.devsquotes.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuotesFile(
    val download_url: String,
    val encoding: String,
    val git_url: String,
    val name: String,
    val path: String,
    val size: Int,
    val url: String
) : Parcelable
