---
title: Refactoring
type: slide
slideOptions: 
    center: false
---

# Refactoring

---

# Refactoring

소프트웨어의 겉보기 동작(사용자의 입장)은 그대로 유지한 채, 코드를 이해하고 수정하기 쉽도록 내부 구조를 변경하는 기법

- 심지어 버그까지도 유지가 되어야한다.
(물론, 발견되었다면 수정)

- 메모리보다는 가독성

- 코드량이 늘어날 수 있음

- 성능 저하 있을 수 있으나 미약함

---

## 리팩토링 예

-- Extract Method

```java!=
// Before
void printOwing() {
  printBanner();
  System.out.println("name: " + name);
  System.out.println("amount: " + getOutstanding());
}






// After
void printOwing() {
  printBanner();
  printDetails(getOutstanding());
}

void printDetails(double outstanding) {
  System.out.println("name: " + name);
  System.out.println("amount: " + outstanding);
}
```

참고: https://refactoring.guru/

---

## 리팩토링 예

-- Inline Method

```java!=
// Before
int getRating() {
    return moreThanFiveLateDeliveries() ? 2 : 1;
}

boolean moreThanFiveLateDeliveries() {
    return numberOfLateDeliveries > 5;
}




// After
class PizzaDelivery {
  int getRating() {
    return numberOfLateDeliveries > 5 ? 2 : 1;
  }
}
```

---

## 리팩토링 예

-- Extract Variable

```java!=
// Before
void renderBanner() {
  if ((platform.toUpperCase().indexOf("MAC") > -1) &&
       (browser.toUpperCase().indexOf("IE") > -1) &&
        wasInitialized() && resize > 0 )
  {
    // do something
  }
}



// After
void renderBanner() {
  final boolean isMacOs = platform.toUpperCase().indexOf("MAC") > -1;
  final boolean isIE = browser.toUpperCase().indexOf("IE") > -1;
  final boolean wasResized = resize > 0;

  if (isMacOs && isIE && wasInitialized() && wasResized) {
    // do something
  }
}
```

---

## 리팩토링 예

-- Inline Temp

```java!=
// Before
boolean hasDiscount(Order order) {
  double basePrice = order.basePrice();
  return basePrice > 1000;
}







// After
boolean hasDiscount(Order order) {
  return order.basePrice() > 1000;
}
```

---

## 만병통치약도 아닌 리팩토링, 왜 할까?

- 리팩터링을 하지 않으면 썩기 쉽다.
  - 나중에 유지하기가 어려워진다.

- 리팩터링하면 이해하기 쉬워진다.
  - 알 것이라고 생각하고 의도적으로 기억하지 않았던 경험?

---

## 만병통치약도 아닌 리팩토링, 왜 할까?

- 리팩터링하면 버그를 쉽게 찾을 수 있다.
  - 점점 명확해지니, 못 찾던 버그도 찾게된다.(뛰어난 개발자가 아니더라도, 좋은 습관으로 인해 괜찮은 개발자가 될 수 있다.)
  
- 리팩터링하면 프로그래밍 속도를 높일 수 있다.
  - 장기적으로 개발 속도를 높일 수 있다.(단기적으론 시간이 걸려보이지만...)

---

## 리팩토링 순서

컴파일 - 테스트 - 커밋

잘게 잘게 작업한다

---

## 두 개의 모자(Two hats)

-- 기능 추가 모자

-- 리팩터링 모자

---

## 두 개의 모자(Two hats)

-- 기능 추가 모자: 기존 코드를 절!대! 건들지 않고 새 기능만 추가

-- 리팩터링 모자: 기능 추가는 절!대! 하지 않기로 다짐 후 코드 재구성에 전념

---

## 리팩토링을 언제할까?

- 3의 법칙
  - 스트라이크 3번이면 리팩토링하라

- 리팩토링을 하지 말아야할 때...
  - 잘 돌아가는 건 건들지 마라

---

## 리팩토링을 언제할까?

- 스트라이크 3번이면 리팩토링하라
  - 비슷한 일을 세 번할 때

- 잘 돌아가는 건 건들지 마라
  - 해당 코드를 건드려야하는 경우에 리팩토링한다.
  (코드를 잘 이해하고 있어야 리팩토링이 잘 된다.)

