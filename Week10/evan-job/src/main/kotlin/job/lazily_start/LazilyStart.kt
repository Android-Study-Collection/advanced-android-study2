package job.lazily_start

import kotlinx.coroutines.*

fun main() = runBlocking {
    // launch started lazily is in New state
    val lazyJob = launch(start = CoroutineStart.LAZY) { delay(1000) }
    println(lazyJob) // New
    lazyJob.start() // We need to start it, to make it active
    println(lazyJob) // Active
    lazyJob.join()
    println(lazyJob) // Completed
}