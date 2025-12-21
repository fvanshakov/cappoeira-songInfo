package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.*
import java.io.Serializable

@Embeddable
data class FavoriteSongId(
    var userId: String = "",
    var songId: String = ""
) : Serializable

@Entity
@Table(name = "favorite_songs")
data class FavoriteSongEntity(
    @EmbeddedId
    var id: FavoriteSongId = FavoriteSongId(),

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("songId")
    @JoinColumn(name = "song_id", nullable = false)
    var song: SongInfoEntity = SongInfoEntity(),

    @Column(nullable = false)
    var addedAt: Long = System.currentTimeMillis()
) {
    constructor(): this(FavoriteSongId(), SongInfoEntity())
}