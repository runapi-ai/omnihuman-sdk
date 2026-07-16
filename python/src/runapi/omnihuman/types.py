"""OmniHuman response models."""

from __future__ import annotations

from runapi.core import BaseModel, TaskResponse, optional, required


class Video(BaseModel):
    url = optional(str)


class Mask(BaseModel):
    url = optional(str)


class AudioToVideoResponse(TaskResponse):
    """Response for an audio-to-video task."""

    id = required(str)
    status = optional(str, enum=lambda: TaskResponse.Status.ALL)
    videos = optional([lambda: Video])
    error = optional(str)


class CompletedAudioToVideoResponse(AudioToVideoResponse):
    """Returned by ``audio_to_video.run()`` once polling observes completion.

    ``videos`` is required so callers never have to null-check it on success.
    """

    videos = required([lambda: Video])


class HumanIdentificationResponse(TaskResponse):
    """Response for a human-identification helper task."""

    id = required(str)
    status = optional(str, enum=lambda: TaskResponse.Status.ALL)
    subject_status = optional(int)
    error = optional(str)


class CompletedHumanIdentificationResponse(HumanIdentificationResponse):
    """Returned by ``human_identification.run()`` once polling observes completion."""

    subject_status = required(int)


class SubjectDetectionResponse(TaskResponse):
    """Response for a subject-detection helper task."""

    id = required(str)
    status = optional(str, enum=lambda: TaskResponse.Status.ALL)
    masks = optional([lambda: Mask])
    error = optional(str)


class CompletedSubjectDetectionResponse(SubjectDetectionResponse):
    """Returned by ``subject_detection.run()`` once polling observes completion."""

    masks = required([lambda: Mask])
