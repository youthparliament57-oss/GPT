package com.jarvis.core.capability

/**
 * Represents a concrete instruction to execute one registered
 * capability.
 *
 * This object is created by the reasoning/planning layer and
 * consumed by the execution runtime.
 */
data class CapabilityInvocation(
    val capabilityName: String,
    val arguments: CapabilityArguments
) {
    init {
        require(capabilityName.isNotBlank()) {
            "Capability name must not be blank."
        }
    }
}
