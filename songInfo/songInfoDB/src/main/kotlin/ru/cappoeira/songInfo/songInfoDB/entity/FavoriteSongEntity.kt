package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.*
import ru.cappoeira.songInfo.emptyString

@Entity
@Table(name = "favorite_songs")
data class FavoriteSongEntity(
    @Id
    @Column(nullable = false)
    var userId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    var song: SongInfoEntity,

    @Column(nullable = false)
    var addedAt: Long = System.currentTimeMillis()
) {
    constructor(): this(emptyString(), SongInfoEntity())
}