package com.jarvis.core.brain

import com.jarvis.core.domain.UserRequest
import com.jarvis.core.provider.llm.LanguageModel
import com.jarvis.core.provider.llm.LanguageModelRequest
import com.jarvis.core.provider.llm.LanguageModelResult

/**
 * Default implementation of the JARVIS intelligence boundary.
 *
 * This class coordinates request interpretation through the
 * provider-independent LanguageModel contract.
 *
 * It does not execute capabilities itself. When the model requests
 * a capability, the request is returned to the runtime as a
 * BrainResult.ExecuteCapability.
 */
class DefaultJarvisBrain(
    private val languageModel: LanguageModel,
    private val systemInstruction: String
) : JarvisBrain {

    init {
        require(systemInstruction.isNotBlank()) {
            "JARVIS system instruction must not be blank."
        }
    }

    override suspend fun process(
        request: UserRequest
    ): BrainResult {
        val modelRequest = LanguageModelRequest(
            systemInstruction = systemInstruction,
            userMessage = request.text
        )

        return when (
            val result = languageModel.generate(modelRequest)
        ) {
            is LanguageModelResult.FinalResponse -> {
                BrainResult.FinalResponse(
                    text = result.text
                )
            }

            is LanguageModelResult.CapabilityRequest -> {
                BrainResult.ExecuteCapability(
                    invocation = result.invocation
                )
            }

            is LanguageModelResult.Failure -> {
                BrainResult.Failure(
                    reason = result.reason
                )
            }
        }
    }
}
