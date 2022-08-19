---
title: Mock이란?
type: slide
slideOptions: 
    center: false
---

# Mock이란?

---

# What is ___?

### - Fake

### - Test Double

---

## Fake

테스트를 하기 위한 환경을 만드는 class 혹은 component.

비행 훈련을 생각해보자. 실제 비행은 아니지만, 실제 환경과 유사한 환경 및 훈련 목적에 맞는 기대하는 환경을 만들어 훈련을 진행할 수 있게한다.

[- What are fakes in testing](https://canro91.github.io/2021/05/24/WhatAreFakesInTesting/#:~:text=Now%20that%20we%20know%20what,called%20with%20the%20right%20parameters.)

---

## Test Double

A test double is an object that can stand in for a real object in a test, similar to how a stunt double stands in for an actor in a movie

(stand-in: 대역, stand in for: ~를 대신하다)

참고: 
[What do you mean by test doubles](https://medium.com/@kashwin95kumar/what-do-you-mean-by-test-doubles-b57a2a792973)

---

```kotlin!
class CreditCardValidator: AbstractValidator<CreditCard>{
    var now
        
    init{
        now = systemClock.Now
        ..
    }
}
```

```kotlin!
interface ISystemClock{
    var now: System.DateTime
}

class SystemClock : ISystemClock{
    var now: DateTime = DateTime.Now
}
```

---

### 어느 부분이 fake일까?

```kotlin!
class CreditCardValidationTest{
    fun CreditCard_ExpiredYear_ReturnsInvalid(){
        val time = DateTime(2021, 01, 01)
        val clock = FixedDateClock(time)
        val validator = CreditCardValidator(clock)
        val request = CreditCardBuilder()
                    .WithExpirationYear(time.AddYears(-1).Year)
                    .Build()
        val result = validator.TestValidate(request)
        result.ShouldHaveAnyValidationError()
    }
}
```

---

### FixedDateClock의 구조

SystemClock은 현재 시간을 가져오기 때문에 테스트하기가 어려웠다. ISystemClock 인터페이스를 사용한다면, 어느 Clock이든지 CreditCardValidator의 파라미터로 넘길 수 있다.
```kotlin!
class FixedDateClock(val time: DateTime) : ISystemClock{
    var now: DateTime = time
}
```
FixedDateClock는 항상 time 값(테스트 환경에서 정할 수 있는)에 따라 Date가 정해진다. 

참고: [How to write tests that use DateTime.Now](https://canro91.github.io/2021/05/10/WriteTestsThatUseDateTimeNow/)

---

# Test Double의 종류

---

# Test Double의 종류

- mock
- stub

---

# Test Double의 종류

- mock: method 호출 체크 목적으로 테스트하는 것
ex) n회 호출 되었는지, 특정 파라미터와 함께 호출 되었는지

- stub: 원하는 값을 설정하거나 예외로 처리하여 테스트 하는 것
ex) a란 메소드를 호출했을 때 무엇을 리턴해야한다고 정의하는 등의 환경을 세팅해놓는 것
ref. return 류

---

# 그 밖의 Test Doubles

- spy: mock보다 좀 더 복잡한 형태
- dummy: 그냥 의미없이 코드가 돌아가게끔만 채워넣기 위한 것들

---

### 예시

```kotlin!
class SystemUnderTest(private val validator: Validator){
    fun publishApiWeWantToTest(): String{
        // validator is used here.(or somewhere else)
        return "OK"
    }
}
```

```kotlin!
interface Validator{
    fun validate(name: String): Boolean
}
```

참고:
[- Youtube-Everything About Test-Doubles](https://www.youtube.com/watch?v=YQ9qlcq6Yyg)

---

#### Dummy
```kotlin!
class DummyValidator: Validator{
    override fun validate(name: String): Boolean{
        throw IlligalStateException("Should not be called")
    }
}
```

#### Stub
```kotlin!
class PassingValidator: Validator{
    override fun validate(name: String): Boolean{
        return true
    }
}

---

#### Spy
```kotlin!
class SpyValidator: Validator{
    var wasCalled = false // can be checked method calling
    var doesValid = false // can be manipulated from outside
    override fun validate(name: String): Boolean{
        wasCalled = true
        return doesValid
    }
}
```

---

#### Mock
```kotlin!
class MockValidator: Validator{
    private var wasCalled = false // can be checked method calling
    var doesValid = false // can be manipulated from outside
    override fun validate(name: String): Boolean{
        wasCalled = true
        return doesValid
    }
    
    fun verifyValidationWasCalled(){
        assertTrue(wasCalled)
    }
}
```
spy와 다른점은 mock 안에는 verification이 들어있다.

---

참고: 
[- What's the difference between faking mocking and stubbing](https://stackoverflow.com/questions/346372/whats-the-difference-between-faking-mocking-and-stubbing)
[- TheLittleMocker](https://blog.cleancoder.com/uncle-bob/2014/05/14/TheLittleMocker.html)
