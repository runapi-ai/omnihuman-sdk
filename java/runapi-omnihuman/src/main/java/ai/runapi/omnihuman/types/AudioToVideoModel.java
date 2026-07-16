package ai.runapi.omnihuman.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for audio to video operations. */
public final class AudioToVideoModel extends OmnihumanValue {
  /** omnihuman-1.5 model slug. */
  public static final AudioToVideoModel OMNIHUMAN_1_5 = new AudioToVideoModel("omnihuman-1.5");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public AudioToVideoModel(String value) {
    super(value);
  }
}
