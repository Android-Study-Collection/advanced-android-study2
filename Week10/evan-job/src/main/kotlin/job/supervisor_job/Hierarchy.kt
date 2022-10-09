package job.supervisor_job

import kotlinx.coroutines.*

fun main(): Unit = runBlocking{
    println("==== START of runblocking")
    launch {
        println("==== START of launch1")
        delay(100L)
        launch {
            println("==== START of launch1-1")
            delay(100L)
            println("==== END of launch1-1")
        }
        launch {
            println("==== START of launch1-2")
            delay(100L)
            println("==== END of launch1-2")
        }
        println("==== END of launch1")
    }
    launch {
        println("==== START of launch2")
        delay(100L)
        println("==== END of launch2")
    }
    println("==== END of runblocking")
}