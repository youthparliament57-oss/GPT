package com.jarvis.core.domain

/**
 * Represents the outcome of processing a complete JARVIS task.
 *
 * This is intentionally separate from CapabilityResult because a
 * capability execution is only one part of a complete JARVIS task.
 */
sealed interface TaskResult {

    /**
     * The task completed successfully and produced a response.
     */
    data class Success(
        val response: JarvisResponse
    ) : TaskResult

    /**
     * The task could not be completed.
     */
    data class Failure(
        val requestId: String,
        val error: JarvisError
    ) : TaskResult {
        init {
            require(requestId.isNotBlank()) {
                "Task failure request id must not be blank."
            }
        }
    }
}
