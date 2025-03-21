package ru.cappoeira.songInfo.songInfoDB.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.repository.SongInfoRepo

@Service
class SongInfoService {

    @Autowired
    private lateinit var repo: SongInfoRepo

    fun saveSongs(songs: List<SongInfoEntity>) {
        repo.saveAll(songs)
    }

    fun getSongByName(name: String): SongInfoEntity? {
        return repo.getReferenceById(name)
    }
}