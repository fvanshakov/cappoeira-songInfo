package ru.cappoeira.songInfo.test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import ru.cappoeira.songInfo.di.TestJpaConfig
import ru.cappoeira.songInfo.encodeToBase64
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.repository.SongInfoRepo
import ru.cappoeira.songInfo.songInfoDB.repository.fullText.SongInfoFullTextRepo

/**
 * Эти тесты нужно запускать с включенным докером
 */
@DataJpaTest
@ContextConfiguration(classes = [TestJpaConfig::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SongInfoDBTest {

    @Autowired
    lateinit var repo: SongInfoRepo

    @Autowired
    lateinit var fullTextRepo: SongInfoFullTextRepo

    @BeforeEach
    fun setup() {
        repo.deleteAll()
    }

    @Test
    fun `should save and retrieve song`() {
        val songInfoEntity = SongInfoEntity(id = encodeToBase64("some song"), name = "some song", videoUrl = "some url")
        val savedSong = repo.save(songInfoEntity)

        assertNotNull(savedSong.id)
        assertEquals("some song", savedSong.name)

        val foundSong = repo.findByName("some song")
        assertNotNull(foundSong)
        assertEquals(foundSong?.id, savedSong.id)
        assertEquals(foundSong?.videoUrl, savedSong.videoUrl)
    }

    @Test
    fun `should return null when song not found`() {
        val song = repo.findByName("some song")
        assertNull(song)
    }

    @Test
    fun `should change song url`() {
        val songInfoEntity = SongInfoEntity(id = encodeToBase64("some song"), name = "some song", videoUrl = "some url")

        repo.save(songInfoEntity)
        repo.save(songInfoEntity.copy(videoUrl = "newUrl"))

        val foundSong = repo.findByName("some song")
        assertEquals("newUrl", foundSong?.videoUrl)
    }

    @Test
    fun `should return all songs`() {
        val songInfoEntity = SongInfoEntity(id = encodeToBase64("some song"), name = "some song", videoUrl = "some url")
        val otherInfoEntity = SongInfoEntity(id = encodeToBase64("other song"), name = "other song", videoUrl = "other url")

        repo.save(songInfoEntity)
        repo.save(otherInfoEntity)

        val foundSongs = repo.findAll()
        assertEquals(foundSongs.size, 2)
        assertEquals(true, foundSongs.contains(songInfoEntity))
        assertEquals(true, foundSongs.contains(otherInfoEntity))
    }
}