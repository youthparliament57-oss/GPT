package com.jarvis.core.brain

import com.jarvis.core.capability.CapabilityInvocation
import com.jarvis.core.domain.UserRequest

/**
 * Defines the intelligence boundary of JARVIS.
 *
 * The brain is responsible for interpreting a user request and
 * determining the next operation required to produce a response.
 *
 * It must not directly execute capabilities.
 */
interface JarvisBrain {

    /**
     * Processes a user request using the available context and
     * returns the next reasoning outcome.
     */
    suspend fun process(
        request: UserRequest
    ): BrainResult
}

/**
 * Represents the result of one reasoning cycle.
 *
 * A reasoning cycle may either produce a final response or request
 * execution of a capability.
 */
sealed interface BrainResult {

    /**
     * JARVIS has enough information to return a final response.
     */
    data class FinalResponse(
        val text: String
    ) : BrainResult {
        init {
            require(text.isNotBlank()) {
                "Final response text must not be blank."
            }
        }
    }

    /**
     * JARVIS requires a capability to be executed before reasoning
     * can continue.
     */
    data class ExecuteCapability(
        val invocation: CapabilityInvocation
    ) : BrainResult
}
