import type { AsyncTaskStatus } from '@runapi.ai/core';

export type OmniHumanAudioToVideoModel = 'omnihuman-1.5';
export type OmniHumanHumanIdentificationModel = 'omnihuman-1.5-human-identification';
export type OmniHumanSubjectDetectionModel = 'omnihuman-1.5-subject-detection';
/** Output resolution. Defaults to 1080p. */
export type OmniHumanOutputResolution = '720p' | '1080p';

/** Parameters for audio-to-video talking-head generation. */
export interface AudioToVideoParams {
  model: OmniHumanAudioToVideoModel;
  /** Source image URL containing the target person. */
  source_image_url: string;
  /** Source audio URL that drives the generated motion and timing. */
  source_audio_url: string;
  /** Optional mask URLs from subject detection; at most 5 URLs. */
  mask_urls?: string[];
  /** Optional generation guidance prompt. Up to 1000 characters. */
  prompt?: string;
  /** Output resolution. Defaults to 1080p. */
  output_resolution?: OmniHumanOutputResolution;
  /** Enable faster generation mode when available. */
  enable_fast_mode?: boolean;
  /** Reproducibility seed. */
  seed?: number;
  /** URL for completion callback notifications. */
  callback_url?: string;
}

/** Parameters for human identification helper requests. */
export interface HumanIdentificationParams {
  model: OmniHumanHumanIdentificationModel;
  /** Source image URL to analyze for human regions. */
  source_image_url: string;
  /** URL for completion callback notifications. */
  callback_url?: string;
}

/** Parameters for subject detection helper requests. */
export interface SubjectDetectionParams {
  model: OmniHumanSubjectDetectionModel;
  /** Source image URL to analyze for subject masks. */
  source_image_url: string;
  /** URL for completion callback notifications. */
  callback_url?: string;
}

export interface TaskCreateResponse {
  id: string;
  status?: AsyncTaskStatus;
}

/** A generated video file with a download URL. */
export interface Video {
  url: string;
}

/** A returned mask file with a download URL. */
export interface Mask {
  url: string;
}

export interface AudioToVideoResponse {
  id: string;
  status: AsyncTaskStatus;
  videos?: Video[];
  error?: string;
  [key: string]: unknown;
}

export type CompletedAudioToVideoResponse = AudioToVideoResponse & {
  status: 'completed';
  videos: Video[];
};

export interface HumanIdentificationResponse {
  id: string;
  status: AsyncTaskStatus;
  subject_status?: number;
  error?: string;
  [key: string]: unknown;
}

export type CompletedHumanIdentificationResponse = HumanIdentificationResponse & {
  status: 'completed';
  subject_status: number;
};

export interface SubjectDetectionResponse {
  id: string;
  status: AsyncTaskStatus;
  masks?: Mask[];
  error?: string;
  [key: string]: unknown;
}

export type CompletedSubjectDetectionResponse = SubjectDetectionResponse & {
  status: 'completed';
  masks: Mask[];
};
