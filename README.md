# AI Knowledge Builder

AI-powered fashion knowledge generation and outfit analysis service built with **Java, Spring Boot, and OpenAI**.

## Overview

AI Knowledge Builder is a backend service that transforms fashion images and prompts into structured fashion knowledge.

The service uses OpenAI's multimodal capabilities to analyze fashion images, extracts structured information from AI responses, and normalizes generated attributes into predefined application-level values.

It also provides AI-assisted outfit category planning and outfit selection functionality.

The project is designed as a supporting AI component for fashion recommendation workflows.

---

## Features

- 🧠 AI-powered fashion image analysis
- 👁️ Multimodal image and text processing
- 🤖 OpenAI Responses API integration
- 📝 External prompt management
- 🔄 Structured AI response parsing
- 🏷️ Fashion attribute normalization
- 👗 Outfit category planning
- ✨ AI-assisted outfit selection
- 🌐 REST API
- 🧩 Strongly typed domain models and enums
- 🔤 Alias-based attribute mapping

---

## Architecture

```text
                         Image URL / Prompt
                                │
                                ▼
                     ┌────────────────────┐
                     │ KnowledgeController │
                     └─────────┬──────────┘
                               │
                               ▼
                  ┌──────────────────────────┐
                  │ KnowledgeGenerationService│
                  └────────────┬─────────────┘
                               │
                    ┌──────────┴──────────┐
                    │                     │
                    ▼                     ▼
             ┌──────────────┐      ┌───────────────┐
             │ PromptBuilder│      │ OpenAiService │
             └──────────────┘      └───────┬───────┘
                                           │
                                           ▼
                                  OpenAI Responses API
                                           │
                                           ▼
                                   OpenAI Response
                                           │
                                           ▼
                                  ┌────────────────┐
                                  │ KnowledgeParser│
                                  └───────┬────────┘
                                          │
                 ┌────────────────────────┼──────────────────────┐
                 │                        │                      │
                 ▼                        ▼                      ▼
          CategoryMapper            ColorMapper           MaterialMapper
                 │
                 ├── PatternMapper
                 ├── FitMapper
                 ├── SeasonMapper
                 ├── StyleMapper
                 └── FormalityMapper
                                          │
                                          ▼
                                Structured Knowledge
```

---

## Processing Flow

The main knowledge generation pipeline is:

```text
Image URL
   ↓
REST Controller
   ↓
Knowledge Generation Service
   ↓
Prompt Builder
   ↓
OpenAI Responses API
   ↓
AI JSON Response
   ↓
Knowledge Parser
   ↓
Attribute Mappers
   ↓
Structured Knowledge
```

### 1. Image Analysis

The client provides an image URL through the REST API.

The service combines the image with a predefined vision-analysis prompt and sends the request to OpenAI.

### 2. Response Parsing

The generated `output_text` is extracted from the OpenAI response and converted into a strongly typed Java response model.

### 3. Attribute Normalization

AI-generated values are mapped to predefined application enums.

Supported attributes include:

- Outfit Category
- Color
- Material
- Pattern
- Fit
- Season
- Style
- Formality

Alias definitions are stored as JSON resources under:

```text
src/main/resources/aliases/
```

The mapping layer prevents different AI-generated expressions from producing inconsistent application values.

---

## Outfit Analysis

The service also supports AI-assisted outfit workflows.

### Outfit Category Planning

The application can generate structured outfit category plans from a prompt.

### Best Outfit Selection

The application can process an outfit-selection prompt and return a structured `OutfitSelection` model.

These capabilities allow the service to act as an AI reasoning layer within a larger fashion recommendation system.

---

## REST API

| Method | Endpoint | Purpose |
|---|---|---|
| `POST` | `/knowledge/generate` | Analyze an image and generate structured fashion knowledge |
| `POST` | `/knowledge/select-categories` | Generate outfit category plans |
| `POST` | `/knowledge/select` | Select an outfit based on a prompt |
| `GET` | `/test/color` | Test color attribute normalization |

### Generate Knowledge

```http
POST /knowledge/generate
Content-Type: application/json
```

```json
{
  "imageUrl": "https://example.com/product-image.jpg"
}
```

