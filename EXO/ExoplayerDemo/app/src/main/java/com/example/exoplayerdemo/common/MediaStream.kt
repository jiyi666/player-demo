package com.example.exoplayerdemo.common

class MediaStream(val streamName: String, val streamPath: String?, val streamDescription: String) {
    override fun toString(): String {
        return "streamName: $streamName, streamPath: $streamPath, streamDescription: $streamDescription"
    }
}