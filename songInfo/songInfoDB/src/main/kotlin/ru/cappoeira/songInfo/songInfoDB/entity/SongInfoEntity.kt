package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.*
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed
import ru.cappoeira.songInfo.emptyString


@Entity
@Indexed
@Table(name = "songs")
data class SongInfoEntity(
    @Id
    var id: String,

    @Column(nullable = false, name = NAME)
    var name: String,

    @FullTextField
    @Column(nullable = true, name = NORMALIZED_NAME)
    var normalizedName: String,

    @Column(nullable = true, name = VIDEO_URL)
    var videoUrl: String?,
) {
    constructor(): this(emptyString(), emptyString(),  emptyString(), null)

    companion object {
        const val NAME = "name"
        const val NORMALIZED_NAME = "normalizedName"
        const val VIDEO_URL = "videoUrl"
    }
}