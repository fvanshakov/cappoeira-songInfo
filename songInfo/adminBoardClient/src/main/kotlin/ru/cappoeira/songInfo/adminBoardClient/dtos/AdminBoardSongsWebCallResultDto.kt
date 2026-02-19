package ru.cappoeira.songInfo.adminBoardClient.dtos

import ru.cappoeira.songInfo.adminBoardClient.mapper.AdminBoardFieldsMapper

data class AdminBoardSongsWebCallResultDto(
    val list: List<Map<String, Any>>
) {
    var records: List<AdminBoardSongInfoDto> = emptyList()

    fun mapRecords() {
        records = list.map { params ->
            AdminBoardFieldsMapper.mapFieldsToDto(params)
        }
    }
}