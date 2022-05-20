package tech.devscast.devsquotes.util

import android.annotation.SuppressLint
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.data.model.QuotesFile
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

object CsvParser {
    @SuppressLint("NewApi")
    fun parse(quotesFile: QuotesFile): List<Quote> {

        val url = URL(quotesFile.download_url)
        val bufferedReader =
            BufferedReader(InputStreamReader(url.openConnection().getInputStream()))
        val csvParser = CSVParser(
            bufferedReader,
            CSVFormat.DEFAULT.withQuote(null).withHeader("en", "fr", "author", "role")
                .withDelimiter('|')
        )
        val quotes = arrayListOf<Quote>()

        for (record in csvParser.records) {
            try {
                val en = record.get("en")
                val fr = record.get("fr")
                val author = record.get("author")
                val role = record.get("role")

                if (en != "\"en\"" && fr != "\"fr\"" && author != "\"author\"" && role != "\"role\"") {
                    val quote = Quote(en = en, fr = fr, author = author, role = role)
                    quotes.add(quote)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
        return quotes
    }
}
