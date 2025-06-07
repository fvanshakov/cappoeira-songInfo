package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongLineDto

object AdminBoardFieldsMapper {

    private fun mapLines(text: String?): List<String> {
        return text
            ?.replace("\n\n", "\n")
            ?.split("\n")
            ?.filterNot { it.isEmpty() || it.isBlank() }
            ?: emptyList()
    }

    fun mapFieldsToDto(fields: Map<String, Any>): AdminBoardSongInfoDto? {
        val songName = fields[NAME] as? String ?: return null
        val videoUrl = fields[VIDEO_URL] as? String?
        val rawText = fields[TEXT] as? String?
        val textLines = mapLines(rawText)
        val rawTranslation = fields[TRANSLATION] as? String?
        val translationLines = mapLines(rawTranslation)
        val rawTranscription = fields[TRANSCRIPTION] as? String
        val transcriptionLines = mapLines(rawTranscription)
        val songLines = textLines.mapIndexed { index, textLine ->
            val isChoirPart = textLine.contains('*')
            val refinedTextLine = textLine.replace("*", "")
            val translationLine = translationLines.getOrNull(index).orEmpty()
            val transcriptionLine = transcriptionLines.getOrNull(index).orEmpty()
            val refinedTranscriptionLine = transcriptionLine.replace("*", "")
            AdminBoardSongLineDto(
                index = index,
                isChoirPart = isChoirPart,
                text = refinedTextLine,
                translation = translationLine,
                transcription = refinedTranscriptionLine
            )
        }
        return AdminBoardSongInfoDto(
            songName = songName,
            videoUrl = videoUrl,
            songType = AdminBoardSongInfoDto.SongType.CORRIDO,
            songLines = songLines
        )
    }

    private const val NAME = "Название"
    private const val VIDEO_URL = "Стриминг-ссылка"
    private const val TEXT = "Текст"
    private const val TRANSLATION = "Перевод"
    private const val TRANSCRIPTION = "Транскрипция"
}