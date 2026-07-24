<p align="center">
  <a href="https://runapi.ai"><img src="https://runapi.ai/icon.svg" height="56" alt="RunAPI"></a>
</p>

<h3 align="center">
  <a href="https://github.com/runapi-ai/omnihuman-sdk">OmniHuman API SDK for RunAPI</a>
</h3>

<p align="center">
  OmniHuman API SDKs for JavaScript, Python, Ruby, Go, Java, and PHP on RunAPI.
</p>

<div align="center">

[![npm](https://img.shields.io/npm/v/@runapi.ai/omnihuman)](https://www.npmjs.com/package/@runapi.ai/omnihuman)
[![PyPI](https://img.shields.io/pypi/v/runapi-omnihuman)](https://pypi.org/project/runapi-omnihuman/)
[![RubyGems](https://img.shields.io/gem/v/runapi-omnihuman)](https://rubygems.org/gems/runapi-omnihuman)
[![Go Reference](https://pkg.go.dev/badge/github.com/runapi-ai/omnihuman-sdk/go.svg)](https://pkg.go.dev/github.com/runapi-ai/omnihuman-sdk/go)
[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-omnihuman)](https://central.sonatype.com/artifact/ai.runapi/runapi-omnihuman)
[![License](https://img.shields.io/github/license/runapi-ai/omnihuman-sdk)](https://github.com/runapi-ai/omnihuman-sdk/blob/main/LICENSE)

</div>
<br/>

The OmniHuman API SDK packages JavaScript, Python, Ruby, Go, Java, and PHP clients for OmniHuman on RunAPI. Use it for audio-driven talking-head video generation, human identification, and subject-mask detection when your app needs typed request builders, predictable task polling, file upload helpers, account helpers, and consistent RunAPI errors.

OmniHuman is listed in the RunAPI model catalog at https://runapi.ai/models/omnihuman. Variant pages below carry pricing, rate-limit, and commercial-usage details. The public `omnihuman-sdk` repository groups the non-PHP language packages, examples, CI, and release tags for this model. The PHP package is released from a split Composer repository.

## Install

```bash
npm install @runapi.ai/omnihuman
pip install runapi-omnihuman
gem install runapi-omnihuman
go get github.com/runapi-ai/omnihuman-sdk/go@latest
```

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

Use the Java BOM when installing multiple RunAPI Java modules:

```kotlin
dependencies {
  implementation(platform("ai.runapi:runapi-bom:0.2.6"))
  implementation("ai.runapi:runapi-omnihuman")
}
```

The PHP package is published from the split Composer repository as `runapi-ai/omnihuman`; see https://github.com/runapi-ai/omnihuman-php for PHP install and examples.

## What you can build

- Generate talking-head video from a source image and driving audio.
- Identify human regions in a source image before generation.
- Detect subject masks and pass returned mask URLs into audio-to-video requests.
- Install only the language package your app needs while keeping one model-specific repository for docs and releases.
- Use `create` for submit-only jobs, `get` for status lookup, and `run` for submit-and-poll scripts.
- Handle validation, authentication, rate limits, insufficient credits, task failures, and polling timeouts through RunAPI SDK errors.

## Java quick start

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

Java packages target Java 8 bytecode and are tested on Java 8, 11, 17, and 21. Each model artifact depends on `ai.runapi:runapi-core`, so application code normally installs only `ai.runapi:runapi-omnihuman`.

## Task lifecycle

OmniHuman media endpoints are asynchronous. `create()` submits a task and returns its id, `get(id)` fetches the latest task state, and `run(params)` creates the task and polls until it reaches a terminal state. In web request handlers, prefer `create()` plus webhook or later `get()` polling so the server does not hold a worker open.

## Repository layout

- `js/` publishes `@runapi.ai/omnihuman`.
- `python/` publishes `runapi-omnihuman`.
- `ruby/` publishes `runapi-omnihuman`.
- `go/` publishes `github.com/runapi-ai/omnihuman-sdk/go` and depends on `github.com/runapi-ai/core-sdk/go`.
- `java/` publishes `ai.runapi:runapi-omnihuman` and depends on `ai.runapi:runapi-core`.

## Public links

- Model page: https://runapi.ai/models/omnihuman
- SDK docs: https://runapi.ai/docs#sdk-omnihuman
- Product docs: https://runapi.ai/docs#omnihuman
- SDK repository: https://github.com/runapi-ai/omnihuman-sdk
- PHP package repository: https://github.com/runapi-ai/omnihuman-php
- Skill repository: https://github.com/runapi-ai/omnihuman
- Provider comparison: https://runapi.ai/providers/bytedance
- Full catalog: https://runapi.ai/models

## Pricing and variants

Use the most specific OmniHuman variant page for pricing, rate limits, and commercial usage:
- [Audio to video](https://runapi.ai/models/omnihuman/1.5)
- [Human identification](https://runapi.ai/models/omnihuman/1.5-human-identification)
- [Subject detection](https://runapi.ai/models/omnihuman/1.5-subject-detection)

Default pricing link for the OmniHuman SDK: https://runapi.ai/models/omnihuman/1.5

## File storage

RunAPI-generated file URLs are temporary. Download and store generated videos, masks, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## FAQ

### Which package should I install for OmniHuman work?

Install the model package for your language: `@runapi.ai/omnihuman` on npm, `runapi-omnihuman` on PyPI, `runapi-omnihuman` on RubyGems, `github.com/runapi-ai/omnihuman-sdk/go`, `ai.runapi:runapi-omnihuman` on Maven Central, or `runapi-ai/omnihuman` on Packagist. Install core SDK packages only when you are building shared SDK infrastructure.

### Where should public links point?

Primary OmniHuman links point to https://runapi.ai/models/omnihuman. Pricing and usage-policy links point to variant pages such as https://runapi.ai/models/omnihuman/1.5. Provider comparisons point to https://runapi.ai/providers/bytedance, and broad browsing points to https://runapi.ai/models.

## License

Licensed under the Apache License, Version 2.0.
