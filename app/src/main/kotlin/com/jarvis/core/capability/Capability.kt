package com.jarvis.core.capability

/**
 * Defines an executable capability available to JARVIS.
 *
 * A capability represents one concrete operation that JARVIS
 * can intentionally invoke.
 *
 * Implementations must perform real work and must never return
 * simulated success.
 */
interface Capability {

    /**
     * Stable identifier used by the runtime and model/tool layer.
     *
     * This value must remain unique within the registered
     * capability set.
     */
    val name: String

    /**
     * Human-readable description of what this capability does.
     *
     * The description may later be exposed to the reasoning
     * layer when selecting available capabilities.
     */
    val description: String

    /**
     * Executes the capability with the supplied arguments.
     *
     * Implementations are responsible for validating their
     * own arguments and returning an explicit result.
     */
    suspend fun execute(
        arguments: CapabilityArguments
    ): CapabilityResult
}

/**
 * Represents arguments supplied to a capability.
 *
 * A simple key-value representation is sufficient for MVP-1.
 * The abstraction can later evolve into a strongly typed schema
 * or JSON-based contract without coupling the JARVIS brain
 * to a specific serialization library.
 */
data class CapabilityArguments(
    val values: Map<String, String>
) {

    /**
     * Returns the value associated with [key], or null when the
     * argument was not supplied.
     */
    fun get(key: String): String? = values[key]
}

/**
 * Represents the outcome of capability execution.
 */
sealed interface CapabilityResult {

    /**
     * The capability completed successfully.
     */
    data class Success(
        val output: String
    ) : CapabilityResult {
        init {
            require(output.isNotBlank()) {
                "Capability success output must not be blank."
            }
        }
    }

    /**
     * The capability could not complete successfully.
     */
    data class Failure(
        val error: String
    ) : CapabilityResult {
        init {
            require(error.isNotBlank()) {
                "Capability failure error must not be blank."
            }
        }
    }
}
