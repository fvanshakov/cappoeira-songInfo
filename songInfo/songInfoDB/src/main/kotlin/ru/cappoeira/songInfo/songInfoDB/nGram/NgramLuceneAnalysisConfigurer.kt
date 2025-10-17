package ru.cappoeira.songInfo.songInfoDB.nGram

import org.apache.lucene.analysis.core.LowerCaseFilterFactory
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory
import org.apache.lucene.analysis.standard.StandardTokenizerFactory
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer
import org.springframework.context.annotation.Configuration

@Configuration
open class NgramLuceneAnalysisConfigurer : LuceneAnalysisConfigurer {
    override fun configure(context: LuceneAnalysisConfigurationContext) {
        context.analyzer(NGRAM_NAME).custom()
            .tokenizer(StandardTokenizerFactory::class.java)
            .tokenFilter(LowerCaseFilterFactory::class.java)
            .tokenFilter(EdgeNGramFilterFactory::class.java)
            .param("minGramSize", "3")
            .param("maxGramSize", "7")
    }

    companion object {
        const val NGRAM_NAME = "edge_ngram"
    }
}