package ai.runapi.omnihuman.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for subject detection operations. */
public final class SubjectDetectionModel extends OmnihumanValue {
  /** omnihuman-1.5-subject-detection model slug. */
  public static final SubjectDetectionModel OMNIHUMAN_1_5_SUBJECT_DETECTION = new SubjectDetectionModel("omnihuman-1.5-subject-detection");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public SubjectDetectionModel(String value) {
    super(value);
  }
}
