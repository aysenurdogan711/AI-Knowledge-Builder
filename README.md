\# AI Knowledge Builder



AI-powered knowledge generation and outfit analysis service built with Spring Boot and OpenAI.



\## Overview



AI Knowledge Builder is a Spring Boot service designed to transform fashion product and outfit information into structured knowledge using OpenAI.



The application can analyze an image provided through a URL, send the image together with a predefined vision-analysis prompt to OpenAI, parse the generated JSON response, and map AI-generated attributes into predefined application enums.



The service also provides AI-powered outfit category planning and outfit selection capabilities.



This project is designed as a knowledge-generation component for AI-powered fashion recommendation workflows.



\---



\## Key Features



\- AI-powered fashion image analysis

\- OpenAI Responses API integration

\- Image and text input support

\- Prompt management through external resource files

\- Structured JSON response parsing

\- AI attribute normalization through alias mappings

\- Product and outfit knowledge generation

\- Outfit category planning

\- AI-based outfit selection

\- REST API endpoints

\- Structured domain models and enums



\---



\## Architecture



```text

&#x20;                   Image URL / Prompt

&#x20;                          │

&#x20;                          ▼

&#x20;                 ┌──────────────────┐

&#x20;                 │ KnowledgeController │

&#x20;                 └────────┬─────────┘

&#x20;                          │

&#x20;                          ▼

&#x20;             ┌──────────────────────────┐

&#x20;             │ KnowledgeGenerationService │

&#x20;             └────────────┬─────────────┘

&#x20;                          │

&#x20;            ┌─────────────┴─────────────┐

&#x20;            │                           │

&#x20;            ▼                           ▼

&#x20;     ┌───────────────┐          ┌────────────────┐

&#x20;     │ PromptBuilder │          │  OpenAiService │

&#x20;     └───────────────┘          └───────┬────────┘

&#x20;                                        │

&#x20;                                        ▼

&#x20;                               OpenAI Responses API

&#x20;                                        │

&#x20;                                        ▼

&#x20;                                 OpenAI Response

&#x20;                                        │

&#x20;                                        ▼

&#x20;                               ┌────────────────┐

&#x20;                               │ KnowledgeParser │

&#x20;                               └───────┬────────┘

&#x20;                                       │

&#x20;                      ┌────────────────┼────────────────┐

&#x20;                      │                │                │

&#x20;                      ▼                ▼                ▼

&#x20;                CategoryMapper   ColorMapper     MaterialMapper

&#x20;                      │

&#x20;                      ├── PatternMapper

&#x20;                      ├── FitMapper

&#x20;                      ├── SeasonMapper

&#x20;                      ├── StyleMapper

&#x20;                      └── FormalityMapper

&#x20;                                       │

&#x20;                                       ▼

&#x20;                             Structured Knowledge

```



\---



\## AI Knowledge Generation Flow



The main knowledge generation process follows these steps:



1\. The client sends an image URL to the REST API.

2\. `KnowledgeController` receives the request.

3\. `KnowledgeGenerationService` loads the vision analysis prompt.

4\. `PromptBuilder` reads the prompt from the application resources.

5\. `OpenAiService` creates a multimodal request containing text and image input.

6\. The request is sent to the OpenAI Responses API.

7\. The returned response is extracted from the `output\_text` content.

8\. `KnowledgeParser` converts the JSON response into the application's response model.

9\. Attribute mapper services normalize AI-generated values.

10\. A structured `Knowledge` object is returned.



\---



\## AI Attribute Normalization



AI-generated values may contain different expressions for the same concept.



To provide consistent application-level values, the project uses dedicated mapper services and alias dictionaries.



Supported attribute groups include:



\- Outfit Category

\- Color Profile

\- Material

\- Pattern

\- Fit

\- Season

\- Style

\- Formality



Alias definitions are stored under:



```text

src/main/resources/aliases/

```



The corresponding mapping services are located under:



