package pl.inpost.recruitmenttask

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

class TestUtils {
    companion object {
        fun isValidJson(jsonString: String?): Boolean {
            return runCatching {
                val jsonElement: JsonElement =
                    jsonString?.let { Json.parseToJsonElement(it) } ?: return false
                // Optionally, you can further check the structure or content of the parsed JSON
                true
            }.onFailure {
                // If an exception occurs during parsing, the JSON is considered invalid
                return false
            }.isSuccess
        }
    }
}