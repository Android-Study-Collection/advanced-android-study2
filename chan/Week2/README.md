- LiveData 관련

1. LiveData.map{} VS LiveData.switchMap{}

```
private val growZone = MutableLiveData<GrowZone>(NoGrowZone)

/**
* A list of plants that updates based on the current filter.
*/
val plants: LiveData<List<Plant>> = growZone.switchMap { growZone ->
    if (growZone == NoGrowZone) {
        plantRepository.plants
    } else {
        plantRepository.getPlantsWithGrowZone(growZone)
    }
}
```
map{}: 필터링만 하고자 할 때, ex. 리스트의 값들이 변경됨

switchMap{}: 값자체를 바꾸고자 할 때 (기존 값이 변경되는 장정) ex. 리스트가 달라짐

2. distinctUntilChanged()

동일한 값이 있을 때, 이벤트 처리하지 않음

```
private val _number: MutableLiveData<Int> = MutableLiveData()
val number: LiveData<Int>
  get() = Transformations.distinctUntilChanged(_number)
```

(androidx.lifecycle 라이브러리에 포함되어있음)

3. asFlow()

Flow로 사용하고자 할 때.

4. emit() vs emitSource()

(코루틴의) liveData{} 블록에서 사용되며,

emit: value를 넣음. 값이 LiveData에 set 될 때까지 기다린다. (suspension point)
emitSource: LiveData 자체를 넣음. MediatorLiveData.addSource와 유사하다. (기존에 세팅되었던 값은 지움)

```
val user = liveData {
    // dispatch loading first
    emit(LOADING(id))
    // check local storage
    val cached = cache.loadUser(id)
    if (cached != null) {
        emit(cached)
    }
    if (cached == null || cached.isStale()) {
        val fresh = api.fetch(id) // errors are ignored for brevity
        cache.save(fresh)
        emit(fresh)
    }
}
```

```
val user = liveData {
    val fromDb: LiveData<User> = roomDatabase.loadUser(id)
    emitSource(fromDb)
    val updated = api.fetch(id) // errors are ignored for brevity
    
    // Since we are using Room here, updating the database will update the `fromDb` LiveData
    // that was obtained above. See Room's documentation for more details.
    // https://developer.android.com/training/data-storage/room/accessing-data#query-observable
    roomDatabase.insert(updated)
}
```

* emit(), emitSource() 둘 다 기존의 값들은 없어진다.

- Flow 관련

1. map

```
map{}
mapLatest{}
mapNotNull{}
flatMapMerge{}
flatMapConCat{}
flatLatest{}

deprecated: switchMap{}, concatMap{}, flatMap{}
```
기타 등등... 너무나 많다. 그래서 LiveData -> Flow로 넘어가는 구나.

2. combine()/zip()/flattenMerge() 과 collect

combine(): 그냥 합쳐서 방출

```
fun flowWithCombine() = runBlocking {
    val intFlow = flowOf(1, 2, 3).delayEach(10)
    val charFlow = flowOf("A", "B", "C").delayEach(20)

    intFlow.combine(charFlow) { num, character ->
        "$num / $character"
    }.collect {
        println(it)
    }
}

// 결과:
2 / A
3 / A
3 / B
3 / C
```

zip(): 다른 애 기다렸다가 방출

```
fun flowWithZip() = runBlocking {
    val intFlow = flowOf(1, 2, 3).delayEach(10)
    val charFlow = flowOf("A", "B", "C").delayEach(20)

    intFlow.zip(charFlow) { num, character ->
        "$num / $character"
    }.collect {
        println(it)
    }
}

// 결과:
1 / A
2 / B
3 / C
```

flattenMerge(): 그냥 따로 방출

```
fun FlowWithflatteMerge() = runBlocking{
    val intFlow = flowOf(1, 2, 3).onEach { delay(10) }
    val charFlow = flowOf("A", "B", "C").onEach { delay(20) }

    flowOf(intFlow, charFlow).flattenMerge().collect {
        println("$it / ${System.currentTimeMillis()}")
    }
}

// 결과:
1 / 1655724966842
A / 1655724966843
2 / 1655724966843
3 / 1655724966843
B / 1655724966843
C / 1655724966844
```

3. asLiveData()
