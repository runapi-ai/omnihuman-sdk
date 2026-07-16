package ai.runapi.omnihuman;

import ai.runapi.core.BaseClient;
import ai.runapi.core.ClientOptions;
import ai.runapi.core.http.HttpTransport;
import java.net.URI;
import ai.runapi.omnihuman.resources.AudioToVideoResource;
import ai.runapi.omnihuman.resources.HumanIdentificationResource;
import ai.runapi.omnihuman.resources.SubjectDetectionResource;

/** OmniHuman model-family Java SDK client. */
public final class OmnihumanClient extends BaseClient {
  private final AudioToVideoResource audioToVideo;
  private final HumanIdentificationResource humanIdentification;
  private final SubjectDetectionResource subjectDetection;

  private OmnihumanClient(ClientOptions options) {
    super(options);
    this.audioToVideo = new AudioToVideoResource(transport(), options());
    this.humanIdentification = new HumanIdentificationResource(transport(), options());
    this.subjectDetection = new SubjectDetectionResource(transport(), options());
  }

  /** Creates a new OmnihumanClient builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Audio To Video operations. */
  public AudioToVideoResource audioToVideo() {
    return audioToVideo;
  }

  /** Human Identification operations. */
  public HumanIdentificationResource humanIdentification() {
    return humanIdentification;
  }

  /** Subject Detection operations. */
  public SubjectDetectionResource subjectDetection() {
    return subjectDetection;
  }

  /** Builder for {@link OmnihumanClient}. */
  public static final class Builder extends BaseClient.Builder<Builder> {
    private Builder() {}

    /** Sets the API key. If omitted, the SDK reads {@code RUNAPI_API_KEY}. */
    @Override
    public Builder apiKey(String value) {
      return super.apiKey(value);
    }

    /** Sets the RunAPI base URL. If omitted, the SDK reads {@code RUNAPI_BASE_URL}. */
    @Override
    public Builder baseUrl(String value) {
      return super.baseUrl(value);
    }

    /** Sets the RunAPI base URL from a URI. */
    @Override
    public Builder baseUrl(URI value) {
      return super.baseUrl(value);
    }

    /** Sets a custom HTTP transport. User-provided transports are not closed by SDK clients. */
    @Override
    public Builder transport(HttpTransport value) {
      return super.transport(value);
    }

    /** Builds an immutable OmnihumanClient. */
    @Override
    public OmnihumanClient build() {
      return new OmnihumanClient(options.build());
    }
  }
}
