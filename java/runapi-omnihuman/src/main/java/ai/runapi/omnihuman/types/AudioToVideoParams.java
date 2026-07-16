package ai.runapi.omnihuman.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for audio to video operations. */
public final class AudioToVideoParams {
  private final String model;
  private final String sourceImageUrl;
  private final String sourceAudioUrl;
  private final List<String> maskUrls;
  private final String prompt;
  private final String callbackUrl;
  private final String outputResolution;
  private final Boolean enableFastMode;
  private final Integer seed;

  private AudioToVideoParams(Builder builder) {
    this.model = builder.model;
    this.sourceImageUrl = OmnihumanParamUtils.requireNonBlank(builder.sourceImageUrl, "sourceImageUrl");
    this.sourceAudioUrl = OmnihumanParamUtils.requireNonBlank(builder.sourceAudioUrl, "sourceAudioUrl");
    this.maskUrls = OmnihumanParamUtils.strings(builder.maskUrls);
    this.prompt = builder.prompt;
    this.callbackUrl = builder.callbackUrl;
    this.outputResolution = builder.outputResolution;
    this.enableFastMode = builder.enableFastMode;
    this.seed = builder.seed;
  }

  /** Creates a new AudioToVideoParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "omnihuman/audio-to-video";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", OmnihumanParamUtils.wireValue(model));
    raw.put("source_image_url", OmnihumanParamUtils.wireValue(sourceImageUrl));
    raw.put("source_audio_url", OmnihumanParamUtils.wireValue(sourceAudioUrl));
    raw.put("mask_urls", OmnihumanParamUtils.wireValue(maskUrls));
    raw.put("prompt", OmnihumanParamUtils.wireValue(prompt));
    raw.put("callback_url", OmnihumanParamUtils.wireValue(callbackUrl));
    raw.put("output_resolution", OmnihumanParamUtils.wireValue(outputResolution));
    raw.put("enable_fast_mode", OmnihumanParamUtils.wireValue(enableFastMode));
    raw.put("seed", OmnihumanParamUtils.wireValue(seed));
    return OmnihumanParamUtils.compact(raw);
  }



  /** Builder for {@link AudioToVideoParams}. */
  public static final class Builder {
    private String model;
    private String sourceImageUrl;
    private String sourceAudioUrl;
    private List<String> maskUrls;
    private String prompt;
    private String callbackUrl;
    private String outputResolution;
    private Boolean enableFastMode;
    private Integer seed;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(AudioToVideoModel value) {
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

    /** Sets the source audio URL. */
    public Builder sourceAudioUrl(String value) {
      this.sourceAudioUrl = OmnihumanParamUtils.requireNonBlank(value, "sourceAudioUrl");
      return this;
    }

    /** Sets the mask URLs. */
    public Builder maskUrls(List<String> value) {
      this.maskUrls = value;
      return this;
    }

    /** Sets the text prompt. */
    public Builder prompt(String value) {
      this.prompt = OmnihumanParamUtils.requireNonBlank(value, "prompt");
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = OmnihumanParamUtils.requireNonBlank(value, "callbackUrl");
      return this;
    }

    /** Sets the output resolution. */
    public Builder outputResolution(String value) {
      this.outputResolution = OmnihumanParamUtils.requireNonBlank(value, "outputResolution");
      return this;
    }

    /** Sets the enable fast mode. */
    public Builder enableFastMode(boolean value) {
      this.enableFastMode = value;
      return this;
    }

    /** Sets the random seed. */
    public Builder seed(int value) {
      this.seed = value;
      return this;
    }

    /** Builds immutable audio to video parameters. */
    public AudioToVideoParams build() {
      return new AudioToVideoParams(this);
    }
  }
}
