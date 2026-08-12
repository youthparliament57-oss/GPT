package com.jarvis.core.runtime

import com.jarvis.core.capability.CapabilityInvocation
import com.jarvis.core.capability.CapabilityResult

/**
 * Executes concrete capability invocations.
 *
 * The executor is responsible for connecting an invocation to the
 * registered capability implementation. It does not decide which
 * capability should be used.
 */
interface TaskExecutor {

    /**
     * Executes the supplied capability invocation.
     *
     * Implementations must return an explicit result and must not
     * convert execution failures into successful results.
     */
    suspend fun execute(
        invocation: CapabilityInvocation
    ): CapabilityResult
}
