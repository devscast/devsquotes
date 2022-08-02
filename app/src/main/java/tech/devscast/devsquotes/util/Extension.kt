package tech.devscast.devsquotes.util

import android.R.attr.x


fun String.removeDoubleQuotes(): String {
    return removeSurrounding("\"")
}