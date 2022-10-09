import kotlinx.coroutines.*

fun main(){
    println("Hello")
    repeat(3) {
        GlobalScope.launch {
            println("start")
            delay(1000L)
            println("end")
        }
    }
}
suspend fun fetchUserData() = "user_name"

suspend fun cacheUserData(user: String) = user

fun updateTextView(user: String) = user