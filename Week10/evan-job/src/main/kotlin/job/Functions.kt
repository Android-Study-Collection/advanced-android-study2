package job

import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.children

fun main() = runBlocking{
    val parentJob:Job = launch {
        println("parentJob start")
        val childJob1 = launch {
            println("child1")
        }
        val childJob2 = launch {
            println("child2")
        }
    }
//     children, attachChild
}
