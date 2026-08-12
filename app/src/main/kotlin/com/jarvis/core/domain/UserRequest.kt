package com.jarvis.core.domain

/**
 * Represents a request received by JARVIS.
 *
 * The request is intentionally independent of the input source.
 * A request may originate from voice, text, an Android client,
 * or a future external device.
 */
data class UserRequest(
    val id: String,
    val text: String,
    val source: RequestSource,
    val timestampEpochMillis: Long
) {
    init {
        require(id.isNotBlank()) {
            "Request id must not be blank."
        }

        require(text.isNotBlank()) {
            "Request text must not be blank."
        }

        require(timestampEpochMillis >= 0) {
            "Request timestamp must not be negative."
        }
    }
}

/**
 * Identifies where a JARVIS request originated.
 */
enum class RequestSource {
    TEXT,
    VOICE
}
