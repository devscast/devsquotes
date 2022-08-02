package tech.devscast.devsquotes.util

fun String.removeDoubleQuotes(): String {
    return removeSurrounding("\"")
}
