package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.*
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import ru.cappoeira.songInfo.emptyString
import ru.cappoeira.songInfo.songInfoDB.nGram.NgramLuceneAnalysisConfigurer.Companion.NGRAM_NAME

@Entity
@Indexed
@Table(name = "songs_lines")
data class SongLineEntity(
    @Id
    var id: String,

    @Column(nullable = false, name = INDEX)
    var index: Int,

    @FullTextField(analyzer = NGRAM_NAME)
    @Column(nullable = false, name = TEXT, length = 1000)
    var text: String,

    @OneToMany(mappedBy = "songLine", cascade = [CascadeType.ALL])
    var translationChunks: MutableList<SongChunkEntity>,

    @FullTextField(analyzer = NGRAM_NAME)
    @Column(nullable = false, name = TRANSLATION, length = 1000)
    var translation: String,

    @OneToMany(mappedBy = "transcriptionLine", cascade = [CascadeType.ALL])
    var transcriptionChunks: MutableList<SongTranscriptionsChunkEntity>,

    @Column(nullable = false, name = IS_CHOIR_PART)
    var isChoirPart: Boolean,

    @ManyToOne
    @JoinColumn(name = "song_id")
    internal var song: SongInfoEntity
) {

    constructor(): this(emptyString(), 0,  emptyString(), mutableListOf(),  emptyString(), mutableListOf(), false, SongInfoEntity())

    companion object {
        const val INDEX = "index"
        const val TEXT = "text"
        const val IS_CHOIR_PART = "isChoirPart"
        const val TRANSLATION = "translation"
        const val TRANSCRIPTION = "transcription"
    }
}