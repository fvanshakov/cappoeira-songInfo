package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.search.engine.backend.types.ObjectStructure
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded
import ru.cappoeira.songInfo.emptyString
import ru.cappoeira.songInfo.songInfoDB.entity.SongLineEntity.Companion.TEXT
import ru.cappoeira.songInfo.songInfoDB.entity.SongLineEntity.Companion.TRANSLATION
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagValueEntity.Companion.TAG_VALUE
import ru.cappoeira.songInfo.songInfoDB.nGram.NgramLuceneAnalysisConfigurer.Companion.NGRAM_NAME

@Embeddable
data class OptimalTransition(
    @Column(name = "song_id")
    var songId: String,

    @Column(name = "song_name", length = 1000)
    var songName: String
) {
    constructor(): this(emptyString(), emptyString())
}


@Entity
@Indexed
@Table(name = "songs")
data class SongInfoEntity(
    @Id
    var id: String,

    @Column(nullable = false, name = NAME, length = 1000)
    var name: String,

    @FullTextField(analyzer = NGRAM_NAME)
    @Column(nullable = true, name = NORMALIZED_NAME, length = 1000)
    var normalizedName: String,

    @Column(nullable = true, name = VIDEO_URL, length = 1000)
    var videoUrl: String?,

    @FullTextField
    @Column(nullable = false, name = SONG_TYPE)
    var songType: String,

    @IndexedEmbedded(includePaths = [TEXT, TRANSLATION], includeDepth = 1, structure = ObjectStructure.NESTED)
    @OneToMany(mappedBy = "song", cascade = [CascadeType.ALL])
    var songLines: MutableList<SongLineEntity>,

    @ElementCollection
    @CollectionTable(
        name = "optimal_transitions",
        joinColumns = [JoinColumn(name = "id")]
    )
    var optimalTransitions: MutableList<OptimalTransition>,

    @GenericField
    @Column(nullable = false, name = IS_VISIBLE)
    var isVisible: Boolean,

    @GenericField
    @Column(nullable = true, name = WARNING, length = 1000)
    var warning: String?,

    @IndexedEmbedded(includePaths = [TAG_VALUE], includeDepth = 1, structure = ObjectStructure.NESTED)
    @OneToMany(mappedBy = "song", cascade = [CascadeType.ALL])
    var tagValues: MutableList<SongTagValueEntity>
) {
    constructor(): this(emptyString(), emptyString(),  emptyString(),  null, emptyString(), mutableListOf(), mutableListOf(), true, null, mutableListOf())

    override fun toString(): String {
        return id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        const val NAME = "name"
        const val NORMALIZED_NAME = "normalizedName"
        const val VIDEO_URL = "videoUrl"
        const val SONG_TYPE = "songType"
        const val IS_VISIBLE = "isVisible"
        const val WARNING = "warning"
    }
}