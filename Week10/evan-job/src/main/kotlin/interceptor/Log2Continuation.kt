
package interceptor

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

class Log2Continuation<T>(
    private val cont: Continuation<T>
) : Continuation<T> {
    override val context: CoroutineContext
        get() = cont.context
    override fun resumeWith(result: Result<T>) {
        println("Log: LogContinuation resume with thread: ${Thread.currentThread().name}")
        cont.resumeWith(result)
    }
}