```text

src/main/java/com/sneaksapp/knowledgebuilder/service/

```



This approach creates a normalization layer between free-form AI output and the application's structured domain model.



\---



\## Outfit Analysis



In addition to image-based knowledge generation, the service supports AI-assisted outfit planning.



\### Outfit Category Selection



The `/knowledge/select-categories` endpoint sends a prompt to OpenAI and converts the returned JSON into a list of `OutfitCategoryPlan` objects.



\### Best Outfit Selection



The `/knowledge/select` endpoint sends a prompt to OpenAI and converts the returned JSON into an `OutfitSelection` object.



These capabilities allow the service to support higher-level outfit recommendation workflows.



\---



\## REST API



\### Generate Knowledge



```http

POST /knowledge/generate

Content-Type: application/json

```



Request:



```json

{

&#x20; "imageUrl": "https://example.com/product-image.jpg"

}

```



The endpoint analyzes the supplied image and returns structured knowledge about the detected fashion attributes.



\---



\### Select Outfit Categories



```http

POST /knowledge/select-categories

Content-Type: application/json

```



Request:



```json

{

&#x20; "prompt": "Create suitable outfit categories for this product."

}

```



Returns a structured list of `OutfitCategoryPlan` objects.



\---



\### Select Best Outfit



```http

POST /knowledge/select

Content-Type: application/json

```



Request:



```json

{

&#x20; "prompt": "Select the best outfit combination from the provided products."

}

```



Returns an `OutfitSelection` object.



\---



\### Color Mapper Test Endpoint



```http

GET /test/color?value=navy

```



This endpoint is provided for testing the color normalization layer.



\---



\## Project Structure



```text

AI-Knowledge-Builder/

│

├── .mvn/

│   └── wrapper/

│

├── src/

│   ├── main/

│   │   ├── java/

│   │   │   └── com/

│   │   │       └── sneaksapp/

│   │   │           └── knowledgebuilder/

│   │   │               ├── config/

│   │   │               │   └── RestClientConfig.java

│   │   │               │

│   │   │               ├── controller/

│   │   │               │   ├── KnowledgeController.java

│   │   │               │   └── TestController.java

│   │   │               │

│   │   │               ├── generator/

│   │   │               │   └── KnowledgeGenerationService.java

│   │   │               │

│   │   │               ├── model/

│   │   │               │   ├── Knowledge.java

│   │   │               │   ├── OutfitAnalysis.java

│   │   │               │   ├── OutfitCategoryPlan.java

│   │   │               │   ├── OutfitItem.java

│   │   │               │   ├── OutfitSelection.java

│   │   │               │   └── enums/

│   │   │               │

│   │   │               ├── openai/

│   │   │               │   ├── OpenAiService.java

│   │   │               │   ├── PromptBuilder.java

│   │   │               │   ├── request/

│   │   │               │   └── response/

│   │   │               │

│   │   │               ├── parser/

│   │   │               │   └── KnowledgeParser.java

│   │   │               │

│   │   │               └── service/

│   │   │                   ├── CategoryMapper.java

│   │   │                   ├── ColorMapper.java

│   │   │                   ├── FitMapper.java

│   │   │                   ├── FormalityMapper.java

│   │   │                   ├── JsonAliasMapper.java

│   │   │                   ├── MaterialMapper.java

│   │   │                   ├── PatternMapper.java

│   │   │                   ├── SeasonMapper.java

│   │   │                   └── StyleMapper.java

│   │   │

│   │   └── resources/

│   │       ├── aliases/

│   │       │   ├── category-aliases.json

│   │       │   ├── color-aliases.json

│   │       │   ├── fit-aliases.json

│   │       │   ├── formality-aliases.json

│   │       │   ├── material-aliases.json

│   │       │   ├── pattern-aliases.json

│   │       │   ├── season-aliases.json

│   │       │   └── style-aliases.json

│   │       │

│   │       ├── prompts/

│   │       │   ├── category-aliases.txt

│   │       │   ├── color-aliases.txt

│   │       │   ├── harmony-analysis.txt

│   │       │   └── vision-analysis.txt

│   │       │

│   │       └── application.properties

│   │

│   └── test/

│       └── java/

│

├── .gitignore

├── pom.xml

├── mvnw

├── mvnw.cmd

└── README.md

```



