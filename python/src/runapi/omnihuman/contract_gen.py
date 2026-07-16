CONTRACT = {
    "audio-to-video": {
        "models": ["omnihuman-1.5"],
        "fields_by_model": {
            "omnihuman-1.5": {
                "output_resolution": {
                    "enum": ["720p", "1080p"]
                },
                "prompt": {
                    "max": 1000,
                    "length": True
                },
                "seed": {
                    "type": "integer"
                },
                "source_audio_url": {
                    "required": True
                },
                "source_image_url": {
                    "required": True
                }
            }
        }
    },
    "human-identification": {
        "models": ["omnihuman-1.5-human-identification"],
        "fields_by_model": {
            "omnihuman-1.5-human-identification": {
                "source_image_url": {
                    "required": True
                }
            }
        }
    },
    "subject-detection": {
        "models": ["omnihuman-1.5-subject-detection"],
        "fields_by_model": {
            "omnihuman-1.5-subject-detection": {
                "source_image_url": {
                    "required": True
                }
            }
        }
    }
}
