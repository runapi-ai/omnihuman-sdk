import { beforeEach, describe, expect, it, vi } from 'vitest';
import type { HttpClient } from '@runapi.ai/core';
import { AudioToVideo } from '../../src/resources/audio-to-video';
import { HumanIdentification } from '../../src/resources/human-identification';
import { SubjectDetection } from '../../src/resources/subject-detection';

describe('OmniHuman resources', () => {
  const mockHttp: HttpClient = {
    request: vi.fn(),
  };

  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('creates audio-to-video tasks with flat params', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-1' });
    const audioToVideo = new AudioToVideo(mockHttp);

    await audioToVideo.create({
      model: 'omnihuman-1.5',
      source_image_url: 'https://cdn.runapi.ai/public/samples/portrait.jpg',
      source_audio_url: 'https://cdn.runapi.ai/public/samples/voice.mp3',
      mask_urls: ['https://cdn.runapi.ai/public/samples/mask.png'],
      prompt: 'A presenter speaks naturally to camera',
      output_resolution: '1080p',
      enable_fast_mode: true,
      seed: 162242,
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/omnihuman/audio_to_video', {
      body: {
        model: 'omnihuman-1.5',
        source_image_url: 'https://cdn.runapi.ai/public/samples/portrait.jpg',
        source_audio_url: 'https://cdn.runapi.ai/public/samples/voice.mp3',
        mask_urls: ['https://cdn.runapi.ai/public/samples/mask.png'],
        prompt: 'A presenter speaks naturally to camera',
        output_resolution: '1080p',
        enable_fast_mode: true,
        seed: 162242,
      },
    });
  });

  it('gets audio-to-video tasks by id', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      id: 'task-2',
      status: 'completed',
      videos: [{ url: 'https://tempfile.runapi.ai/omnihuman/out.mp4' }],
    });
    const audioToVideo = new AudioToVideo(mockHttp);

    const result = await audioToVideo.get('task-2');

    expect(mockHttp.request).toHaveBeenCalledWith('GET', '/api/v1/omnihuman/audio_to_video/task-2', {});
    expect(result.videos?.[0]?.url).toBe('https://tempfile.runapi.ai/omnihuman/out.mp4');
  });

  it('creates human-identification tasks with source_image_url', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-3' });
    const humanIdentification = new HumanIdentification(mockHttp);

    await humanIdentification.create({
      model: 'omnihuman-1.5-human-identification',
      source_image_url: 'https://cdn.runapi.ai/public/samples/portrait.jpg',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/omnihuman/human_identification', {
      body: {
        model: 'omnihuman-1.5-human-identification',
        source_image_url: 'https://cdn.runapi.ai/public/samples/portrait.jpg',
      },
    });
  });

  it('gets human-identification tasks by id', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      id: 'task-4',
      status: 'completed',
      subject_status: 1,
    });
    const humanIdentification = new HumanIdentification(mockHttp);

    const result = await humanIdentification.get('task-4');

    expect(mockHttp.request).toHaveBeenCalledWith('GET', '/api/v1/omnihuman/human_identification/task-4', {});
    expect(result.subject_status).toBe(1);
  });

  it('creates subject-detection tasks with source_image_url', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-7' });
    const subjectDetection = new SubjectDetection(mockHttp);

    await subjectDetection.create({
      model: 'omnihuman-1.5-subject-detection',
      source_image_url: 'https://cdn.runapi.ai/public/samples/portrait.jpg',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/omnihuman/subject_detection', {
      body: {
        model: 'omnihuman-1.5-subject-detection',
        source_image_url: 'https://cdn.runapi.ai/public/samples/portrait.jpg',
      },
    });
  });

  it('gets subject-detection tasks by id', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      id: 'task-8',
      status: 'completed',
      masks: [{ url: 'https://tempfile.runapi.ai/omnihuman/mask.png' }],
    });
    const subjectDetection = new SubjectDetection(mockHttp);

    const result = await subjectDetection.get('task-8');

    expect(mockHttp.request).toHaveBeenCalledWith('GET', '/api/v1/omnihuman/subject_detection/task-8', {});
    expect(result.masks?.[0]?.url).toBe('https://tempfile.runapi.ai/omnihuman/mask.png');
  });
});
