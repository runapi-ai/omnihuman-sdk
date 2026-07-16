package ai.runapi.omnihuman.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for subject detection operations. */
public final class SubjectDetectionParams {
  private final String model;
  private final String sourceImageUrl;
  private final String callbackUrl;

  private SubjectDetectionParams(Builder builder) {
    this.model = builder.model;
    this.sourceImageUrl = OmnihumanParamUtils.requireNonBlank(builder.sourceImageUrl, "sourceImageUrl");
    this.callbackUrl = builder.callbackUrl;
  }

  /** Creates a new SubjectDetectionParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "omnihuman/subject-detection";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", OmnihumanParamUtils.wireValue(model));
    raw.put("source_image_url", OmnihumanParamUtils.wireValue(sourceImageUrl));
    raw.put("callback_url", OmnihumanParamUtils.wireValue(callbackUrl));
    return OmnihumanParamUtils.compact(raw);
  }



  /** Builder for {@link SubjectDetectionParams}. */
  public static final class Builder {
    private String model;
    private String sourceImageUrl;
    private String callbackUrl;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(SubjectDetectionModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = OmnihumanParamUtils.requireNonBlankTrim(value, "model");
      return this;
    }


    /** Sets the source image URL. */
    public Builder sourceImageUrl(String value) {
      this.sourceImageUrl = OmnihumanParamUtils.requireNonBlank(value, "sourceImageUrl");
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = OmnihumanParamUtils.requireNonBlank(value, "callbackUrl");
      return this;
    }

    /** Builds immutable subject detection parameters. */
    public SubjectDetectionParams build() {
      return new SubjectDetectionParams(this);
    }
  }
}
