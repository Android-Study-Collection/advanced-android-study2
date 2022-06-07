
1. 테스트 코드를 짜야하는 이유: automation, scaleable. faster, repeatable, actionable feedback


2. 대상(Subject)에 따른 테스트 종류

Functional testing: 내 앱은 원래 뭘 해야하나?

Performance testing: 빠르고 효율적인가?

Accessibility testing: 접근성 서비스가 잘 동작하나?

Compatibility testing: 모든 디바이스 혹은 모든 API 레벨에서 동작하는가?


3. 크기(Scope)에 따른 테스트 종류

Unit tests(Small Test): 메서드나 class 같은 앱의 작은 부분을 테스트

Intergration tests(Medium Test) : 두 개 이상의 유닛을 테스트

End-to-end(Large Test): 화면 전체 혹은 사용자 플로우 같은 테스트


4. 기기 사용 유무에 따른 테스트 종류

계측 테스트(Instrumented tests): 실제 디바이스 혹은 에뮬레이터에서 동작하는 테스트로, 앱이 실행된 후, 앱의 상태를 테스트한다.

로컬 테스트(local tests/host-side tests): 개발 PC나 서버에서 테스트하는 것으로 빠르며, 보통 테스트 단위가 크기가 작다. 기기의 상태가 필요없는 부분들을 테스트한다.

* 하지만, 모든 Unit Test가 Local Test가 아니며, 반대로 모든 Instrumented Test가 기기 테스트는 아니다. 예를 들어, 다음과 같은 것들이 있다.

Big local test: Robolectric 라이브러리는 Local에서도 실기기(혹은 에뮬레이터)처럼 돌릴 수 있다.

Small instrumented test: SQLite database 같은 경우, 여러 기기, 여러 SQLite 버전에서 테스트를 할 수 있는데, 실기기(혹은 에뮬레이터)를 돌리지 않고 Robotics 등을 사용하여 Local에서 돌릴 수 있다.(다만, 권장되는 방법은 아니다.)


5. Unit Test와 UI Test(Instrumented Test) 예시

- Unit Test는 다음과 같이 어떤 데이터에 행위에 대한 결과만 확인한다.

```
// DI 용도의 repository를 사용하여, MyViewModel를 생성한다.
val viewModel = MyViewModel(myFakeDataRepository)

// 데이터를 준비 혹은 어떤 행위를 한다.
viewModel.loadData()

// 제대로 동작이 되었는지 확인한다.
assertTrue(viewModel.data != null)
```

- UI Test는 다음과 같이 View 이벤트를 주고, 제대로 View에 반영이 되었는지 테스트한다.

```
// Continue란 글자가 있는 View를 클릭한다.
onView(withText("Continue")).perform(click())

// Welcome이란 글씨가 있는 View가 보여지는지 확인한다.
onView(withText("Welcome")).check(matches(isDisplayed()))
```

6. 테스트 전략

이상적으로는 모든 코드 한 줄 한 줄마다 테스트 코드를 작성하는 게 좋다. 하지만, 실제로 하기엔 너무 작업속도가 느리며, 가성비가 좋지 않다. 그러므로, fidelity가 높은지에 대해 고려해가며 밸런스를 찾는 것이 중요하다.

High-fidelity(UI Test)는 속도가 느리며, Low-fidelity(Unit Test)는 로컬에서 도는 한계가 있다. 그래서 High-fidelity보다는 Low-fidelity로 하되, 되도록이면 필요시에만 High-fidelity로 하는 것이 필요하다.


7. Testable app architecture

테스트가 쉽게 만드는 아키텍쳐는 readability, maintainability, scalability, reusability를 좋게한다.

반대로, 테스트가 어렵게된 아키텍쳐는,

크고, 느리며, 버그를 생성할 수 있는(flaky) 코드이다. 큰 코드는 Unit Test 화할 수 없고 Instrumented test로 진행하게 된다.(되도록 Unit Test를 할 수 있도록 짜야한다.)

여러 시나리오로 코드 활용할 수 있는 기회가 적다. 또한, 계측 테스트는 느리고, 모든 케이스를 테스트하기엔 너무 비현실적이 될 수 있다.


8. decoupling

Testable한 아키텍쳐를 위한 것으로, decoupling이 있다. 이것은 메소드, 클래스, 모듈을 나눌 수 있으면, 최대한 나누는 것이다.

Presentation, Domain, Data로 나누고, 기능별로 module 나누기

Activity나 Fragment에 로직 넣지 않기: 의존이 많은 class 내에서는 로직을 처리하면 안된다. Composable, ViewModel 혹은 다른 domain layer에 로직을 작성해야한다.

Business Logic이 있는 class에 디펜던시 넣지 않기: 이를테면 ViewModel에 Context를 사용하지 않는 것이다.

Interface(Dependency Injection)를 사용한다. 이는 의존성을 낮추고 다른 걸로 대체하기 쉽게 해준다.


참고:

https://developer.android.com/training/testing/fundamentals


--------------------------------------------------------------
espresso를 통한 UI 테스트 방법 업데이트.

[androidTest 바로가기](https://github.com/Android-Study-Collection/advanced-android-study2/blob/main/chan/Week1/MyApp/app/src/androidTest/java/com/chanjungkim/myapp/components/ui/main/MainActivityTest.kt)

