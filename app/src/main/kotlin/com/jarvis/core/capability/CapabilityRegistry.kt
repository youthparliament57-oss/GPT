package com.jarvis.core.capability

/**
 * Registry of capabilities available to the JARVIS runtime.
 *
 * The registry provides controlled discovery of capabilities and
 * prevents duplicate capability identifiers from being registered.
 */
class CapabilityRegistry(
    capabilities: Collection<Capability> = emptyList()
) {

    private val capabilitiesByName: Map<String, Capability>

    init {
        val duplicateNames = capabilities
            .groupingBy { it.name }
            .eachCount()
            .filterValues { count -> count > 1 }
            .keys

        require(duplicateNames.isEmpty()) {
            "Duplicate capability names: ${duplicateNames.joinToString(", ")}"
        }

        capabilities.forEach { capability ->
            require(capability.name.isNotBlank()) {
                "Capability name must not be blank."
            }

            require(capability.description.isNotBlank()) {
                "Capability description must not be blank."
            }
        }

        capabilitiesByName = capabilities.associateBy { it.name }
    }

    /**
     * Returns a capability by its stable name.
     */
    fun find(name: String): Capability? {
        return capabilitiesByName[name]
    }

    /**
     * Returns all currently registered capabilities.
     *
     * The returned collection cannot be used to modify the registry.
     */
    fun all(): Collection<Capability> {
        return capabilitiesByName.values.toList()
    }

    /**
     * Returns true when a capability with the supplied name exists.
     */
    fun contains(name: String): Boolean {
        return capabilitiesByName.containsKey(name)
    }
}
