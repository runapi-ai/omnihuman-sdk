import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type {
  AudioToVideoParams,
  AudioToVideoResponse,
  CompletedAudioToVideoResponse,
  TaskCreateResponse,
} from '../types';

const ENDPOINT = '/api/v1/omnihuman/audio_to_video';

/** Generate a talking-head video from a source image and driving audio. */
export class AudioToVideo {
  constructor(private readonly http: HttpClient) {}

  async run(params: AudioToVideoParams, options?: RequestOptions & PollingOptions): Promise<CompletedAudioToVideoResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<AudioToVideoResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedAudioToVideoResponse;
  }

  async create(params: AudioToVideoParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    const body = compactParams(params);
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, {
      body,
      ...options,
    });
  }

  async get(id: string, options?: RequestOptions): Promise<AudioToVideoResponse> {
    return this.http.request<AudioToVideoResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
