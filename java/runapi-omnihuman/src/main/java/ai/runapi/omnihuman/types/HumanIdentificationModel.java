package ai.runapi.omnihuman.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for human identification operations. */
public final class HumanIdentificationModel extends OmnihumanValue {
  /** omnihuman-1.5-human-identification model slug. */
  public static final HumanIdentificationModel OMNIHUMAN_1_5_HUMAN_IDENTIFICATION = new HumanIdentificationModel("omnihuman-1.5-human-identification");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public HumanIdentificationModel(String value) {
    super(value);
  }
}
