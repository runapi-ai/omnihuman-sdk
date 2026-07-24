"""OmniHuman client."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import ProviderClient

from .resources.audio_to_video import AudioToVideo
from .resources.human_identification import HumanIdentification
from .resources.subject_detection import SubjectDetection


class OmnihumanClient(ProviderClient):
    """OmniHuman audio-to-video and helper endpoint client.

    Example::

        client = OmnihumanClient(api_key="sk-...")
        result = client.audio_to_video.run(
            model="omnihuman-1.5",
            source_image_url="https://cdn.runapi.ai/public/samples/portrait.jpg",
            source_audio_url="https://cdn.runapi.ai/public/samples/voice.mp3",
        )
    """

    def __init__(self, api_key: Optional[str] = None, **options: Any) -> None:
        super().__init__(api_key, **options)
        http = self._http
        self.audio_to_video = AudioToVideo(http)
        self.human_identification = HumanIdentification(http)
        self.subject_detection = SubjectDetection(http)
