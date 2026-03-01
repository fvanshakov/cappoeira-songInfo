package ru.cappoeira.songInfo.songInfoDB.entity

import jakarta.persistence.*
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField
import ru.cappoeira.songInfo.emptyString

@Entity
@Table(name = "songs_tags")
data class SongTagEntity(
    @Id
    var id: String,

    @Column(nullable = false, name = TAG)
    var tag: String,

    @Column(nullable = false, name = IS_PLURAL)
    var isPlural: Boolean,

    @Column(nullable = false, name = TYPE)
    var type: String,

    @OneToMany(mappedBy = "tag", cascade = [CascadeType.ALL], orphanRemoval = true)
    var tagValues: MutableList<SongTagValueEntity>
) {

    override fun toString(): String {
        return tag
    }

    override fun hashCode(): Int {
        return tag.hashCode()
    }

    constructor(): this(emptyString(), emptyString(), false, emptyString(), mutableListOf())

    companion object {
        const val TAG = "tag"
        const val IS_PLURAL = "is_plural"
        const val TYPE = "type"
    }
}

@Entity
@Table(name = "songs_tag_values")
data class SongTagValueEntity(

    @Id
    var id: String,

    @FullTextField
    @Column(nullable = false, name = TAG_VALUE)
    var tagValue: String,

    @Column(nullable = false, name = TAG_STRING_VALUE)
    var tagStringValues: String,

    @ManyToOne
    @JoinColumn(name = "tag_id")
    internal var tag: SongTagEntity,

    @ManyToOne
    @JoinColumn(name = "song_id")
    internal var song: SongInfoEntity
) {

    override fun toString(): String {
        return tagValue + tagStringValues
    }

    override fun hashCode(): Int {
        return tagValue.hashCode() * 42 + tagStringValues.hashCode()
    }

    constructor(): this(emptyString(),  emptyString(), emptyString(), SongTagEntity(), SongInfoEntity())

    companion object {
        const val TAG_VALUE = "tagValue"
        const val TAG_STRING_VALUE = "tag_string_value"
    }
}