package job.supervisor_job

import kotlinx.coroutines.*

fun main(){
//    exceptionWithNormalJob()
    exceptionWithSupervisorJob()
}

/**
 * child에서 exception 발생 시, 다른 child에서 예외를 무시한다.
 */
fun exceptionWithSupervisorJob() = runBlocking{
    println("start supervisor job")
    val scope = CoroutineScope(SupervisorJob())
    scope.launch { // a
        delay(500)
        throw Error("Some error")
    }
    scope.launch { // b
        delay(1000)
        println("Will be printed")
    }
    delay(1500)
}

/**
 * child에서 exception 발생 시, 전체가 취소되어버린다.
 */
fun exceptionWithNormalJob() = runBlocking{
    println("start normal job")
    val scope = CoroutineScope(Dispatchers.Default)

    scope.launch { // a
        delay(500)
        throw Error("Some error")
    }
    scope.launch { // b
        delay(1000)
        println("Will be printed")
    }
    delay(1500)
}