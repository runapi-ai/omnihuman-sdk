# frozen_string_literal: true

module RunApi
  module Omnihuman
    module Resources
      # Generates talking-head videos from a source image and driving audio.
      class AudioToVideo
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/omnihuman/audio_to_video"
        RESPONSE_CLASS = Types::AudioToVideoResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedAudioToVideoResponse

        def initialize(http)
          @http = http
        end

        def run(options: nil, **params)
          task = create(options: options, **params)
          poll_until_complete { get(task.id, options: options) }
        end

        def create(options: nil, **params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params, options: options)
        end

        def get(id, options: nil)
          request(:get, "#{ENDPOINT}/#{id}", options: options)
        end

        private

        def validate_params!(params)
          validate_contract!(CONTRACT["audio-to-video"], params)
        end
      end
    end
  end
end
