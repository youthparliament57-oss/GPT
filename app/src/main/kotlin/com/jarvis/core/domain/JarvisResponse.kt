package com.jarvis.core.domain

/**
 * Represents the final response produced by JARVIS.
 *
 * The response is independent of the output mechanism.
 * It may later be delivered through text, speech, UI,
 * or another client.
 */
data class JarvisResponse(
    val requestId: String,
    val text: String,
    val source: ResponseSource
) {
    init {
        require(requestId.isNotBlank()) {
            "Response request id must not be blank."
        }

        require(text.isNotBlank()) {
            "Response text must not be blank."
        }
    }
}

/**
 * Identifies how the response was produced.
 */
enum class ResponseSource {
    MODEL,
    SYSTEM
}
