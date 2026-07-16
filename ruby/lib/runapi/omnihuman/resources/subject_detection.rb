# frozen_string_literal: true

module RunApi
  module Omnihuman
    module Resources
      # Detects subject masks in a source image before video generation.
      class SubjectDetection
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/omnihuman/subject_detection"
        RESPONSE_CLASS = Types::SubjectDetectionResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedSubjectDetectionResponse

        def initialize(http)
          @http = http
        end

        def run(options: nil, **params)
          task = create(options: options, **params)
          poll_until_complete { get(task.id, options: options) }
        end

        def create(options: nil, **params)
          params = compact_params(params)
          validate_contract!(CONTRACT["subject-detection"], params)
          request(:post, ENDPOINT, body: params, options: options)
        end

        def get(id, options: nil)
          request(:get, "#{ENDPOINT}/#{id}", options: options)
        end
      end
    end
  end
end
