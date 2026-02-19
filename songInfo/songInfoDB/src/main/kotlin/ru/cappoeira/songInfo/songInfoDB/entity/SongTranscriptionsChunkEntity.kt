package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.*
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import ru.cappoeira.songInfo.emptyString

@Entity
@Indexed
@Table(name = "song_translation_chunks")
data class SongTranscriptionsChunkEntity(
    @Id
    var id: String,

    @Column(nullable = true, name = "transcription", length = 10000)
    var transcription: String,

    @Column(nullable = true, name = "definition", length = 10000)
    var definition: String?,

    @ManyToOne
    @JoinColumn(name = "song_line_id")
    internal var transcriptionLine: SongLineEntity
) {
    constructor(): this(emptyString(), emptyString(), emptyString(), SongLineEntity())

    override fun toString(): String {
        return id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}