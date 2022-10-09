---
title: Coroutine-Dispachers
type: slide
slideOptions: 
    center: false
---

# Coroutine-Dispachers

---

## Dispatcher 종류

- Default
- IO
- Main
- Unconfined(제한을 받지 않는, 무한정의*)

참고: [kotlinlang-CoroutineDispatcher](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-dispatcher/)

---

## 필수 용어

- CoroutineScope
- Coroutine
- [Continuation](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-continuation.html)
- [CoroutineContext](https://github.com/JetBrains/kotlin/blob/ea836fd46a1fef07d77c96f9d7e8d7807f793453/libraries/stdlib/src/kotlin/coroutines/CoroutineContext.kt#L14)
- [CoroutineDispatcher](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/common/src/CoroutineDispatcher.kt#L10)

---

## 구성도

![coroutine 구성도](https://i.imgur.com/pWU19RN.png)

---

### 관계도

![coroutine 관계도](https://i.imgur.com/BP3Slu6.png)

---

### Dispatcher 사전적 의미

- Dispatcher: 운행 관리원, (차량) 배치 관리자
  ref. scheduler
- Dispatch: 보내다, 파견하다, 출동시키다, 죽이다, 처치하다
  ref. dispatch rider(퀵 배달원), with dispatch(신속하게 효율적으로), dispatch box(공문서 송달함)
- the act of sending someone or something to a particular place for a particular purpose

참고: [Kotlin World - Coroutine의 Dispatcher란 무엇인가?](https://kotlinworld.com/141)

---

![ThreadPool 동작](https://i.imgur.com/JTQDVk1.png)

---

### [CoroutineDispatcher](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/common/src/CoroutineDispatcher.kt#L10)

```!kotlin=
public abstract class CoroutineDispatcher :
    AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {

    /** @suppress */
    @ExperimentalStdlibApi
    public companion object Key : AbstractCoroutineContextKey<ContinuationInterceptor, CoroutineDispatcher>(
        ContinuationInterceptor,
        { it as? CoroutineDispatcher })

    public open fun isDispatchNeeded(context: CoroutineContext): Boolean = true

    public abstract fun dispatch(context: CoroutineContext, block: Runnable)

    @InternalCoroutinesApi
    public open fun dispatchYield(context: CoroutineContext, block: Runnable): Unit = dispatch(context, block)

    public final override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
        DispatchedContinuation(this, continuation)

    public final override fun releaseInterceptedContinuation(continuation: Continuation<*>) {
        val dispatched = continuation as DispatchedContinuation<*>
        dispatched.release()
    }
}
```

---

#### class 해설

- context는 builder에서 정한 값이며, 정하지 않았다면 EmptyCoroutineContext로 전달된다.
- dispatch(context, block): Runnable 객체인 block으로 Task를 실행함.
- isDispatchNeeded(context): Dispatch가 필요한지 체크, true면 dispatch() 호출.
    - false면 현재 Thread에서 실행함. 후자의 경우 StackOverflow 방지를 위해 Event Loop 사용(Queue)
-  [ContinuationInterceptor](https://github.com/JetBrains/kotlin/blob/ea836fd46a1fef07d77c96f9d7e8d7807f793453/libraries/stdlib/src/kotlin/coroutines/ContinuationInterceptor.kt#L20)에 의해서 흐름 컨트롤을 함

---

#### [ContinuationIntercepter](https://github.com/JetBrains/kotlin/blob/ea836fd46a1fef07d77c96f9d7e8d7807f793453/libraries/stdlib/src/kotlin/coroutines/ContinuationInterceptor.kt#L20)

- interceptContinuation(): continuation을 넘김
- releaseInterceptedContinuation(): 아무것도 하지 않음

참고:[KotlinConf 2017 - Deep Dive into Coroutines on JVM by Roman Elizarov](https://youtu.be/YrrUCSi72E8?t=1223)
([pdf](https://resources.jetbrains.com/storage/products/kotlinconf2017/slides/2017+KotlinConf+-+Deep+dive+into+Coroutines+on+JVM.pdf))

---

#### [DispatchedContinuation](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/common/src/internal/DispatchedContinuation.kt)

```!kotlin=
class DispatchedContinuation<in T>(
    val dispatcher: CoroutineDispatcher,
    val continuation: Continuation<T>
): Continuation<T> by continuation {
    override fun resume(value: T) {
        dispatcher.dispatch(context, DispatchTask(…))
    }
    …
}
```

---

#### Key가 무엇일까?

```!kotlin=
@OptIn(ExperimentalStdlibApi::class)
class DispatcherKeyTest : TestBase() {

    companion object CustomInterceptor : AbstractCoroutineContextElement(ContinuationInterceptor),
        ContinuationInterceptor {
        override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
            return continuation
        }
    }

    private val name = CoroutineName("test")

    @Test
    fun testDispatcher() {
        val context = name + CustomInterceptor
        assertNull(context[CoroutineDispatcher])
        assertSame(CustomInterceptor, context[ContinuationInterceptor])

        val updated = context + Dispatchers.Main
        val result: CoroutineDispatcher? = updated[CoroutineDispatcher]
        assertSame(Dispatchers.Main, result)
        assertSame(Dispatchers.Main, updated[ContinuationInterceptor])
        assertEquals(name, updated.minusKey(CoroutineDispatcher))
        assertEquals(name, updated.minusKey(ContinuationInterceptor))
    }

    @Test
    fun testExecutorCoroutineDispatcher() {
        val context = name + CustomInterceptor
        assertNull(context[ExecutorCoroutineDispatcher])
        val updated = context + Dispatchers.Main
        assertNull(updated[ExecutorCoroutineDispatcher])
        val executor = Dispatchers.Default
        val updated2 = updated + executor
        assertSame(Dispatchers.Default, updated2[ContinuationInterceptor])
        assertSame(Dispatchers.Default, updated2[CoroutineDispatcher])
        assertSame(Dispatchers.Default as ExecutorCoroutineDispatcher, updated2[ExecutorCoroutineDispatcher])
        assertEquals(name, updated2.minusKey(ContinuationInterceptor))
        assertEquals(name, updated2.minusKey(CoroutineDispatcher))
        assertEquals(name, updated2.minusKey(ExecutorCoroutineDispatcher))
    }
}
```

---

## Dispatchers
- [ThreadPoolDispatcher](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/src/ThreadPoolDispatcher.kt)
    - [newSingleThreadContext(name)](https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#jumping-between-threads)
    - newFixedThreadPoolContext(number_of_thread, name)
- [common Dispatchers](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/common/src/Dispatchers.common.kt#L23)
    - Default(CoroutineDispatcher): DefaultScheduler
    - Main(MainCoroutineDispatcher): MainDispatcherLoader.dispatcher
    - Unconfined(CoroutineDispatcher): kotlinx.coroutines.Unconfined
    - IO(CoroutineDispatcher): DefaultIoScheduler

---

## 기타
- [Runnable](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/src/Runnable.kt)
- [Event Loop](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/src/EventLoop.kt)
- [DispatchedTask](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/common/src/internal/DispatchedTask.kt)
- Worker
- Scheduler
- [Job](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/common/src/Job.kt)
