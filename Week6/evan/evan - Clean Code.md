---
title: Clean Code
type: slide
slideOptions: 
    center: false
---

# Clean Code

---

> "I like my code to be elegant and efficient ... clean code does one thing well"
> -Bjarne Stroustrup[야니 스트로스트럽], C++ 만든이
> 
> "Clean code is simple and direct. Clean code reads like well-written prose..."
> -Grady Booch
> *prose: 산문
> 
> "Clean code always looks like it was written by someone who cares (ME)."
> -Michael Feathers
> 
> "You know you are working on clean code when each routinee you read turns out to be pretty much what. you expected"
> -Ward Cunningham, [wiki](http://c2.com/) 창시자, TDD, Pair programming 등 애자일 관련 개념을 만듦.

참고: [Clean Code with Uncle Bob](https://www.youtube.com/watch?v=8SMOB6k3hkM)

---

## 어떻게 작성해야할까?

---

## 어떻게 작성해야할까?

### 1. The rules of functions.
### 2. Avoid switch statements.
### 3. Use descriptive names.
### 4. No more than three arguments.

---

### 1. The rules of functions

- The First Rule:
  - They should be small.
- The Second Rule:
  - They should be smaller than that.

> A function does one thing if you canno meaningfully extract another function from it. ... Extract, extract, extract until you can't extract anymore.
> -Uncle Bob, from [Clean Code with Uncle Bob]

참고: [Clean Code with Uncle Bob](https://www.youtube.com/watch?v=8SMOB6k3hkM)

---

```java!

```

---

> Clean 하지 않은 코드를 작성한 사람은 예의가 없는 사람이다.
> -Uncle Bob

참고: [Clean Code with Uncle Bob, 22:27](https://youtu.be/8SMOB6k3hkM?t=1347)

---

### 2. Avoid Switch Statements

Replace conditional with polymorphism

```java!=
class Bird {
  // ...
  double getSpeed() {
    switch (type) {
      case EUROPEAN:
        return getBaseSpeed();
      case AFRICAN:
        return getBaseSpeed() - getLoadFactor() * numberOfCoconuts;
      case NORWEGIAN_BLUE:
        return (isNailed) ? 0 : getBaseSpeed(voltage);
    }
    throw new RuntimeException("Should be unreachable");
  }
}
```
Bird가 3종류있다면, condition을 사용하지 말고, 3종류의 새를 만든다.

---

### 2. Avoid Switch Statements

```java!=
abstract class Bird {
  // ...
  abstract double getSpeed();
}

class European extends Bird {
  double getSpeed() {
    return getBaseSpeed();
  }
}

class African extends Bird {
  double getSpeed() {
    return getBaseSpeed() - getLoadFactor() * numberOfCoconuts;
  }
}

class NorwegianBlue extends Bird {
  double getSpeed() {
    return (isNailed) ? 0 : getBaseSpeed(voltage);
  }
}
```

참고: [Stackoverflow - Replace conditional with polymorphism](https://stackoverflow.com/questions/48377525/replace-conditional-with-polymorphism)

---

### 3. Use descriptive names.

- Variables
- Functions
- Classes and others

---

#### 얼마나 길어야 할까?

- variable: scope에 따라 달라진다.
  - for loop: nobody cares.(ex. i, j)
  - local variable: a word
  - instance variable: maybe 2-3 words
  - global variable: longer name

- function: variable과 반대로 general할 수록 짧아진다.
  - 최대한 extract해서 길게 작성한다. 

- class: function처럼 큰 scope일수록 작고 작은 scope일 수록 짧아진다.

---

#### 4. No more than three arguments.

- 최대 3개로 한다. (Uncle Bob 의견)
- constructor등도 마찬가지로 많은 argument를 사용하지 않는다.

---

참고: 
- [Refactor](http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9791162242742&orderClick=LAG&Kc=) by martin folwer
- [refactoring.guru](https://refactoring.guru/refactoring/techniques)
