package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.*
import ru.cappoeira.songInfo.emptyString


@Entity
@Table(name = "songs")
data class SongInfoEntity(
    @Id
    var id: String,

    @Column(nullable = false)
    var name: String
) {
    constructor(): this(emptyString(), emptyString())
}