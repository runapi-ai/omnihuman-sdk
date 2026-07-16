import type { HttpClient, PollingOptions, RequestOptions, ActionSchema } from '@runapi.ai/core';
import { compactParams, validateParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import { contract } from '../contract_gen';
import type {
  CompletedSubjectDetectionResponse,
  SubjectDetectionParams,
  SubjectDetectionResponse,
  TaskCreateResponse,
} from '../types';

const ENDPOINT = '/api/v1/omnihuman/subject_detection';

/** Detect subject masks in a source image before generation. */
export class SubjectDetection {
  constructor(private readonly http: HttpClient) {}

  async run(params: SubjectDetectionParams, options?: RequestOptions & PollingOptions): Promise<CompletedSubjectDetectionResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<SubjectDetectionResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedSubjectDetectionResponse;
  }

  async create(params: SubjectDetectionParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    const body = compactParams(params);
    validateParams(contract['subject-detection'] as ActionSchema, body as Record<string, unknown>);
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, {
      body,
      ...options,
    });
  }

  async get(id: string, options?: RequestOptions): Promise<SubjectDetectionResponse> {
    return this.http.request<SubjectDetectionResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
