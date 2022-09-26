package interceptor

import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

val myInterceptor = object: ContinuationInterceptor{
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor.Key

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        println("3 interceptContinuation: ${continuation.context[CoroutineName].toString()}")
        return LogContinuation(continuation)
    }

    override fun releaseInterceptedContinuation(continuation: Continuation<*>) {
        println("4 releaseInterceptedContinuation: ${continuation.context[CoroutineName].toString()}")
    }
}

fun main(){
    runBlocking(context = myInterceptor + CoroutineName("myRunBlocking")){
        println("1 runBlocking start: ${Thread.currentThread().name}")
        delay(1000L)
        println("2 runBlocking end: ${Thread.currentThread().name}")
    }
}