package tech.devscast.devsquotes.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubQuotesResponse(val quotesFiles: List<QuotesFile>) : Parcelable
