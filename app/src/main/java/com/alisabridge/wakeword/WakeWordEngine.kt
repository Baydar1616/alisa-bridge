package com.alisabridge.wakeword

interface WakeWordEngine {
    fun initialize(): Result<Unit>
    fun start(): Result<Unit>
    fun stop(): Result<Unit>
    fun release(): Result<Unit>
    fun isRunning(): Boolean
}
