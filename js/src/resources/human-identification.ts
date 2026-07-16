import type { HttpClient, PollingOptions, RequestOptions, ActionSchema } from '@runapi.ai/core';
import { compactParams, validateParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import { contract } from '../contract_gen';
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
    validateParams(contract['human-identification'] as ActionSchema, body as Record<string, unknown>);
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, {
      body,
      ...options,
    });
  }

  async get(id: string, options?: RequestOptions): Promise<HumanIdentificationResponse> {
    return this.http.request<HumanIdentificationResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
