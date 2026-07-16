# frozen_string_literal: true

module RunApi
  module Omnihuman
    module Types
      # A generated video asset.
      class Video < RunApi::Core::BaseModel
        optional :url, String
      end

      # A generated mask asset.
      class Mask < RunApi::Core::BaseModel
        optional :url, String
      end

      # Result of an audio-to-video generation task.
      # While processing, +videos+ is nil; once completed, it contains generated videos.
      class AudioToVideoResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
        optional :videos, [-> { Video }]
        optional :error, String
      end

      # Narrowed response type guaranteed to contain completed videos.
      class CompletedAudioToVideoResponse < AudioToVideoResponse
        required :videos, [-> { Video }]
      end

      # Result of a human-identification helper task.
      class HumanIdentificationResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
        optional :subject_status, Integer
        optional :error, String
      end

      # Narrowed response type guaranteed to contain a subject status.
      class CompletedHumanIdentificationResponse < HumanIdentificationResponse
        required :subject_status, Integer
      end

      # Result of a subject-detection helper task.
      class SubjectDetectionResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
        optional :masks, [-> { Mask }]
        optional :error, String
      end

      # Narrowed response type guaranteed to contain completed masks.
      class CompletedSubjectDetectionResponse < SubjectDetectionResponse
        required :masks, [-> { Mask }]
      end
    end
  end
end
