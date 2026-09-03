package com.bbeniful.teleprompter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform