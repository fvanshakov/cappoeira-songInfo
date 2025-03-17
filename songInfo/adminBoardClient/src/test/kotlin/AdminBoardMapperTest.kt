import org.junit.jupiter.api.Test
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.mapper.AdminBoardFieldsMapper
import ru.cappoeira.songInfo.loadJsonAsMap
import kotlin.test.assertEquals

class AdminBoardMapperTest {

    val jsonMap = loadJsonAsMap("airtableResultWithOffset.json")
    val records = jsonMap["records"] as? List<Map<String, Any>>

    @Test
    fun `test mapper returns right dto when there is a song name`() {
        val result = (records?.get(0)?.get("fields") as? Map<String, Any>)
            ?.let(AdminBoardFieldsMapper::mapFieldsToDto)

        val expectedResult = AdminBoardSongInfoDto("Первая песня")

        assertEquals(expected = expectedResult, actual =  result)
    }

    @Test
    fun `test mapper returns null when there is no song name`() {
        val result = (records?.get(1)?.get("fields") as? Map<String, Any>)
            ?.let(AdminBoardFieldsMapper::mapFieldsToDto)

        val expectedResult = null

        assertEquals(expected = expectedResult, actual =  result)
    }
}