\---



\## Technologies



| Technology | Purpose |

|---|---|

| Java 25 | Application development |

| Spring Boot 4.1.0 | Application framework |

| Spring Web | REST API development |

| Spring Validation | Request validation support |

| OpenAI API | AI-powered image and text analysis |

| Jackson | JSON serialization and deserialization |

| Lombok | Boilerplate reduction |

| Maven | Dependency and build management |



\---



\## Running Locally



Start the Spring Boot application:



```powershell

.\\mvnw.cmd spring-boot:run

```



The application runs on:



```text

http://localhost:8080

```



\---



\## Build and Test



Run the test suite:



```powershell

.\\mvnw.cmd clean test

```



Build the application:



```powershell

.\\mvnw.cmd clean package

```



Run the generated application:



```powershell

java -jar target/knowledge-builder-0.0.1-SNAPSHOT.jar

```



\---



\## Prompt Management



AI prompts are kept outside the Java source code under:



```text

src/main/resources/prompts/

```



`PromptBuilder` loads prompt files from the application's classpath.



This separates prompt content from application logic and makes prompts easier to maintain and update.



\---



\## Error Handling and Response Parsing



The application validates the OpenAI response structure before extracting generated content.



The service searches the response output for `output\_text` content and throws an application exception when a valid response cannot be extracted.



AI-generated JSON responses are then deserialized into strongly typed Java models.



This provides a structured processing pipeline:



```text

OpenAI Response

&#x20;     ↓

Output Extraction

&#x20;     ↓

JSON Parsing

&#x20;     ↓

Domain Model

&#x20;     ↓

Attribute Mapping

```



\---



\## Relationship to the SneaksUp Recommendation System



AI Knowledge Builder is designed as a supporting AI component for a broader fashion recommendation workflow.



Its primary responsibility is to transform visual and textual fashion information into structured knowledge that can be consumed by downstream recommendation components.



Conceptually:



```text

&#x20;                AI Knowledge Builder

&#x20;                        │

&#x20;                        ▼

&#x20;             Structured Fashion Knowledge

&#x20;                        │

&#x20;                        ▼

&#x20;             Recommendation Components

&#x20;                        │

&#x20;                        ▼

&#x20;                Outfit Recommendations

```



This separation allows knowledge generation and recommendation logic to remain independent services/components.



\---



\## What This Project Demonstrates



This project demonstrates practical experience with:



\- Spring Boot REST API development

\- Java backend architecture

\- OpenAI API integration

\- Multimodal AI requests

\- Prompt engineering and prompt management

\- JSON response processing

\- DTO-based API integration

\- Domain modeling with Java enums

\- AI output normalization

\- Alias-based attribute mapping

\- Separation of concerns

\- Maven-based project management



\---



\## Current Scope



The current implementation focuses on the AI knowledge-generation and outfit-analysis layer.



It does not include a persistent database layer or user-facing frontend within this repository.



The service is intended to operate as a backend component that can be integrated with other recommendation and e-commerce components.



\---



\## Future Improvements



Potential future improvements include:



\- Stronger request validation

\- Centralized exception handling

\- OpenAPI / Swagger documentation

\- More comprehensive automated tests

\- Improved response validation

\- Structured logging

\- Additional fashion attributes

\- Database persistence for generated knowledge

\- Integration with recommendation services

\- Docker containerization

\- Production-oriented configuration management



\---



\## Author



\*\*Ayşenur DOĞAN\*\*



Computer Engineering Student



GitHub: \[aysenurdogan711](https://github.com/aysenurdogan711)

