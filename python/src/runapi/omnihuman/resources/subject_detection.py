"""OmniHuman subject-detection resource."""

from __future__ import annotations

from typing import Any, Dict, Optional

from runapi.core import Resource, RequestOptions

from ..contract_gen import CONTRACT
from ..types import (
    CompletedSubjectDetectionResponse,
    SubjectDetectionResponse,
)


class SubjectDetection(Resource):
    """Detect subject masks in a source image."""

    ENDPOINT = "/api/v1/omnihuman/subject_detection"

    RESPONSE_CLASS = SubjectDetectionResponse
    COMPLETED_RESPONSE_CLASS = CompletedSubjectDetectionResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        """Create a task and poll until it completes."""
        task = self.create(options=options, **params)
        return self._poll_until_complete(lambda: self.get(task.id, options=options))

    def create(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        """Create a subject-detection task and return immediately with an ``id``."""
        compacted = self._compact_params(params)
        self._validate_params(compacted)
        return self._request("post", self.ENDPOINT, body=compacted, options=options)

    def get(self, id: str, options: Optional[RequestOptions] = None) -> Any:
        """Fetch the current status of a subject-detection task."""
        return self._request("get", f"{self.ENDPOINT}/{id}", options=options)

    def _validate_params(self, params: Dict[str, Any]) -> None:
        self._validate_contract(CONTRACT["subject-detection"], params)
