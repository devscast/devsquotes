package tech.devscast.devsquotes.util

import android.annotation.SuppressLint
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.data.model.QuotesFile
import java.io.BufferedReader
import java.io.FileReader

object CsvParser {
    @SuppressLint("NewApi")
    fun parse(quotesFile: QuotesFile): List<Quote> {

        val bufferedReader = BufferedReader(FileReader(quotesFile.download_url))
        val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT)
        val quotes = arrayListOf<Quote>()
        for (record in csvParser) {
            val en = record.get(0)
            val fr = record.get(1)
            val author = record.get(2)
            val role = record.get(3)

            val quote = Quote(en = en, fr = fr, author = author, role = role)
            quotes.add(quote)
        }
        return quotes
    }
}
