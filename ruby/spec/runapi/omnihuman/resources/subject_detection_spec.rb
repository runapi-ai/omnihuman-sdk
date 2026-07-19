# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Omnihuman::Resources::SubjectDetection do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/omnihuman/subject_detection" }

  it "POSTs to the correct endpoint" do
    params = {
      model: "omnihuman-1.5-subject-detection",
      source_image_url: "https://cdn.runapi.ai/public/samples/portrait.jpg"
    }
    expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-1")

    result = resource.create(**params)
    expect(result.id).to eq("task-1")
  end

  it "GETs the correct endpoint" do
    expect(http).to receive(:request).with(:get, "#{endpoint}/task-1")
      .and_return("id" => "task-1", "status" => "completed", "masks" => [{"url" => "https://x/mask.png"}])

    result = resource.get("task-1")
    expect(result.masks.first.url).to eq("https://x/mask.png")
  end
end
