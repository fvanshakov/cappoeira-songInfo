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

    @FullTextField
    @Column(nullable = false, name = NAME)
    var name: String
) {
    constructor(): this(emptyString(), emptyString())

    companion object {
        const val NAME = "name"
    }
}