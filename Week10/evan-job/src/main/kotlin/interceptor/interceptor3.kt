package interceptor

import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

fun main(){
    runBlocking(context = myInterceptor + CoroutineName("myRunBlocking") + Dispatchers.Unconfined){
        println("1 runBlocking start: ${Thread.currentThread().name}")
        delay(1000L)
        println("2 runBlocking end: ${Thread.currentThread().name}")
    }
}