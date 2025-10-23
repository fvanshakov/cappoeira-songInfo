package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongLineDto
import ru.cappoeira.songInfo.songInfoDB.entity.SongChunkEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongLineEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongTranscriptionsChunkEntity

object SongLineMapper {

    fun mapDtoToEntity(
        dto: AdminBoardSongLineDto,
        song: SongInfoEntity,
        definitions: Map<String, String>
    ): SongLineEntity {

        val songLineId = song.id + dto.index
        val result = SongLineEntity(
            id = songLineId,
            text = dto.text,
            translation = cleanLine(dto.translation),
            translationChunks = mutableListOf(),
            transcriptionChunks = mutableListOf(),
            isChoirPart = dto.isChoirPart,
            song = song,
            index = dto.index
        )
        val translationsChunks = mapLineToChunks(dto.translation, definitions, songLineId).map {
            it.copy(
                songLine = result
            )
        }
        val transcriptionChunks = mapLineToTransacriptionsChunks(dto.transcription, definitions, songLineId).map {
            it.copy(
                transcriptionLine = result
            )
        }
        result.translationChunks.addAll(translationsChunks)
        result.transcriptionChunks.addAll(transcriptionChunks)

        return result
    }

    private fun cleanLine(line: String): String {
        val pattern = Regex("""\|(.+?)\|\[[^\]]+]]""")
        val cleaned = pattern.replace(line) { match ->
            val word = match.groupValues[1]
            word
        }

        return cleaned
    }

    private fun mapLineToChunks(
        line: String,
        definitions: Map<String, String>,
        lineId: String
    ): List<SongChunkEntity> {
        val pattern = Regex("""\|(.+?)\|\[([^\]]+)]""")
        val chunks = mutableListOf<SongChunkEntity>()
        var lastIndex = 0

        for (match in pattern.findAll(line)) {
            val start = match.range.first
            val end = match.range.last + 1

            if (start > lastIndex) {
                val beforeText = line.substring(lastIndex, start)
                if (beforeText.isNotBlank()) {
                    chunks.add(
                        SongChunkEntity().apply {
                            text = beforeText
                            definition = null
                        }
                    )
                }
            }

            val word = match.groupValues[1]
            val definitionId = match.groupValues[2]

            chunks.add(
                SongChunkEntity().apply {
                    text = word
                    this.definition = definitions[definitionId]
                }
            )

            lastIndex = end
        }

        if (lastIndex < line.length) {
            val remaining = line.substring(lastIndex)
            if (remaining.isNotBlank()) {
                chunks.add(
                    SongChunkEntity().apply {
                        text = remaining
                        definition = null
                    }
                )
            }
        }

        return chunks.mapIndexed { index, songChunkEntity ->
            songChunkEntity.copy(id = lineId + index)
        }
    }

    private fun mapLineToTransacriptionsChunks(
        line: String,
        definitions: Map<String, String>,
        lineId: String
    ): List<SongTranscriptionsChunkEntity> {
        val pattern = Regex("""\|(.+?)\|\[([^\]]+)]""")
        val chunks = mutableListOf<SongTranscriptionsChunkEntity>()
        var lastIndex = 0

        for (match in pattern.findAll(line)) {
            val start = match.range.first
            val end = match.range.last + 1

            if (start > lastIndex) {
                val beforeText = line.substring(lastIndex, start)
                if (beforeText.isNotBlank()) {
                    val results = beforeText.split("(?<= )".toRegex())
                    results.forEach {
                        chunks.add(
                            SongTranscriptionsChunkEntity().apply {
                                transcription = it
                                definition = null
                            }
                        )
                    }
                }
            }

            val word = match.groupValues[1]
            val definitionId = match.groupValues[2]

            chunks.add(
                SongTranscriptionsChunkEntity().apply {
                    transcription = word
                    this.definition = definitions[definitionId]
                }
            )

            lastIndex = end
        }

        if (lastIndex < line.length) {
            val remaining = line.substring(lastIndex)
            if (remaining.isNotBlank()) {
                val results = remaining.split("(?<= )".toRegex())
                results.forEach {
                    chunks.add(
                        SongTranscriptionsChunkEntity().apply {
                            transcription = it
                            definition = null
                        }
                    )
                }
            }
        }

        return chunks.mapIndexed { index, songChunkEntity ->
            songChunkEntity.copy(id = lineId + index)
        }
    }
}