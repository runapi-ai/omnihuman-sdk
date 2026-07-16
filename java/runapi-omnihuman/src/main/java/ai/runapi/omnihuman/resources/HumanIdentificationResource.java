package ai.runapi.omnihuman.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.omnihuman.types.CompletedHumanIdentificationResponse;
import ai.runapi.omnihuman.types.HumanIdentificationParams;
import ai.runapi.omnihuman.types.HumanIdentificationResponse;

/** Human Identification operations. */
public final class HumanIdentificationResource extends OmnihumanResource {
  /** API endpoint path for human identification operations. */
  public static final String ENDPOINT = "/api/v1/omnihuman/human_identification";

  /** Creates a resource bound to the supplied transport and client options. */
  public HumanIdentificationResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Creates a human identification task. */
  public TaskCreateResponse create(HumanIdentificationParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a human identification task with per-request options. */
  public TaskCreateResponse create(HumanIdentificationParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a human identification task by ID. */
  public HumanIdentificationResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a human identification task by ID with per-request options. */
  public HumanIdentificationResponse get(String id, RequestOptions options) {
    return getTask(id, options, HumanIdentificationResponse.class);
  }

  /** Creates a human identification task and polls until it completes. */
  public CompletedHumanIdentificationResponse run(HumanIdentificationParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a human identification task with per-request options and polls until it completes. */
  public CompletedHumanIdentificationResponse run(HumanIdentificationParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, HumanIdentificationResponse.class, CompletedHumanIdentificationResponse.class);
  }
}
