---
type: slide
slideOptions: 
    center: false
---

### 테스트 라이브러리의 종류와 용도

- 사용한 적이 있거나, 들어본 라이브러리들이 있나요?

---

1. JUnit4
2. Espresso
3. Mockito
4. MockK
5. Robolectric
6. UIAutomator2
7. PowerMock

---

1. JUnit4
Java Unit Test를 위한 라이브러리.
안드로이드 스튜디오에서 default임.

2. Espresso
Android UI 테스트를 위한 라이브러리.
안드로이드 스튜디오에서 default임.
참고:
https://developer.android.com/training/testing/espresso

---

3. Mockito
Java Mock 라이브러리
모히또(Java Mojito) 로고 - Tasty mocking framework
mock/stub/spy

참고:
Test Double - https://tecoble.techcourse.co.kr/post/2020-09-19-what-is-test-double/
https://site.mockito.org/
https://www.youtube.com/watch?v=DJDBl0vURD4
https://www.youtube.com/watch?v=YdtknE_yPk4
https://github.com/woowacourse/jwp-refactoring/pull/2
https://github.com/woowacourse/jwp-refactoring/pull/12

---

4. MockK
Kotlin Mock 라이브러리
Mockito는 Kotlin의 장점을 살리지 못한다.(ex. default 값)
참고:
https://github.com/mockk/mockk
https://www.ericthecoder.com/2021/07/20/why-im-switching-from-mockito-to-mockk/

---

5. Robolectric
로컬 테스트에서 android emulator 환경을 가상으로 만들어주는 라이브러리.

6. UIAutomator2
기기를 테스트할 때 사용되는 라이브러리.
Espresso보다 접근 가능 범위가 훨씬 넓다(device 테스트). - 다른 앱(ex. 설정), 노티피케이션, 홈버튼 등
View id 접근 가능

7. MockWebServer
okhttp3 테스트 라이브러리
참고:
with Espresso: https://www.raywenderlich.com/33855511-testing-rest-apis-using-mockwebserver

---

8. turbine
flow 테스트 하기 위한 라이브러리
참고:
https://github.com/cashapp/turbine

9. PowerMock
확장성 있는 Mock 라이브러리
참고:
https://github.com/powermock/powermock

10. GTest
Google Test의 약자로 NDK(cpp)를 테스트하기 위한 라이브러리
JUnit으로 브릿지 형태로 NDK와 연결 가능함
cpp로 구현해야함
참고:
https://github.com/google/googletest

---

### 권용근 - 무엇을 테스트할 것인가? 어떻게 테스트할 것인가?
1. 안정감과 자신감(나 뿐만이 아닌 모두에게)
2. 구현이 아닌 설계를 테스트
메소드1:테스트1 형태로의 테스트가 아닌 설계를 테스트 
3. 테스트가능한 것, 불가능한 것
변화하는 것들(ex. 시간, random, 외부 저장소 등)
call tree의 마지막 메소드가 테스트 불가능하면 관련 메소드들은 모두 테스트
불가능
4. context가 정말 필요한지 판단할 것
5. 테스트 안에서 의도와 목적 분명하게
어떠한 조건에서 무엇을 수행했을 때, 어떤 것을 검증할 것인지
6. 테스트 코드도 리팩토링
참고:
https://www.youtube.com/watch?v=YdtknE_yPk4

---

### 정진욱 - 테스트하기 쉬운 코드로 개발하기
1. 테스트하기 쉬운 코드란?
-- 같은 인풋이면 항상 같은 결과를 반환하는 코드(Deterministic)
-- 외부 상태를 변경하지 않는 코드(ex. io관련)-no side effect
2. 다음 중 테스트하기 어려운 단계는?
-- ConferenceRegistration 유효성 검사
-- 이미 등록된 좌석 수 DB에서 읽어오기*
-- 요청 좌석 수가 확보 가능한 지 판단
-- 등록 정보 저장*
-- HTTP 결과 반환

---

3. 테스트 가능하도록 하기(db)
-- 테스트 쉬운 코드와 어려운 코드 분리 - 일단, 어려운 부분 repository 안에 둔다.
-- 두 부류의 코드 어디서 만나야하나? - call tree에서 바깥으로 꺼내자

- TDD 구현을 해보자.
TDD: Test를 만족하는 만큼만 코드를 구현하라
테스트 보다 구현 코드가 많게 되면, 커버리지를 넘어버리는 문제가 발생
-- 등록된 좌석 수 읽기 -> db의 data가 필요함 -> 준비 -> 반복 코드가 발생 ->
하나로 통일하여 재사용가능하도록 변경
(테스트가 어렵다는 건 테스트 불가능하다는 건 아님)
- Mock을 남발하지 말자
참고:
https://www.youtube.com/watch?v=Cz_a2gQp63c

