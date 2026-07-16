package omnihuman

// AudioToVideoModel identifies the OmniHuman audio-to-video model.
type AudioToVideoModel string

// HumanIdentificationModel identifies the OmniHuman human-identification helper model.
type HumanIdentificationModel string

// SubjectDetectionModel identifies the OmniHuman subject-detection helper model.
type SubjectDetectionModel string

// OutputResolution controls the output video dimensions.
type OutputResolution string

// TaskStatus represents the processing state of an asynchronous task.
type TaskStatus string

const (
	// ModelAudioToVideo generates talking-head video from an image and audio source.
	ModelAudioToVideo AudioToVideoModel = "omnihuman-1.5"
	// ModelHumanIdentification identifies human regions in a source image.
	ModelHumanIdentification HumanIdentificationModel = "omnihuman-1.5-human-identification"
	// ModelSubjectDetection detects subject masks in a source image.
	ModelSubjectDetection SubjectDetectionModel = "omnihuman-1.5-subject-detection"

	// Resolution720P produces 720p output.
	Resolution720P OutputResolution = "720p"
	// Resolution1080P produces 1080p output.
	Resolution1080P OutputResolution = "1080p"
)

// AsyncTaskResponse contains the common fields shared by all asynchronous task responses.
type AsyncTaskResponse struct {
	ID     string     `json:"id"`
	Status TaskStatus `json:"status"`
	Error  string     `json:"error,omitempty"`
}

func (r AsyncTaskResponse) GetID() string     { return r.ID }
func (r AsyncTaskResponse) GetStatus() string { return string(r.Status) }
func (r AsyncTaskResponse) GetError() string  { return r.Error }

// Video holds the URL of a generated video asset.
type Video struct {
	URL string `json:"url"`
}

// Mask holds the URL of a generated mask asset.
type Mask struct {
	URL string `json:"url"`
}

// AudioToVideoResponse is the result of an audio-to-video task.
type AudioToVideoResponse struct {
	AsyncTaskResponse
	Videos []Video `json:"videos,omitempty"`
}

// HumanIdentificationResponse is the result of a human-identification helper task.
type HumanIdentificationResponse struct {
	AsyncTaskResponse
	SubjectStatus *int `json:"subject_status,omitempty"`
}

// SubjectDetectionResponse is the result of a subject-detection helper task.
type SubjectDetectionResponse struct {
	AsyncTaskResponse
	Masks []Mask `json:"masks,omitempty"`
}

// AudioToVideoParams configures an audio-to-video generation request.
type AudioToVideoParams struct {
	Model            AudioToVideoModel `json:"model" help:"required; model slug"`
	SourceImageURL   string            `json:"source_image_url" help:"required; source image URL"`
	SourceAudioURL   string            `json:"source_audio_url" help:"required; source audio URL"`
	MaskURLs         []string          `json:"mask_urls,omitempty" help:"optional; subject mask URLs, max 5"`
	Prompt           string            `json:"prompt,omitempty" help:"optional; max 1000 chars"`
	CallbackURL      string            `json:"callback_url,omitempty" help:"optional; webhook URL"`
	OutputResolution OutputResolution  `json:"output_resolution,omitempty" help:"optional; 720p or 1080p"`
	EnableFastMode   *bool             `json:"enable_fast_mode,omitempty" help:"optional; faster generation mode"`
	Seed             *int              `json:"seed,omitempty" help:"optional; integer seed"`
}

// HumanIdentificationParams configures a human-identification helper request.
type HumanIdentificationParams struct {
	Model          HumanIdentificationModel `json:"model" help:"required; model slug"`
	SourceImageURL string                   `json:"source_image_url" help:"required; source image URL"`
	CallbackURL    string                   `json:"callback_url,omitempty" help:"optional; webhook URL"`
}

// SubjectDetectionParams configures a subject-detection helper request.
type SubjectDetectionParams struct {
	Model          SubjectDetectionModel `json:"model" help:"required; model slug"`
	SourceImageURL string                `json:"source_image_url" help:"required; source image URL"`
	CallbackURL    string                `json:"callback_url,omitempty" help:"optional; webhook URL"`
}
