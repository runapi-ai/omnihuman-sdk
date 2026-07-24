# OmniHuman Java SDK for RunAPI

[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-omnihuman)](https://central.sonatype.com/artifact/ai.runapi/runapi-omnihuman)

The OmniHuman Java SDK is the language-specific package for OmniHuman on RunAPI. Use it when your Java application needs typed builders, strict request validation, task status lookup, local polling helpers, file uploads, account helpers, and consistent RunAPI errors for OmniHuman workflows.

This README is the Java package guide inside the public `omnihuman-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/omnihuman; for API reference, use https://runapi.ai/docs#omnihuman; for SDK docs, use https://runapi.ai/docs#sdk-omnihuman.

## Requirements

The Java SDK targets Java 8 bytecode and is tested on Java 8, 11, 17, and 21.

## Install

Gradle:

```kotlin
dependencies {
  implementation("ai.runapi:runapi-omnihuman:0.1.2")
}
```

Maven:

```xml
<dependency>
  <groupId>ai.runapi</groupId>
  <artifactId>runapi-omnihuman</artifactId>
  <version>0.1.2</version>
</dependency>
```

Use the BOM when multiple RunAPI Java modules are installed:

```kotlin
dependencies {
  implementation(platform("ai.runapi:runapi-bom:0.2.6"))
  implementation("ai.runapi:runapi-omnihuman")
}
```

## Quick Start

```java
import ai.runapi.omnihuman.OmnihumanClient;
import ai.runapi.omnihuman.types.AudioToVideoParams;
import ai.runapi.omnihuman.types.AudioToVideoModel;
import ai.runapi.omnihuman.types.CompletedAudioToVideoResponse;

OmnihumanClient client = OmnihumanClient.builder()
    .apiKey(System.getenv("RUNAPI_API_KEY"))
    .build();

CompletedAudioToVideoResponse result = client.audioToVideo().run(
    AudioToVideoParams.builder()
        .model(AudioToVideoModel.OMNIHUMAN_1_5)
        .sourceImageUrl("https://cdn.runapi.ai/public/samples/portrait.jpg")
        .sourceAudioUrl("https://cdn.runapi.ai/public/samples/voice.mp3")
        .outputResolution("720p")
        .build()
);
```

The client builder reads `RUNAPI_API_KEY` when `.apiKey(...)` is omitted. Set `RUNAPI_BASE_URL` or `.baseUrl(...)` only when using a non-default RunAPI endpoint.

## Task Lifecycle

OmniHuman media endpoints are asynchronous. `create(params)` submits a task and returns its id, `get(id)` fetches the latest task state, and `run(params)` creates the task and polls until it reaches a terminal state. Helper resources expose the same lifecycle.

RunAPI-generated file URLs are temporary. Download and store generated videos, masks, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## Links

- Model page: https://runapi.ai/models/omnihuman
- SDK docs: https://runapi.ai/docs#sdk-omnihuman
- Product docs: https://runapi.ai/docs#omnihuman
- Pricing and rate limits: https://runapi.ai/models/omnihuman/1.5
- Human identification: https://runapi.ai/models/omnihuman/1.5-human-identification
- Subject detection: https://runapi.ai/models/omnihuman/1.5-subject-detection
- Provider comparison: https://runapi.ai/providers/bytedance
- Full catalog: https://runapi.ai/models
- Repository: https://github.com/runapi-ai/omnihuman-sdk

## License

Licensed under the Apache License, Version 2.0.
