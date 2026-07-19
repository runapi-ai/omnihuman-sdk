# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Omnihuman::Resources::HumanIdentification do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/omnihuman/human_identification" }

  it "POSTs to the correct endpoint" do
    params = {
      model: "omnihuman-1.5-human-identification",
      source_image_url: "https://cdn.runapi.ai/public/samples/portrait.jpg"
    }
    expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-1")

    result = resource.create(**params)
    expect(result.id).to eq("task-1")
  end

  it "GETs the correct endpoint" do
    expect(http).to receive(:request).with(:get, "#{endpoint}/task-1")
      .and_return("id" => "task-1", "status" => "completed", "subject_status" => 1)

    result = resource.get("task-1")
    expect(result.subject_status).to eq(1)
  end
end
