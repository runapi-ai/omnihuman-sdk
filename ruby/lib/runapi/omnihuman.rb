# frozen_string_literal: true

require "runapi/core"
require_relative "omnihuman/types"
require_relative "omnihuman/contract_gen"
require_relative "omnihuman/resources/audio_to_video"
require_relative "omnihuman/resources/human_identification"
require_relative "omnihuman/resources/subject_detection"
require_relative "omnihuman/client"

module RunApi
  module Omnihuman
    AuthenticationError = RunApi::Core::AuthenticationError
    RateLimitError = RunApi::Core::RateLimitError
    InsufficientCreditsError = RunApi::Core::InsufficientCreditsError
    NotFoundError = RunApi::Core::NotFoundError
    ValidationError = RunApi::Core::ValidationError
    TaskFailedError = RunApi::Core::TaskFailedError
    TaskTimeoutError = RunApi::Core::TaskTimeoutError
  end
end
