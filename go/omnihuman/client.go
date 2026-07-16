// Package omnihuman provides the OmniHuman API client.
//
//	client, err := omnihuman.NewClient(option.WithAPIKey("sk-your-api-key"))
//	result, err := client.AudioToVideo.Run(ctx, omnihuman.AudioToVideoParams{
//	    Model:          omnihuman.ModelAudioToVideo,
//	    SourceImageURL: "https://cdn.runapi.ai/public/samples/portrait.jpg",
//	    SourceAudioURL: "https://cdn.runapi.ai/public/samples/voice.mp3",
//	})
package omnihuman

import (
	"context"

	"github.com/runapi-ai/core-sdk/go/base"
	"github.com/runapi-ai/core-sdk/go/core"
	"github.com/runapi-ai/core-sdk/go/option"
)

const (
	audioToVideoPath        = "/api/v1/omnihuman/audio_to_video"
	humanIdentificationPath = "/api/v1/omnihuman/human_identification"
	subjectDetectionPath    = "/api/v1/omnihuman/subject_detection"
)

// Client provides access to OmniHuman generation and helper endpoints.
type Client struct {
	base.Base
	AudioToVideo        *AudioToVideo
	HumanIdentification *HumanIdentification
	SubjectDetection    *SubjectDetection
}

// NewClient creates an OmniHuman client with the given options.
func NewClient(opts ...option.ClientOption) (*Client, error) {
	resolved, err := option.ResolveClientOptions(opts...)
	if err != nil {
		return nil, err
	}
	httpClient, err := core.NewHTTPClient(resolved)
	if err != nil {
		return nil, err
	}
	return NewClientWithHTTP(httpClient), nil
}

// NewClientWithHTTP creates an OmniHuman client using a pre-configured HTTP client.
func NewClientWithHTTP(httpClient core.HTTPClient) *Client {
	return &Client{
		Base:                base.New(httpClient),
		AudioToVideo:        &AudioToVideo{http: httpClient},
		HumanIdentification: &HumanIdentification{http: httpClient},
		SubjectDetection:    &SubjectDetection{http: httpClient},
	}
}

// AudioToVideo generates talking-head video from a source image and driving audio.
type AudioToVideo struct{ http core.HTTPClient }

// Create submits an audio-to-video task and returns immediately with a task ID.
func (r *AudioToVideo) Create(ctx context.Context, params AudioToVideoParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	body := core.CompactParams(params)
	if err := core.ValidateParams(contractSchema["audio-to-video"], body); err != nil {
		return nil, err
	}
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, audioToVideoPath, body, requestOptions)
}

// Get retrieves the current status and result of an audio-to-video task by its ID.
func (r *AudioToVideo) Get(ctx context.Context, id string, opts ...option.RequestOption) (*AudioToVideoResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[AudioToVideoResponse](ctx, r.http, core.ResourcePath(audioToVideoPath, id), requestOptions)
}

// Run submits an audio-to-video task and polls until it completes or fails.
func (r *AudioToVideo) Run(ctx context.Context, params AudioToVideoParams, opts ...option.RequestOption) (*AudioToVideoResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*AudioToVideoResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// HumanIdentification identifies human regions in a source image.
type HumanIdentification struct{ http core.HTTPClient }

func (r *HumanIdentification) Create(ctx context.Context, params HumanIdentificationParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	body := core.CompactParams(params)
	if err := core.ValidateParams(contractSchema["human-identification"], body); err != nil {
		return nil, err
	}
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, humanIdentificationPath, body, requestOptions)
}

func (r *HumanIdentification) Get(ctx context.Context, id string, opts ...option.RequestOption) (*HumanIdentificationResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[HumanIdentificationResponse](ctx, r.http, core.ResourcePath(humanIdentificationPath, id), requestOptions)
}

func (r *HumanIdentification) Run(ctx context.Context, params HumanIdentificationParams, opts ...option.RequestOption) (*HumanIdentificationResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*HumanIdentificationResponse, error) {
		return r.Get(ctx, id, opts...)
	}, pollingOptions)
}

// SubjectDetection detects subject masks in a source image.
type SubjectDetection struct{ http core.HTTPClient }

func (r *SubjectDetection) Create(ctx context.Context, params SubjectDetectionParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	body := core.CompactParams(params)
	if err := core.ValidateParams(contractSchema["subject-detection"], body); err != nil {
		return nil, err
	}
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, subjectDetectionPath, body, requestOptions)
}

func (r *SubjectDetection) Get(ctx context.Context, id string, opts ...option.RequestOption) (*SubjectDetectionResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[SubjectDetectionResponse](ctx, r.http, core.ResourcePath(subjectDetectionPath, id), requestOptions)
}

func (r *SubjectDetection) Run(ctx context.Context, params SubjectDetectionParams, opts ...option.RequestOption) (*SubjectDetectionResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*SubjectDetectionResponse, error) {
		return r.Get(ctx, id, opts...)
	}, pollingOptions)
}
