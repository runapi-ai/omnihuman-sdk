# frozen_string_literal: true

module RunApi
  module Omnihuman
    # OmniHuman talking-head video generation client with helper endpoints for
    # human identification and subject-mask detection.
    #
    # @example
    #   client = RunApi::Omnihuman::Client.new(api_key: "sk-...")
    #   result = client.audio_to_video.run(
    #     model: "omnihuman-1.5",
    #     source_image_url: "https://cdn.runapi.ai/public/samples/portrait.jpg",
    #     source_audio_url: "https://cdn.runapi.ai/public/samples/voice.mp3"
    #   )
    #   puts result.videos.first.url
    class Client < RunApi::Core::Client
      # @return [Resources::AudioToVideo] Talking-head video generation from source image and audio.
      # @return [Resources::HumanIdentification] Human-region helper endpoint.
      # @return [Resources::SubjectDetection] Subject-mask helper endpoint.
      attr_reader :audio_to_video, :human_identification, :subject_detection

      def initialize(api_key: nil, **options)
        super
        @audio_to_video = Resources::AudioToVideo.new(http)
        @human_identification = Resources::HumanIdentification.new(http)
        @subject_detection = Resources::SubjectDetection.new(http)
      end
    end
  end
end
