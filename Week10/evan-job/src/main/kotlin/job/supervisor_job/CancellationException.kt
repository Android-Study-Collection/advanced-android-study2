package job.supervisor_job

import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.cancellation.CancellationException

fun main(): Unit = runBlocking {
//    exceptionCancelAllWithCoroutineScope()
//    exceptionCancelAllWithCoroutineScope2()
//    exceptionNoCancelAllWithSupervisorScope()
//    catchCancellationExceptionWithCoroutineScope()
//    catchCancellationExceptionWithSupervisorScope()
    catchCancellationExceptionWithTryCatch()
}

suspend fun exceptionCancelAllWithCoroutineScope() = coroutineScope {
    launch { // 1
        launch { // 2
            throw RuntimeException("exception!") // 3
        }
        delay(500L)
        println("Will be printed1")
    }
    launch { // 4
        delay(1000L)
        println("Will be printed2")
    }
    delay(1500L)
    println("Will be printed3")
}

suspend fun exceptionCancelAllWithCoroutineScope2() = coroutineScope {
    launch { // 1
        launch { // 2
            delay(500)
            println("Will not be printed")
        }
        throw RuntimeException("exception!") // 3
    }
    launch { // 4
        delay(1000)
        println("Will not be printed")
    }
    delay(1500)
    println("Will be printed")
}

suspend fun exceptionNoCancelAllWithSupervisorScope() = supervisorScope {
    launch { // 1
        launch { // 2
            throw RuntimeException("exception!") // 3
        }
        delay(500L)
        println("Will be printed1")
    }
    launch { // 4
        delay(1000L)
        println("Will be printed2")
    }
    delay(1500L)
    println("Will be printed3")
}

val handler = CoroutineExceptionHandler { ctx, exception -> println("Caught $exception") }

suspend fun catchCancellationExceptionWithCoroutineScope() = runBlocking {
    val scope = CoroutineScope(handler)
    scope.launch {
        val job = scope.launch {
            delay(5000L)
        }
        yield()
        job.cancelAndJoin()
        println("Will be printed1")
    }
    scope.launch { // 4
        delay(1000L)
        println("Will be printed2")
    }
    delay(1500L)
    println("Will be printed3")
}

suspend fun catchCancellationExceptionWithSupervisorScope() = runBlocking {
    val scope = CoroutineScope(SupervisorJob() + handler)
    scope.launch {
        val job = scope.launch {
            delay(5000L)
        }
        yield()
        job.cancelAndJoin()
        println("Will be printed1")
    }
    scope.launch { // 4
        delay(1000L)
        println("Will be printed2")
    }
    delay(1500L)
    println("Will be printed3")
}

suspend fun catchCancellationExceptionWithTryCatch() = coroutineScope {
    launch {
        val job: Job = launch {
            try {
                delay(Long.MAX_VALUE)
            }catch (e: CancellationException){
                e.printStackTrace()
            }
        }
        yield()
        job.cancelAndJoin()
        println("Will be printed1")
    }
    launch { // 4
        delay(1000L)
        println("Will be printed2")
    }
    delay(1500L)
    println("Will be printed3")
}