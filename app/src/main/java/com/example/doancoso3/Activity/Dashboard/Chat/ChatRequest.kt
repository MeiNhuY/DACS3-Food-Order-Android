package com.example.doancoso3.Activity.Dashboard.Chat

data class ChatRequest(
    val model: String, // Bắt buộc
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)

data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)
