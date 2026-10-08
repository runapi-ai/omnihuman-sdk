import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type {
  CompletedHumanIdentificationResponse,
  HumanIdentificationParams,
  HumanIdentificationResponse,
  TaskCreateResponse,
} from '../types';

const ENDPOINT = '/api/v1/omnihuman/human_identification';

/** Identify human regions in a source image before generation. */
export class HumanIdentification {
  constructor(private readonly http: HttpClient) {}

  async run(params: HumanIdentificationParams, options?: RequestOptions & PollingOptions): Promise<CompletedHumanIdentificationResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<HumanIdentificationResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedHumanIdentificationResponse;
  }

  async create(params: HumanIdentificationParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    const body = compactParams(params);
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, {
      body,
      ...options,
    });
  }

  async get(id: string, options?: RequestOptions): Promise<HumanIdentificationResponse> {
    return this.http.request<HumanIdentificationResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
