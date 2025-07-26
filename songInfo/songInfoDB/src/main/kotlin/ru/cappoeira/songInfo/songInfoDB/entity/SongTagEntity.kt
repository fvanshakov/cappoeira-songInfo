package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.*
import ru.cappoeira.songInfo.emptyString

@Entity
@Table(name = "songs_tags")
data class SongTagEntity(
    @Id
    @Column(nullable = false, name = TAG)
    var tag: String,

    @Column(nullable = false, name = IS_PLURAL)
    var isPlural: Boolean,

    @OneToMany(mappedBy = "tag", cascade = [CascadeType.ALL])
    var tagValues: MutableList<SongTagValueEntity>
) {

    constructor(): this(emptyString(), false, mutableListOf())

    companion object {
        const val TAG = "tag"
        const val IS_PLURAL = "is_plural"
    }
}

@Entity
@Table(name = "songs_tag_values")
data class SongTagValueEntity(
    @Id
    @Column(nullable = false, name = TAG_VALUE)
    var tagValue: String,

    @ManyToOne
    @JoinColumn(name = "tag_id")
    var tag: SongTagEntity,

    @ManyToOne
    @JoinColumn(name = "song_id")
    var song: SongInfoEntity
) {

    constructor(): this(emptyString(), SongTagEntity(), SongInfoEntity())

    companion object {
        const val TAG_VALUE = "tag_value"
    }
}