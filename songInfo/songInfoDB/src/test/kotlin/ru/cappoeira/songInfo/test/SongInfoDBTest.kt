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

@DataJpaTest
@ContextConfiguration(classes = [TestJpaConfig::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SongInfoDBTest {

    @Autowired
    lateinit var repo: SongInfoRepo

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
        assertEquals(savedSong.id, foundSong?.id)
        assertEquals(savedSong.videoUrl, foundSong?.videoUrl)
    }

    @Test
    fun `should return null when song not found`() {
        val song = repo.findByName("some song")
        assertNull(song)
    }
}