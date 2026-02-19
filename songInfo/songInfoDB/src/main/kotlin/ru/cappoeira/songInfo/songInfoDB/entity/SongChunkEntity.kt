package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import ru.cappoeira.songInfo.emptyString

@Entity
@Indexed
@Table(name = "song_chunks")
data class SongChunkEntity(
    @Id
    var id: String,

    @Column(nullable = false, name = "text", length = 10000)
    var text: String,

    @Column(nullable = true, name = "definition", length = 10000)
    var definition: String?,

    @ManyToOne
    @JoinColumn(name = "song_line_id")
    internal var songLine: SongLineEntity
) {
    constructor(): this(emptyString(), emptyString(), emptyString(), SongLineEntity())

    override fun toString(): String {
        return id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}