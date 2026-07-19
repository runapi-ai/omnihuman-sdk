# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Omnihuman::Resources::AudioToVideo do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/omnihuman/audio_to_video" }

  it "POSTs to the correct endpoint" do
    params = {
      model: "omnihuman-1.5",
      source_image_url: "https://cdn.runapi.ai/public/samples/portrait.jpg",
      source_audio_url: "https://cdn.runapi.ai/public/samples/voice.mp3",
      prompt: "A presenter speaks naturally to camera",
      output_resolution: "720p"
    }
    expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-1")

    result = resource.create(**params)
    expect(result.id).to eq("task-1")
  end

  it "GETs the correct endpoint" do
    expect(http).to receive(:request).with(:get, "#{endpoint}/task-1")
      .and_return("id" => "task-1", "status" => "completed")

    result = resource.get("task-1")
    expect(result.status).to eq("completed")
  end

  it "raises ValidationError for invalid output_resolution" do
    expect {
      resource.create(
        model: "omnihuman-1.5",
        source_image_url: "https://cdn.runapi.ai/public/samples/portrait.jpg",
        source_audio_url: "https://cdn.runapi.ai/public/samples/voice.mp3",
        output_resolution: "480p"
      )
    }.to raise_error(RunApi::Core::ValidationError, /output_resolution must be one of: 720p, 1080p/)
  end
end
