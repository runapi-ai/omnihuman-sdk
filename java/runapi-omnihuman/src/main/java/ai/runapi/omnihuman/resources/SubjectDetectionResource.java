package ai.runapi.omnihuman.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.omnihuman.types.CompletedSubjectDetectionResponse;
import ai.runapi.omnihuman.types.SubjectDetectionParams;
import ai.runapi.omnihuman.types.SubjectDetectionResponse;

/** Subject Detection operations. */
public final class SubjectDetectionResource extends OmnihumanResource {
  /** API endpoint path for subject detection operations. */
  public static final String ENDPOINT = "/api/v1/omnihuman/subject_detection";

  /** Creates a resource bound to the supplied transport and client options. */
  public SubjectDetectionResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Creates a subject detection task. */
  public TaskCreateResponse create(SubjectDetectionParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a subject detection task with per-request options. */
  public TaskCreateResponse create(SubjectDetectionParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a subject detection task by ID. */
  public SubjectDetectionResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a subject detection task by ID with per-request options. */
  public SubjectDetectionResponse get(String id, RequestOptions options) {
    return getTask(id, options, SubjectDetectionResponse.class);
  }

  /** Creates a subject detection task and polls until it completes. */
  public CompletedSubjectDetectionResponse run(SubjectDetectionParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a subject detection task with per-request options and polls until it completes. */
  public CompletedSubjectDetectionResponse run(SubjectDetectionParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, SubjectDetectionResponse.class, CompletedSubjectDetectionResponse.class);
  }
}
