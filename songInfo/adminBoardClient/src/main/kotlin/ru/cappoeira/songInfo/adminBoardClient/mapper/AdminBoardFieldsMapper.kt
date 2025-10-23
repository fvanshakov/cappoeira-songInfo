package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongLineDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardTagDao
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardTagsDao

object AdminBoardFieldsMapper {

    private fun mapLines(text: String?): List<String> {
        return text
            ?.replace("\n\n", "\n")
            ?.split("\n")
            ?.filterNot { it.isEmpty() || it.isBlank() }
            ?: emptyList()
    }

    fun mapFieldsToDto(fields: Map<String, Any>): AdminBoardSongInfoDto {
        val songName = fields[NAME] as? String ?: ""
        val videoUrl = fields[VIDEO_URL] as? String?
        val rawText = fields[TEXT] as? String?
        val textLines = mapLines(rawText)
        val rawTranslation = fields[TRANSLATION] as? String?
        val translationLines = mapLines(rawTranslation)
        val rawTranscription = fields[TRANSCRIPTION] as? String
        val optimalTransitionsRaw = fields[OPTIMAL_FOLLOWINGS]
        val optimalTransitions = optimalTransitionsRaw?.let {
            (it as? String)?.split(',')?.toList()
        } ?: emptyList()
        val transcriptionLines = mapLines(rawTranscription)
        val songLines = textLines.mapIndexed { index, textLine ->
            val isChoirPart = textLine.contains('*')
            val refinedTextLine = textLine.replace("*", "")
            val translationLine = translationLines.getOrNull(index).orEmpty()
            val refinedTranslationLine = translationLine.replace("*", "")
            val transcriptionLine = transcriptionLines.getOrNull(index).orEmpty()
            val refinedTranscriptionLine = transcriptionLine.replace("*", "")
            AdminBoardSongLineDto(
                index = index,
                isChoirPart = isChoirPart,
                text = refinedTextLine,
                translation = refinedTranslationLine,
                transcription = refinedTranscriptionLine,
            )
        }
        val tagsWithValues = mutableMapOf<String, MutableList<String>>()
        TAGS.forEach { tagKey ->
            val singleValue = (fields[tagKey] as? String)?.let { mutableListOf(it) }
            val multipleValues = (fields[tagKey] as? List<String>)?.toMutableList()
            var existingValues: MutableList<String>? = tagsWithValues[tagKey]
            val newValues: MutableList<String> = singleValue ?: multipleValues ?: mutableListOf()
            if (existingValues == null) {
                existingValues = newValues
            } else {
                existingValues.addAll(newValues)
            }
            tagsWithValues[tagKey] = existingValues
        }
        val tags = tagsWithValues.map { (key, values) ->
            key to AdminBoardTagDao(
                tagValues = values,
                isPlural = values.size > 1
            )
        }.toMap()
        return AdminBoardSongInfoDto(
            songName = songName,
            videoUrl = videoUrl,
            songType = AdminBoardSongInfoDto.SongType.CORRIDO,
            songLines = songLines,
            tags = AdminBoardTagsDao(
                tags
            ),
            optimalTransitions = optimalTransitions,
            isVisible = fields[VISIBILITY] == true,
            warning = fields[WARNING] as? String
        )
    }

    private const val NAME = "Название"
    private const val VIDEO_URL = "Стриминг-ссылка"
    private const val TEXT = "Текст"
    private const val TRANSLATION = "Перевод"
    private const val TRANSCRIPTION = "Транскрипция"
    private const val WARNING = "Предупреждение"
    private const val OPTIMAL_FOLLOWINGS = "Оптимальные переходы"
    private const val VISIBILITY = "Видимость в приложении"
    private val TAGS = listOf(
        "Гармония",
        "Скорость",
        "Сложность солисту",
        "Сложность хора",
        "Темы текста"
    )
}