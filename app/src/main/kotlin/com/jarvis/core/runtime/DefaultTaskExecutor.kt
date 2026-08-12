package com.jarvis.core.runtime

import com.jarvis.core.capability.CapabilityInvocation
import com.jarvis.core.capability.CapabilityRegistry
import com.jarvis.core.capability.CapabilityResult

/**
 * Default production implementation of [TaskExecutor].
 *
 * Resolves a capability from the registry and delegates execution
 * to that capability.
 */
class DefaultTaskExecutor(
    private val capabilityRegistry: CapabilityRegistry
) : TaskExecutor {

    override suspend fun execute(
        invocation: CapabilityInvocation
    ): CapabilityResult {
        val capability = capabilityRegistry.find(invocation.capabilityName)
            ?: return CapabilityResult.Failure(
                error = "Capability '${invocation.capabilityName}' is not registered."
            )

        return try {
            capability.execute(invocation.arguments)
        } catch (exception: Exception) {
            CapabilityResult.Failure(
                error = buildExecutionError(
                    capabilityName = invocation.capabilityName,
                    exception = exception
                )
            )
        }
    }

    private fun buildExecutionError(
        capabilityName: String,
        exception: Exception
    ): String {
        val message = exception.message
            ?.takeIf { it.isNotBlank() }
            ?: exception::class.simpleName
            ?: "Unknown capability execution error"

        return "Capability '$capabilityName' execution failed: $message"
    }
}
