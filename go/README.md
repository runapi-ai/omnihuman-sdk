# OmniHuman Go SDK for RunAPI

The OmniHuman Go SDK is the language-specific package for OmniHuman on RunAPI. Use this package for audio-driven talking-head video generation, human identification, and subject-mask detection when your application needs request bodies, task status lookup, and consistent RunAPI errors in Go.

This README is the Go package guide inside the public `omnihuman-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/omnihuman; for API reference, use https://runapi.ai/docs#omnihuman; for SDK docs, use https://runapi.ai/docs#sdk-omnihuman.

## Install

```bash
go get github.com/runapi-ai/omnihuman-sdk/go@latest
```

## Quick start

```go
import (
  "context"

  "github.com/runapi-ai/omnihuman-sdk/go/omnihuman"
)

client, err := omnihuman.NewClient()
task, err := client.AudioToVideo.Create(context.Background(), omnihuman.AudioToVideoParams{
  Model:          omnihuman.ModelAudioToVideo,
  SourceImageURL: "https://cdn.runapi.ai/public/samples/portrait.jpg",
  SourceAudioURL: "https://cdn.runapi.ai/public/samples/voice.mp3",
  OutputResolution: omnihuman.Resolution720P,
})
status, err := client.AudioToVideo.Get(context.Background(), task.ID)
```

Use `create` when you want to submit a task and return quickly, `get` when you need the latest task state, and `run` when a script should create and poll until completion. In web request handlers, prefer `create` plus webhook or later `get` polling so a worker is not held open.

RunAPI-generated file URLs are temporary. Download and store generated videos, masks, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## Language notes

Use the public Go module with `github.com/runapi-ai/core-sdk/go` options when building video services, CLIs, or workers. The available resources are `AudioToVideo`, `HumanIdentification`, and `SubjectDetection`. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys or callback secrets.

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
