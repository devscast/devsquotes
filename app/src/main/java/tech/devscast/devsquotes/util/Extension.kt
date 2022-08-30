package tech.devscast.devsquotes.util

import tech.devscast.devsquotes.data.model.Quote

fun String.removeDoubleQuotes(): String {
    return removeSurrounding("\"")
}

fun Quote.getShareableText() : String = "${this.fr.removeDoubleQuotes()}\n-${this.author.removeDoubleQuotes()}\n\nEnvoyé depuis Devsquotes - https://quotes.devscast.tech"
