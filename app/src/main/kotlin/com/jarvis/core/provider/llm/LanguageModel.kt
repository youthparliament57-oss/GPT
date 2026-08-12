package com.jarvis.core.provider.llm

/**
 * Provider-independent contract for language-model interaction.
 *
 * The JARVIS brain communicates through this abstraction rather
 * than depending directly on a specific model provider or SDK.
 */
interface LanguageModel {

    /**
     * Sends a structured model request and returns the model result.
     *
     * Implementations are responsible for provider-specific
     * authentication, networking, serialization, retries and
     * provider error translation.
     */
    suspend fun generate(
        request: LanguageModelRequest
    ): LanguageModelResult
}

/**
 * Provider-independent representation of a model request.
 */
data class LanguageModelRequest(
    val systemInstruction: String,
    val userMessage: String,
    val conversation: List<ModelMessage> = emptyList()
) {
    init {
        require(systemInstruction.isNotBlank()) {
            "System instruction must not be blank."
        }

        require(userMessage.isNotBlank()) {
            "User message must not be blank."
        }
    }
}

/**
 * Represents one message in the model conversation context.
 */
data class ModelMessage(
    val role: ModelRole,
    val content: String
) {
    init {
        require(content.isNotBlank()) {
            "Model message content must not be blank."
        }
    }
}

/**
 * Roles understood by the provider-independent model layer.
 */
enum class ModelRole {
    USER,
    ASSISTANT
}

/**
 * Result returned by a language-model implementation.
 */
sealed interface LanguageModelResult {

    /**
     * The provider successfully generated a response.
     */
    data class Success(
        val text: String
    ) : LanguageModelResult {
        init {
            require(text.isNotBlank()) {
                "Model response text must not be blank."
            }
        }
    }

    /**
     * The provider could not generate a response.
     */
    data class Failure(
        val reason: String
    ) : LanguageModelResult {
        init {
            require(reason.isNotBlank()) {
                "Model failure reason must not be blank."
            }
        }
    }
}
