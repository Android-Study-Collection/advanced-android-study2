package interceptor

import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

val myInterceptor2 = object: ContinuationInterceptor{
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor.Key

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        println("5 interceptContinuation: ${continuation.context[CoroutineName].toString()}")
        return Log2Continuation(continuation)
    }

    override fun releaseInterceptedContinuation(continuation: Continuation<*>) {
        println("6 releaseInterceptedContinuation: ${continuation.context[CoroutineName].toString()}")
    }
}

fun main(){
    runBlocking(context = myInterceptor + CoroutineName("myRunBlocking") + myInterceptor2 + CoroutineName("myRunBlocking2")){
        println("1 runBlocking start: ${Thread.currentThread().name}")
        delay(1000L)
        println("2 runBlocking end: ${Thread.currentThread().name}")
        repeat(1){
            println("${7+it} runBlocking start: ${Thread.currentThread().name}")
            println("${8+it} runBlocking end: ${Thread.currentThread().name}")
        }
    }
}