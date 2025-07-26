package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.*
import org.hibernate.search.engine.backend.types.ObjectStructure
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded
import ru.cappoeira.songInfo.emptyString
import ru.cappoeira.songInfo.songInfoDB.entity.SongLineEntity.Companion.TEXT
import ru.cappoeira.songInfo.songInfoDB.entity.SongLineEntity.Companion.TRANSLATION
import ru.cappoeira.songInfo.songInfoDB.nGram.NgramLuceneAnalysisConfigurer.Companion.NGRAM_NAME


@Entity
@Indexed
@Table(name = "songs")
data class SongInfoEntity(
    @Id
    var id: String,

    @Column(nullable = false, name = NAME)
    var name: String,

    @FullTextField(analyzer = NGRAM_NAME)
    @Column(nullable = true, name = NORMALIZED_NAME)
    var normalizedName: String,

    @Column(nullable = true, name = VIDEO_URL)
    var videoUrl: String?,

    @FullTextField
    @Column(nullable = false, name = SONG_TYPE)
    var songType: String,

    @IndexedEmbedded(includePaths = [TEXT, TRANSLATION], includeDepth = 1, structure = ObjectStructure.NESTED)
    @OneToMany(mappedBy = "song", cascade = [CascadeType.ALL])
    var songLines: MutableList<SongLineEntity>,

    @OneToMany(mappedBy = "song", cascade = [CascadeType.ALL])
    var tagValues: MutableList<SongTagValueEntity>
) {
    constructor(): this(emptyString(), emptyString(),  emptyString(),  null, emptyString(), mutableListOf(), mutableListOf())

    companion object {
        const val NAME = "name"
        const val NORMALIZED_NAME = "normalizedName"
        const val VIDEO_URL = "videoUrl"
        const val SONG_TYPE = "songType"
    }
}