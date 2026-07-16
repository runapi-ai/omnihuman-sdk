import { describe, expect, it } from 'vitest';
import { OmnihumanClient } from '../src';

describe('OmnihumanClient', () => {
  it('exposes OmniHuman resources', () => {
    const client = new OmnihumanClient({ apiKey: 'test-key' });

    expect(client.audioToVideo).toBeDefined();
    expect(client.humanIdentification).toBeDefined();
    expect(client.subjectDetection).toBeDefined();
  });
});
