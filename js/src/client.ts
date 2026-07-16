import { BaseClient, type ClientOptions } from '@runapi.ai/core';
import { AudioToVideo } from './resources/audio-to-video';
import { HumanIdentification } from './resources/human-identification';
import { SubjectDetection } from './resources/subject-detection';

/**
 * OmniHuman audio-to-video generation and helper endpoint API client.
 *
 * @example
 * ```typescript
 * const client = new OmnihumanClient({ apiKey: 'your-api-key' });
 *
 * const result = await client.audioToVideo.run({
 *   model: 'omnihuman-1.5',
 *   source_image_url: 'https://cdn.runapi.ai/public/samples/portrait.jpg',
 *   source_audio_url: 'https://cdn.runapi.ai/public/samples/voice.mp3',
 * });
 * ```
 */
export class OmnihumanClient extends BaseClient {
  /** Generate a talking-head video from a source image and driving audio. */
  public readonly audioToVideo: AudioToVideo;
  /** Identify human regions in a source image before generation. */
  public readonly humanIdentification: HumanIdentification;
  /** Detect subject masks in a source image before generation. */
  public readonly subjectDetection: SubjectDetection;

  constructor(options: ClientOptions = {}) {
    super(options);
    this.audioToVideo = new AudioToVideo(this.http);
    this.humanIdentification = new HumanIdentification(this.http);
    this.subjectDetection = new SubjectDetection(this.http);
  }
}
