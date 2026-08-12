package com.jarvis.core.provider.llm

import com.jarvis.core.capability.CapabilityArguments
import com.jarvis.core.capability.CapabilityInvocation

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
 *
 * A model can either provide a final response or request execution
 * of a JARVIS capability.
 */
sealed interface LanguageModelResult {

    /**
     * The model generated a final response for the user.
     */
    data class FinalResponse(
        val text: String
    ) : LanguageModelResult {
        init {
            require(text.isNotBlank()) {
                "Model response text must not be blank."
            }
        }
    }

    /**
     * The model decided that a capability must be executed before
     * a final response can be produced.
     */
    data class CapabilityRequest(
        val invocation: CapabilityInvocation
    ) : LanguageModelResult

    /**
     * The provider could not generate a valid result.
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
