package com.jarvis.core.domain

/**
 * Represents an internal unit of work created from a user request.
 *
 * A single UserRequest may eventually produce one or more Tasks.
 * Keeping Task separate from UserRequest allows the execution
 * system to evolve toward multi-step operations without changing
 * the external request model.
 */
data class Task(
    val id: String,
    val requestId: String,
    val description: String,
    val status: TaskStatus
) {
    init {
        require(id.isNotBlank()) {
            "Task id must not be blank."
        }

        require(requestId.isNotBlank()) {
            "Task request id must not be blank."
        }

        require(description.isNotBlank()) {
            "Task description must not be blank."
        }
    }
}

/**
 * Represents the lifecycle state of a JARVIS task.
 */
enum class TaskStatus {
    CREATED,
    RUNNING,
    COMPLETED,
    FAILED
}
