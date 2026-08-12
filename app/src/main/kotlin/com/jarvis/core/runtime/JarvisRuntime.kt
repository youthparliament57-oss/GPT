package com.jarvis.core.runtime

import com.jarvis.core.domain.JarvisResponse
import com.jarvis.core.domain.UserRequest

/**
 * Main execution boundary for the JARVIS runtime.
 *
 * The application layer submits user requests to this runtime.
 * The runtime coordinates the internal JARVIS processing flow
 * without exposing implementation details to the application.
 */
interface JarvisRuntime {

    /**
     * Processes a complete user request and returns the resulting
     * JARVIS response.
     *
     * Failures are represented through the domain result model
     * used by the concrete runtime implementation.
     */
    suspend fun process(
        request: UserRequest
    ): Result<JarvisResponse>
}
