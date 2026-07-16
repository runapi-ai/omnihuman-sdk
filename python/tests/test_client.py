import pytest

from runapi.core import config
from runapi.core.errors import AuthenticationError, ValidationError
from runapi.omnihuman import OmnihumanClient
from runapi.omnihuman.resources.audio_to_video import AudioToVideo
from runapi.omnihuman.resources.human_identification import HumanIdentification
from runapi.omnihuman.resources.subject_detection import SubjectDetection
from runapi.omnihuman.types import (
    AudioToVideoResponse,
    CompletedAudioToVideoResponse,
    CompletedHumanIdentificationResponse,
    CompletedSubjectDetectionResponse,
    HumanIdentificationResponse,
    SubjectDetectionResponse,
)


class FakeHttp:
    """Records (method, path, body) and replays preset responses by call order."""

    def __init__(self, *responses):
        self._responses = list(responses)
        self.calls = []

    def request(self, method, path, body=None, options=None):
        self.calls.append((method, path, body))
        if self._responses:
            return self._responses.pop(0)
        return {"id": "task_1", "status": "pending"}


@pytest.fixture(autouse=True)
def reset_config(monkeypatch):
    monkeypatch.delenv("RUNAPI_API_KEY", raising=False)
    monkeypatch.setattr(config, "api_key", None)
    yield


VALID_AUDIO_PARAMS = dict(
    model="omnihuman-1.5",
    source_image_url="https://cdn.runapi.ai/public/samples/portrait.jpg",
    source_audio_url="https://cdn.runapi.ai/public/samples/voice.mp3",
)


def test_accepts_api_key_parameter():
    assert isinstance(
        OmnihumanClient(api_key="param-key", http_client=FakeHttp()), OmnihumanClient
    )


def test_falls_back_to_global(monkeypatch):
    monkeypatch.setattr(config, "api_key", "global-key")
    assert isinstance(OmnihumanClient(http_client=FakeHttp()), OmnihumanClient)


def test_falls_back_to_env(monkeypatch):
    monkeypatch.setenv("RUNAPI_API_KEY", "env-key")
    assert isinstance(OmnihumanClient(http_client=FakeHttp()), OmnihumanClient)


def test_raises_without_api_key():
    with pytest.raises(AuthenticationError, match="API key is required"):
        OmnihumanClient()


def test_exposes_resource_accessors():
    client = OmnihumanClient(api_key="k", http_client=FakeHttp())
    assert isinstance(client.audio_to_video, AudioToVideo)
    assert isinstance(client.human_identification, HumanIdentification)
    assert isinstance(client.subject_detection, SubjectDetection)


def test_audio_to_video_create_posts_compacted_body():
    fake = FakeHttp({"id": "t1", "status": "pending"})
    client = OmnihumanClient(api_key="k", http_client=fake)
    result = client.audio_to_video.create(
        **VALID_AUDIO_PARAMS,
        prompt="A presenter speaks naturally to camera",
        output_resolution="720p",
        enable_fast_mode=True,
        seed=None,
    )
    assert fake.calls == [
        (
            "post",
            "/api/v1/omnihuman/audio_to_video",
            {
                "model": "omnihuman-1.5",
                "source_image_url": "https://cdn.runapi.ai/public/samples/portrait.jpg",
                "source_audio_url": "https://cdn.runapi.ai/public/samples/voice.mp3",
                "prompt": "A presenter speaks naturally to camera",
                "output_resolution": "720p",
                "enable_fast_mode": True,
            },
        ),
    ]
    assert isinstance(result, AudioToVideoResponse)
    assert result.id == "t1"


def test_audio_to_video_get_fetches_by_id():
    fake = FakeHttp({"id": "t1", "status": "processing"})
    client = OmnihumanClient(api_key="k", http_client=fake)
    client.audio_to_video.get("t1")
    assert fake.calls == [("get", "/api/v1/omnihuman/audio_to_video/t1", None)]


