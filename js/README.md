# OmniHuman JavaScript SDK for RunAPI

The OmniHuman JavaScript SDK is the language-specific package for OmniHuman on RunAPI. Use this package for audio-driven talking-head video generation, human identification, and subject-mask detection when your application needs request bodies, task status lookup, and consistent RunAPI errors in JavaScript or TypeScript.

This README is the JavaScript package guide inside the public `omnihuman-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/omnihuman; for API reference, use https://runapi.ai/docs#omnihuman; for SDK docs, use https://runapi.ai/docs#sdk-omnihuman.

## Install

```bash
npm install @runapi.ai/omnihuman
```

## Quick start

```typescript
import { OmnihumanClient } from '@runapi.ai/omnihuman';

const client = new OmnihumanClient();
const task = await client.audioToVideo.create({
  model: 'omnihuman-1.5',
  source_image_url: 'https://cdn.runapi.ai/public/samples/portrait.jpg',
  source_audio_url: 'https://cdn.runapi.ai/public/samples/voice.mp3',
  output_resolution: '720p',
});
const status = await client.audioToVideo.get(task.id);
```

Use `create` when you want to submit a task and return quickly, `get` when you need the latest task state, and `run` when a script should create and poll until completion. In web request handlers, prefer `create` plus webhook or later `get` polling so a worker is not held open.

RunAPI-generated file URLs are temporary. Download and store generated videos, masks, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## Language notes

Use the TypeScript types in `src/types.ts` and the resource classes under `src/resources` when building video applications. The available resources are `audioToVideo`, `humanIdentification`, and `subjectDetection`. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys or callback secrets.

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
