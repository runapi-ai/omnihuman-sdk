<p align="center">
  <a href="https://github.com/runapi-ai/omnihuman">
    <h3 align="center">OmniHuman API Skill for RunAPI</h3>
  </a>
</p>

<p align="center">
  Install this agent skill, inspect OmniHuman fields, then create audio-driven video and helper tasks through the RunAPI CLI.
</p>

<p align="center">
  <a href="https://runapi.ai/models/omnihuman"><strong>Model Reference</strong></a> · <a href="https://github.com/runapi-ai/cli"><strong>CLI</strong></a> · <a href="https://github.com/runapi-ai/omnihuman-sdk"><strong>SDK</strong></a>
</p>

<div align="center">

[![skills.sh](https://www.skills.sh/b/runapi-ai/omnihuman)](https://www.skills.sh/runapi-ai/omnihuman/omnihuman)
[![ClawHub](https://img.shields.io/badge/ClawHub-runapi--omnihuman-111827)](https://clawhub.ai/runapi-ai/runapi-omnihuman)
[![License](https://img.shields.io/github/license/runapi-ai/omnihuman)](https://github.com/runapi-ai/omnihuman/blob/main/LICENSE)

</div>
<br/>

Create OmniHuman audio-to-video tasks and helper tasks for human identification and subject-mask detection through RunAPI. This skill helps Claude Code, Codex, Gemini CLI, Cursor, and 50+ agents use OmniHuman through RunAPI.

The canonical agent file is `skills/omnihuman/SKILL.md`.

## Variants

- Audio to video: generate talking-head video from a source image and driving audio with `runapi omnihuman audio-to-video`.
- Human identification: identify human regions in a source image with `runapi omnihuman human-identification`.
- Subject detection: detect subject masks in a source image with `runapi omnihuman subject-detection`.

## Install

```bash
npx skills add runapi-ai/omnihuman -g
```

Or paste this prompt to your AI agent:

```text
Install the omnihuman skill for me:

1. Clone https://github.com/runapi-ai/omnihuman
2. Copy the skills/omnihuman/ directory into your
   user-level skills directory.
3. Verify that SKILL.md is present.
4. Confirm the install path when done.
```

## Quick example

```shell
runapi omnihuman human-identification --async --input-file identify-human.json
runapi omnihuman subject-detection --async --input-file detect-subject.json
runapi omnihuman audio-to-video --async --input-file audio-to-video.json
runapi wait <task-id> --service omnihuman --action audio-to-video
```

## Routing

- Model page: https://runapi.ai/models/omnihuman
- Product docs: https://runapi.ai/docs#omnihuman
- SDK docs: https://runapi.ai/docs#sdk-omnihuman
- SDK repository: https://github.com/runapi-ai/omnihuman-sdk
- Audio-to-video pricing and rate limits: https://runapi.ai/models/omnihuman/1.5
- Human-identification pricing and rate limits: https://runapi.ai/models/omnihuman/1.5-human-identification
- Subject-detection pricing and rate limits: https://runapi.ai/models/omnihuman/1.5-subject-detection
- Provider comparison: https://runapi.ai/providers/bytedance
- Browse all RunAPI models and skills: https://runapi.ai/models

## Agent rules

- Integration work uses the target language SDK; one-off generation, manual smoke tests, debugging, or user-requested CLI runs use the RunAPI CLI skill: https://github.com/runapi-ai/cli-skill
- RunAPI-generated file URLs are temporary. Download and store generated videos, masks, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.
- Keep API keys in `RUNAPI_API_KEY` or RunAPI CLI config; never commit secrets.
- Prefer `create`, `get`, and `run` JSON passthrough patterns instead of inventing flags for every model parameter.
- For pricing, rate-limit, and commercial-usage answers, link to the variant page rather than the repository README.

## License

Licensed under the Apache License, Version 2.0.