def test_audio_to_video_run_polls_and_narrows_completed_type():
    fake = FakeHttp(
        {"id": "t1", "status": "pending"},
        {"id": "t1", "status": "completed", "videos": [{"url": "https://x/y.mp4"}]},
    )
    client = OmnihumanClient(api_key="k", http_client=fake)
    result = client.audio_to_video.run(**VALID_AUDIO_PARAMS)

    assert isinstance(result, CompletedAudioToVideoResponse)
    assert result.videos[0].url == "https://x/y.mp4"
    assert [call[0] for call in fake.calls] == ["post", "get"]


def test_human_identification_create_and_get():
    fake = FakeHttp(
        {"id": "human_1", "status": "pending"},
        {"id": "human_1", "status": "completed", "subject_status": 1},
    )
    client = OmnihumanClient(api_key="k", http_client=fake)
    task = client.human_identification.create(
        model="omnihuman-1.5-human-identification",
        source_image_url="https://cdn.runapi.ai/public/samples/portrait.jpg",
    )
    result = client.human_identification.get(task.id)

    assert fake.calls[0] == (
        "post",
        "/api/v1/omnihuman/human_identification",
        {
            "model": "omnihuman-1.5-human-identification",
            "source_image_url": "https://cdn.runapi.ai/public/samples/portrait.jpg",
        },
    )
    assert fake.calls[1] == ("get", "/api/v1/omnihuman/human_identification/human_1", None)
    assert isinstance(result, HumanIdentificationResponse)
    assert result.subject_status == 1


def test_human_identification_run_polls_and_narrows_completed_type():
    fake = FakeHttp(
        {"id": "human_1", "status": "pending"},
        {"id": "human_1", "status": "completed", "subject_status": 1},
    )
    client = OmnihumanClient(api_key="k", http_client=fake)
    result = client.human_identification.run(
        model="omnihuman-1.5-human-identification",
        source_image_url="https://cdn.runapi.ai/public/samples/portrait.jpg",
    )

    assert isinstance(result, CompletedHumanIdentificationResponse)
    assert result.subject_status == 1


def test_subject_detection_create_and_get():
    fake = FakeHttp(
        {"id": "mask_1", "status": "pending"},
        {"id": "mask_1", "status": "completed", "masks": [{"url": "https://x/mask.png"}]},
    )
    client = OmnihumanClient(api_key="k", http_client=fake)
    task = client.subject_detection.create(
        model="omnihuman-1.5-subject-detection",
        source_image_url="https://cdn.runapi.ai/public/samples/portrait.jpg",
    )
    result = client.subject_detection.get(task.id)

    assert fake.calls[0] == (
        "post",
        "/api/v1/omnihuman/subject_detection",
        {
            "model": "omnihuman-1.5-subject-detection",
            "source_image_url": "https://cdn.runapi.ai/public/samples/portrait.jpg",
        },
    )
    assert fake.calls[1] == ("get", "/api/v1/omnihuman/subject_detection/mask_1", None)
    assert isinstance(result, SubjectDetectionResponse)
    assert result.masks[0].url == "https://x/mask.png"


def test_subject_detection_run_polls_and_narrows_completed_type():
    fake = FakeHttp(
        {"id": "mask_1", "status": "pending"},
        {"id": "mask_1", "status": "completed", "masks": [{"url": "https://x/mask.png"}]},
    )
    client = OmnihumanClient(api_key="k", http_client=fake)
    result = client.subject_detection.run(
        model="omnihuman-1.5-subject-detection",
        source_image_url="https://cdn.runapi.ai/public/samples/portrait.jpg",
    )

    assert isinstance(result, CompletedSubjectDetectionResponse)
    assert result.masks[0].url == "https://x/mask.png"


def test_audio_to_video_requires_model():
    client = OmnihumanClient(api_key="k", http_client=FakeHttp())
    params = {k: v for k, v in VALID_AUDIO_PARAMS.items() if k != "model"}
    with pytest.raises(ValidationError, match="model must be one of: omnihuman-1.5"):
        client.audio_to_video.create(**params)


def test_audio_to_video_rejects_invalid_resolution():
    client = OmnihumanClient(api_key="k", http_client=FakeHttp())
    params = {**VALID_AUDIO_PARAMS, "output_resolution": "480p"}
    with pytest.raises(ValidationError, match="output_resolution must be one of: 720p, 1080p"):
        client.audio_to_video.create(**params)
