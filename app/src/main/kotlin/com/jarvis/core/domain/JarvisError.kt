package com.jarvis.core.domain

/**
 * Represents a known failure that can occur while processing
 * a JARVIS request.
 *
 * Errors are part of the domain so that higher layers do not
 * need to depend on provider-specific exception types.
 */
sealed interface JarvisError {

    /**
     * The user's request could not be understood or was invalid.
     */
    data class InvalidRequest(
        val reason: String
    ) : JarvisError

    /**
     * The selected model provider failed to process the request.
     */
    data class ModelFailure(
        val reason: String
    ) : JarvisError

    /**
     * An external service could not be reached successfully.
     */
    data class NetworkFailure(
        val reason: String
    ) : JarvisError

    /**
     * Authentication or authorization failed.
     */
    data class AuthenticationFailure(
        val reason: String
    ) : JarvisError

    /**
     * A capability/tool failed during execution.
     */
    data class CapabilityFailure(
        val capabilityName: String,
        val reason: String
    ) : JarvisError

    /**
     * A capability received arguments that it could not process.
     */
    data class InvalidCapabilityArguments(
        val capabilityName: String,
        val reason: String
    ) : JarvisError

    /**
     * An operation exceeded its permitted execution time.
     */
    data class Timeout(
        val operation: String
    ) : JarvisError

    /**
     * JARVIS does not currently have the requested capability.
     */
    data class UnsupportedCapability(
        val capabilityName: String
    ) : JarvisError

    /**
     * An unexpected internal failure occurred.
     *
     * The original exception is intentionally not stored in the
     * domain model so the domain remains independent of JVM or
     * provider-specific exception types.
     */
    data class InternalFailure(
        val reason: String
    ) : JarvisError
}
