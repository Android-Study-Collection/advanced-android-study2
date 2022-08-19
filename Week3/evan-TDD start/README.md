Android Test-Driven Development by raywenderlich Tutorial Team

1. Red->Green->Refactor

- 실패하도록 코딩한다.

```
fun main(){
  val nullResult = getSearchUrl(null)

  if(nullResult == null){
    println("Success")
  }else{
    throw AssertionError("Result was not null")
  }
}

// 테스트 대상인 빈 메소드를 만든다.
fun getSearchUrl(query: String?): String? {
  return "" // 왜 null로 하지 않았는가? -> 처음엔 Fail이 되는 지 확인하기 위해, 무조건 Fail 케이스를 만들어야한다.
}
```

테스트란, 원치 않는 방식으로 코딩을 했을 때, Fail이 보여지도록 하기 위해 필요한 것이다.
만약 Success만을 보여지기 위하도록 테스트른 짠다면, 놓치는 부분이 생길 것이다.

- 실패하도록 된 부분을 Pass되도록 수정한다.

```
fun main(){
  val nullResult = getSearchUrl(null)

  if(nullResult == null){
    println("Success")
  }else{
    throw AssertionError("Result was not null")
  }
}

fun getSearchUrl(query: String?): String? {
  return null
}
```

Success를 확인한다.

- 테스트 케이스를 추가한다.

```
fun main(){
  // Test case 1: null일 때, 제대로 동작하는 지 확인
  val nullResult = getSearchUrl(null)

  if(nullResult == null){
    println("Success")
  }else{
    throw AssertionError("Result was not null")
  }

  // Test case 2: 문자열일 떄, 제대로 동작되는지 확인
  val nonNullResult = getSearchUrl("toaster")

  if(nonNullResult != null){
    println("Success")
  } else {
    throw AsserionError("Result was null")
  }
}

fun getSearchUrl(query: String?): String? {
  return null // 수정되지 않은 상태 그대로이다.
}
```

- pass 되도록 메소드를 수정한다.

```
fun main(){
  // Test case 1: null일 때, 제대로 동작하는 지 확인
  val nullResult = getSearchUrl(null)

  if(nullResult == null){
    println("Success")
  }else{
    throw AssertionError("Result was not null")
  }

  // Test case 2: 문자열일 떄, 제대로 동작되는지 확인
  val nonNullResult = getSearchUrl("toaster")

  if(nonNullResult != null){
    println("Success")
  } else {
    throw AsserionError("Result was null")
  }
}

fun getSearchUrl(query: String?): String? {
  return query // 일단, query를 넘기자. 그러면, null->null, 문자열->문자열을
return 할 것이다.
}
```

- False Positive와 False Negative

우리말로는 거짓 양성, 거짓 음성이라고 한다.

정답이 NO 인 경우에 YES라고 대답하면 이 오류는 False Postivie이다.
정답이 YES 인 경우, NO라고 대답하면 이 오류는 False Negative라고 한다. 

간단히 생각하려면, 거짓 Postivie/거짓 Negative라고 된다.
거짓인데 긍정(YES)라고 한 것과 거짓인데 부정(NO)라고 한 것이기 때문이다.

테스트를 작성할 때, False Postive가 되지 않도록 해야한다. 그러면, pass가
되어버리기 때문이다.

```
fun main() {
   // Test case 1: null일 때, 제대로 동작하는 지 확인
  val nullResult = getSearchUrl(null)

  if(nullResult == null){
    println("Success")
  }else{
    throw AssertionError("Result was not null")
  }

  // Test case 2: 문자열일 떄, 제대로 동작되는지 확인
  val nonNullResult = getSearchUrl("toaster")

  if(nonNullResult != null){
    println("Success")
  } else {
    throw AsserionError("Result was null")
  } val result = getSearchUrl("toaster")

  if (result?.contains("toaster") == true) {
    println("Success")
  } else {
    throw AssertionError("Result did not contain query")
  }
}

fun getSearchUrl(query: String?): String? {
  return "https://www.google.com/search?q=$query"
}

// Result:
AssertionError: Result was not null
```

