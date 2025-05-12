package com.example.doancoso3.Activity.Dashboard.Chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatViewModel : ViewModel() {

    private val _aiResponse = MutableStateFlow("")
    val aiResponse: StateFlow<String> = _aiResponse

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openrouter.ai/") // Nhớ có dấu "/" cuối
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(OpenRouterApi::class.java)


    fun getSuggestions(
        ingredients: String,
        portion: String,
        cuisine: String,
        level: String,
        diet: String
    ) {
        viewModelScope.launch {
            try {
                val prompt = """
                    Tôi có những nguyên liệu sau: $ingredients.
                    Hãy gợi ý cho tôi vài món ăn phù hợp với khẩu phần $portion, ẩm thực $cuisine, độ khó $level.
                    Tôi muốn chế độ ăn: $diet. Trả lời bằng tiếng Việt, dạng danh sách.
                """.trimIndent()

                val request = ChatRequest(
                    model = "openai/gpt-3.5-turbo",
                    messages = listOf(
                        Message("system", "Bạn là một đầu bếp AI chuyên nghiệp."),
                        Message("user", prompt)
                    )
                )

                val response = api.getChatResponse(
                    request = request,
                    token = "Bearer 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",
                    title = "GobbleFood"
                )
                Log.d("Response", response.toString()) // Kiểm tra dữ liệu trả về


                _aiResponse.value = response.choices.firstOrNull()?.message?.content ?: "Không có gợi ý."
            } catch (e: Exception) {
                _aiResponse.value = "Lỗi: ${e.localizedMessage}"
            }
        }
    }
}