---

## 리팩토링에 대한 오해와 고려사항

- 리팩토링은 'Clean Code' 혹은 '바람직한 엔지니어링 습관'이 아닌, 단지 경제적인 이유로 하는 것이다.
  - 기능 추가 시간을 줄이고, 버그 수정 시간을 줄여준다.

---

## 리팩토링에 대한 고려사항

- 소유권: 여러 팀이 나눠서 작업할 때
  - 이미 사용되고 있던 다른 코드를 수정할 수 없기에 함수안의 함수를 호출하는 방식으로 처리한다.
  - 오픈 소스 개발 모델을 권장하기도 한다.

---

## 리팩토링에 대한 고려사항

- 브랜치: 아주 짧게 관리한다.
  - CI(Continuous Integration): 모든 팀원이 최소 한 번 마스터와 통합한다.
  -- XP(Extreme Programming)*
  - TBD(Trunk-Based Development)
  - 기능을 잘게 쪼개는 방법을 배워야한다.
  - 기능 토글(feature toggle 혹은 기능 플래그(feature flag)) 적용하여 시스템을 망치지 않도록 해야한다.

참고: [Accelerate: The Science of Learn Software and DevOps - IT Reveolution Press, 2018](https://www.amazon.com/Accelerate-Software-Performing-Technology-Organizations/dp/1942788339)

---

## 리팩토링에 대한 고려사항

- 레거시 코드
  대체로 복잡하고 테스트가 없다. 더군다나 남이 짠 경우가 많아 끔찍하다.
  - 리팩토링을 하면 거친 원석을 반짝이는 보석으로 다듬을 수 있다.
  - 테스트부터 보강한다.
  - [레거시 코드 활용 전략(2018)](http://www.kyobobook.co.kr/product/detailViewKor.laf?mallGb=KOR&ejkGb=KOR&barcode=9791161752075)에 나온 지침을 따른다.

---

## 리팩토링에 대한 고려사항

- 레거시 코드 활용 전략
    - 테스트를 추가할 틈새를 찾아서 시스템을 테스트해야 한다.
    - 틈새를 만들 때 리팩토링이 활용된다.
    - 위험할 수 밖에 없지만 감내해야한다.
    - 아쉽게도 더 나은 방법은 없다.

---

## 리팩토링에 대한 고려사항

- 데이터베이스
    - 프라모드 사달라게(Pramod Sadalge)
      - [진화형 데이터베이스 설계(Evolutionary Database Design)](https://martinfowler.com/articles/evodb.html)
      - [데이터베이스 리팩토링(Database Refactoring)](https://martinfowler.com/books/refactoringDatabases.html)
  - 마이그레이션 스크립트를 작성한다.
    - 접근 코드와 데이터베이스 스키마에 대한 구조적 변경을 스크립트에 작성한다.
  - 아무래도 다른 리팩토링보다 쉽지는 않기 때문에 여러번 나눠서 리팩토링한다.

---

## 리팩토링&소프트웨어 개발 프로세스

- 자가 테스트 코드+리팩터링 ~> TDD
- [YAGNI(You Aren't Gonna Need It)](https://ko.wikipedia.org/wiki/YAGNI): 실제로 필요할 때 무조건 구현하되, 그저 필요할 것이라고 예상할 때에는 절대 구현하지 말라

---

## 더 알고 싶다면

- 리팩터링 워크북(인사이트, 2006)
- 패턴을 활요한 리팩터링(인사이트, 2011)
- 리팩토링 데이터베이스(위키북스, 2007)
- 리팩토링 HTML(에이콘출판사, 2009)
- 레거시 코드 활용 전략(에이코눌판사, 2018): 테스트 커버리지가 낮은 오래된 코드 전용
- Refactoring: Ruby Edition(Additon-Wesley, 2009) - Ruby 언어 특화
- https://github.com/WegraLee/Refactoring
- https://refactoring.com

---

## Refactoring

- Extract Functions
- Replace Conditions with Polymorphysm

참고: https://refactoring.guru/

---

## Refactoring

### Extract Functions

함수 추출하기

---

## Refactoring

### Replace Conditions with Polymorphysm

조건문을 다형성으로 바꾸기

---
