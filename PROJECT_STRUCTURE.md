JARVIS MVP-1 Project Structure

1. Structure Philosophy

MVP-1 must remain small.

The project will not use a large multi-module architecture at this stage.

The initial implementation will use a single application/runtime module with clearly separated packages.

Future modules may be introduced only when there is a real architectural reason to separate them.

The goal is:

Small now
    ↓
Clear boundaries
    ↓
Easy extension
    ↓
Modules only when necessary

2. Initial Repository Structure

The initial source structure is:

GPT/
│
├── app/
│   └── src/
│       ├── main/
│       │   └── kotlin/
│       │       └── com/
│       │           └── jarvis/
│       │               └── core/
│       │
│       └── test/
│           └── kotlin/
│               └── com/
│                   └── jarvis/
│                       └── core/
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
│
├── ARCHITECTURE.md
├── PROJECT_STRUCTURE.md
└── .gitignore

This structure is intentionally minimal.

3. Core Package Structure

Inside the main Kotlin source package:

com.jarvis.core/
│
├── domain/
│
├── brain/
│
├── runtime/
│
├── capability/
│
├── memory/
│
├── provider/
│
├── speech/
│
└── application/

Each package has one primary responsibility.

4. Domain

domain/

Contains stable data models and contracts representing JARVIS concepts.

Examples:

UserRequest
JarvisResponse
Task
TaskResult
ExecutionStatus
Error

Domain objects must not depend on Android, specific LLM providers, networking libraries, or UI frameworks.

5. Brain

brain/

Responsible for intelligence orchestration.

Responsibilities include:

- Request interpretation
- Context preparation
- Reasoning orchestration
- Tool selection
- Response generation

The brain does not directly implement individual tools.

6. Runtime

runtime/

Responsible for executing tasks.

Responsibilities include:

- Execution lifecycle
- Tool invocation
- Execution state
- Failure propagation
- Timeouts where required
- Result handling

The runtime is responsible for making planned actions actually execute.

7. Capability

capability/

Contains the capability abstraction and MVP-1 tools.

Conceptually:

capability/
│
├── Capability.kt
├── CapabilityRequest.kt
├── CapabilityResult.kt
│
└── builtin/
    ├── TimeCapability.kt
    ├── CalculatorCapability.kt
    └── WebSearchCapability.kt

The exact files may change during implementation if a better design is required.

A capability must represent a real executable operation.

8. Memory

memory/

MVP-1 memory is intentionally limited.

Initially it provides session/context storage required by the agent.

It must not introduce a vector database or complex persistent-memory infrastructure without an actual MVP requirement.

Future memory implementations may include:

Working Memory
Session Memory
Episodic Memory
Semantic Memory
Preference Memory

9. Provider

provider/

Contains adapters for external intelligence and infrastructure providers.

Examples:

provider/
├── llm/
├── search/
└── ...

Provider-specific SDKs and APIs must remain behind interfaces wherever practical.

The brain should depend on abstractions rather than directly on a specific provider.

10. Speech

speech/

Responsible for speech-related functionality.

Conceptually:

Speech Input
    ↓
Speech-to-Text
    ↓
JARVIS Core
    ↓
Text Response
    ↓
Text-to-Speech
    ↓
Speech Output

Speech implementation must remain separate from reasoning logic.

11. Application

application/

Contains the composition and application-level orchestration required to start JARVIS.

Responsibilities include:

- Dependency construction
- Component wiring
- Application lifecycle
- Runtime startup

The application layer must not contain business logic that belongs in the core.

12. Dependency Rule

The following dependency direction should be maintained:

application
     ↓
runtime
     ↓
brain
     ↓
domain

Infrastructure/provider implementations may implement interfaces defined by the core.

Conceptually:

             ┌───────────────┐
             │    DOMAIN     │
             └───────▲───────┘
                     │
             ┌───────┴───────┐
             │     BRAIN     │
             └───────▲───────┘
                     │
             ┌───────┴───────┐
             │    RUNTIME    │
             └───────▲───────┘
                     │
             ┌───────┴───────┐
             │ APPLICATION    │
             └───────────────┘

External providers should connect through defined contracts.

13. What Must NOT Happen

The following patterns are prohibited:

Brain → Android UI
Brain → specific HTTP client implementation
Brain → specific LLM SDK
Brain → database implementation
Brain → individual screen
Capability → UI
UI → direct LLM calls
UI → direct tool execution

Instead:

UI
 ↓
Application
 ↓
Runtime
 ↓
Brain
 ↓
Capability / Provider Interfaces
 ↓
Concrete Implementations

14. Module Policy

MVP-1 starts with one module.

Additional modules will be introduced only when at least one of the following becomes true:

- Independent build lifecycle is required.
- A component needs a genuinely independent API boundary.
- A component will be shared by multiple applications.
- Isolation provides a meaningful security or deployment benefit.
- Build complexity becomes significant enough to justify separation.

Creating modules merely for organisational appearance is prohibited.

15. Future Expansion

The structure is expected to evolve toward something similar to:

JARVIS
│
├── core
├── runtime
├── memory
├── capability
├── providers
├── speech
├── perception
├── security
│
├── android-client
├── desktop-client
├── device-gateway
├── robotics
└── integrations

These are future architectural possibilities, not MVP-1 requirements.

16. Structural Stability Rule

Once implementation begins, package boundaries should not be changed casually.

If a structural change becomes necessary:

1. Identify the reason.
2. Determine affected components.
3. Update the architecture documentation.
4. Apply the structural change.
5. Verify compilation.
6. Verify affected functionality.
7. Continue implementation only after verification.

The repository structure is part of the system architecture and must be treated as production code.
