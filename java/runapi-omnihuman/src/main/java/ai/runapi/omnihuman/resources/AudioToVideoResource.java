package ai.runapi.omnihuman.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.omnihuman.types.CompletedAudioToVideoResponse;
import ai.runapi.omnihuman.types.AudioToVideoParams;
import ai.runapi.omnihuman.types.AudioToVideoResponse;

/** Audio To Video operations. */
public final class AudioToVideoResource extends OmnihumanResource {
  /** API endpoint path for audio to video operations. */
  public static final String ENDPOINT = "/api/v1/omnihuman/audio_to_video";

  /** Creates a resource bound to the supplied transport and client options. */
  public AudioToVideoResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Creates a audio to video task. */
  public TaskCreateResponse create(AudioToVideoParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a audio to video task with per-request options. */
  public TaskCreateResponse create(AudioToVideoParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a audio to video task by ID. */
  public AudioToVideoResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a audio to video task by ID with per-request options. */
  public AudioToVideoResponse get(String id, RequestOptions options) {
    return getTask(id, options, AudioToVideoResponse.class);
  }

  /** Creates a audio to video task and polls until it completes. */
  public CompletedAudioToVideoResponse run(AudioToVideoParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a audio to video task with per-request options and polls until it completes. */
  public CompletedAudioToVideoResponse run(AudioToVideoParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, AudioToVideoResponse.class, CompletedAudioToVideoResponse.class);
  }
}