여기서 null을 넣으면 null로 리턴값이 되어야하는데 그렇지 않았다.


```
fun main() {
   // Test case 1: null일 때, 제대로 동작하는 지 확인
  val nullResult = getSearchUrl(null)

  if(nullResult == null){
    println("Success")
  }else{
    throw AssertionError("Result was not null")
  }

  // Test case 2: 문자열일 떄, 제대로 동작되는지 확인
  val nonNullResult = getSearchUrl("toaster")

  if(nonNullResult != null){
    println("Success")
  } else {
    throw AsserionError("Result was null")
  } val result = getSearchUrl("toaster")

  if (result?.contains("toaster") == true) {
    println("Success")
  } else {
    throw AssertionError("Result did not contain query")
  }
}

fun getSearchUrl(query: String?): String? {
  val url = "https://www.google.com/search?q=$query"
  return query
}

```

참고: [raywenderlich 튜토리얼](https://www.raywenderlich.com/7109-test-driven-development-tutorial-for-android-getting-started#toc-anchor-001")

2. 테스트의 종류

- UI Test
- Intergration Test
- Unit Test
[Succeeding with Agile]()

- Unit Test(Small Test)

```
class Game() {
  var score = 0
    private set

  var highScore = 0
    private set

  fun incrementScore() {
    // 빈 메소드 선언. 테스트할 메소드.
  }
}
```

```
fun shouldIncrementHighScore_whenIncrementingScore() {
  val game = Game()
  
  game.incrementScore()

  if(game.highScore = 1) {
    println("Success")
  } else {
    throw AssertionError("Score and HighScore don't match")
  }
}

// Result:
Score and HighScore don't match
```

* JUnit, Mockito, Robolectric

- Intergration Test(Medium Test)

여러 다른 기능들 예를 들면, database/filesystem/network/device sensors을 포함.


```
fun shouldLaunchLogin_whenAddingFavorite() {
  // 1
  val user: User = null
  val detailActivity = createProductDetailActivity(user)
  detailActivity.findViewById(R.id.addFavorite).performClick()

  // 2
  val expectedIntent = Intent(detailActivity, LoginActivity::class.java)
  
  // 3
  val actualIntent = getNextStartedActivity()
  if(expectedIntent == actualIntent) {
    println("Success")
  }else{
    throw AssertionError("LoginActivity wasn't launched")    
  }
}
```

* JUnit, Mokito, Robolectric
* Espresso - validation, stubbing of intent, actions on view objects (by Google. 그러나 몇몇은 UI 테스라고 여긴다.)

- UI Test

```
fun shouldWelcomeUser_whenLogin() {
  onView(withId(R.id.username)).perform(typeText("Fernando"))
  onView(withId(R.id.password)).perform(typeText("password"))
  onView(withId(R.id.login_button)).perform(click())
  onView(withText("Hello Fernando!")).check(matches(isDisplayed())
}

```

* Espresso, Robolectric(v4.0)
* [UI Automator](https://codechacha.com/ko/android-uiautomator2/)

3. 적정 비율은?

UI Test: 10%
Integration Test: 20%
Unit Test: 70%

(by Google)

- anti-pattern

1. UI나 Integration Test가 Unit Test가 많다 -> 테스트 문화 X
2. Unit Test가 많지만, Integration Test가 UI Test보다 적다 -> 시간이 오래
   걸려서 좋지 않다.

- testing pyramid를 따라야한다.

- 참고
[Teesting
Fundamentals](https://developer.android.com/training/testing/fundamentals#testing-pyramid)
[Unit Tests](https://youtu.be/pK7W5npkhho?t=111)
[Integration Tests](https://youtu.be/pk7W5npkhho?t=1915)
[UI Tests](https://youtu.be/pk7W5npkhho?t=1838)
[The Practical Test
Pyramid](https://martinfowler.com/articles/practical-test-pyramid.html)
[Google Testing Blog-Just Say No to More End-to-End
Tests](https://testing.googleblog.com/2015/04/just-say-no-to-more-end-to-end-tests.html)
[Succeeding with Agile
book](https://www.amazon.com/Succeeding-Agile-Software-Development-Using/dp/0321579364)