### Select Outfit Categories

```http
POST /knowledge/select-categories
Content-Type: application/json
```

```json
{
  "prompt": "Create suitable outfit categories for this product."
}
```

### Select Best Outfit

```http
POST /knowledge/select
Content-Type: application/json
```

```json
{
  "prompt": "Select the best outfit combination from the provided products."
}
```

---

## Project Structure

```text
src/
├── main/
│   ├── java/com/sneaksapp/knowledgebuilder/
│   │
│   ├── config/
│   │   └── RestClientConfig.java
│   │
│   ├── controller/
│   │   ├── KnowledgeController.java
│   │   └── TestController.java
│   │
│   ├── generator/
│   │   └── KnowledgeGenerationService.java
│   │
│   ├── model/
│   │   ├── Knowledge.java
│   │   ├── OutfitAnalysis.java
│   │   ├── OutfitCategoryPlan.java
│   │   ├── OutfitItem.java
│   │   ├── OutfitSelection.java
│   │   └── enums/
│   │
│   ├── openai/
│   │   ├── OpenAiService.java
│   │   ├── PromptBuilder.java
│   │   ├── request/
│   │   └── response/
│   │
│   ├── parser/
│   │   └── KnowledgeParser.java
│   │
│   └── service/
│       ├── CategoryMapper.java
│       ├── ColorMapper.java
│       ├── FitMapper.java
│       ├── FormalityMapper.java
│       ├── JsonAliasMapper.java
│       ├── MaterialMapper.java
│       ├── PatternMapper.java
│       ├── SeasonMapper.java
│       └── StyleMapper.java
│
└── resources/
    ├── aliases/
    └── prompts/
```

---

## Technologies

| Technology | Role |
|---|---|
| **Java 25** | Backend development |
| **Spring Boot 4.1.0** | Application framework |
| **Spring Web** | REST API |
| **Spring Validation** | Validation support |
| **OpenAI API** | AI and multimodal analysis |
| **Jackson** | JSON processing |
| **Lombok** | Boilerplate reduction |
| **Maven** | Build and dependency management |

---

## Prompt Management

Prompts are maintained separately from the Java source code:

```text
src/main/resources/prompts/
```

Current prompt resources include:

```text
vision-analysis.txt
harmony-analysis.txt
category-aliases.txt
color-aliases.txt
```

`PromptBuilder` loads these resources from the application classpath.

This keeps AI prompt definitions separate from business logic and makes them easier to maintain.

---

## Error Handling and Parsing

The application validates the OpenAI response before processing its content.

The response pipeline is:

```text
OpenAI Response
      ↓
Output Extraction
      ↓
JSON Deserialization
      ↓
AI Response Model
      ↓
Attribute Mapping
      ↓
Domain Model
```

Invalid or incomplete responses result in application-level exceptions rather than silently producing incomplete knowledge.

---

## Relationship to the SneaksUp Recommendation System

AI Knowledge Builder is designed as a supporting component for a broader fashion recommendation architecture.

Its responsibility is to transform visual and textual fashion information into structured knowledge that can be consumed by downstream recommendation components.

```text
              AI Knowledge Builder
                       │
                       ▼
            Structured Fashion Data
                       │
                       ▼
          Recommendation Components
                       │
                       ▼
               Outfit Recommendations
```

This separation keeps AI knowledge generation independent from recommendation logic.

---

## Running Locally

Start the application with Maven:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

### Build

```powershell
.\mvnw.cmd clean package
```

### Test

```powershell
.\mvnw.cmd clean test
```

---

## Current Scope

The current repository focuses on the AI knowledge-generation and outfit-analysis layer.

It does not contain:

- A persistent database layer
- A user-facing frontend
- The complete recommendation engine

The service is intended to integrate with other components in a larger AI-powered fashion recommendation ecosystem.

---

## Future Improvements

- OpenAPI / Swagger documentation
- Expanded automated test coverage
- Centralized exception handling
- Stronger response validation
- Structured production logging
- Persistent knowledge storage
- Integration with recommendation services
- Docker-based deployment

---

## Author

**Ayşenur DOĞAN**

Computer Engineering Student

[GitHub](https://github.com/aysenurdogan711)
