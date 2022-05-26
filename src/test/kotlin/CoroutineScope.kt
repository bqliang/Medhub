import kotlinx.coroutines.*

fun main() {
    val job = Job()
    val scope = CoroutineScope(job)
    scope.launch {
        println(1)
        println(2)
        println(3)

        try {
            throw NullPointerException()
        } catch (e: Exception) {
            return@launch
        }

        println(4)
        println(5)
        println(6)
        println(7)
        println(8)
    }

    Thread.sleep(10000)
}