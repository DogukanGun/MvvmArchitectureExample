package com.dag.mvvmarchitectureexample.coroutine.exercise

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CoroutineExercise {

    lateinit var job: Job
    val TAG = "CoroutineTest"


    @OptIn(DelicateCoroutinesApi::class)
    suspend fun coroutineOpertion1(){
        coroutineScope {
            launch {
                delay(1000L)
                Log.d(TAG,"Hello World!")
                println(this.coroutineContext.job)
            }
        }
        GlobalScope.launch {
            delay(6000L)
            Log.d(TAG,"Hello World from Global Scope!")
            println(this.coroutineContext.job)
        }
        runBlocking(Dispatchers.IO){
            delay(5000)
            Log.d(TAG,"start of the run-blocking")
            launch(Dispatchers.IO)
            {
                delay(3000L)
                Log.d(TAG,"Finished to coroutine 1")
            }

            launch(Dispatchers.IO)
            {
                delay(3000L)
                Log.d(TAG,"Finished to coroutine 2")
            }
            Log.d(TAG,"End of the runBlocking")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun coroutineOpertion2(){
        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5)
            {
                Log.d(TAG, "Coroutines is still working")
                delay(1000)
            }
        }
        job.cancel()
        Log.d(TAG, "Coroutines is not working")
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun coroutineOpertion3(){
        GlobalScope.launch(Dispatchers.Default) {
            Log.i(TAG,"Inside Default dispatcher ".plus(Thread.currentThread().name.toString()))
        }
        GlobalScope.launch(Dispatchers.IO) {
            Log.i(TAG,"Inside IO dispatcher ".plus(Thread.currentThread().name.toString()))
        }
        GlobalScope.launch(Dispatchers.Main) {
            Log.i(TAG,"Inside Main dispatcher ".plus(Thread.currentThread().name.toString()))
        }
        GlobalScope.launch(Dispatchers.Unconfined) {
            Log.i(TAG,"Inside Unconfined dispatcher ".plus(Thread.currentThread().name.toString()))
        }
        coroutineScope {
            coroutineOpertion4().await()
        }
        Log.d(TAG, "Coroutines is not working from await")
        withContext(Dispatchers.IO){ coroutineOpertion4() }
        Log.d(TAG, "Coroutines is not working from withContext")
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun coroutineOpertion4() = GlobalScope.async {
        delay(3000)
        Log.i(TAG,"Let's wait")
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun coroutineOpertion5() {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Coroutine Exception got $exception")
        }
        GlobalScope.launch(handler) {
            throw OutOfMemoryError()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun coroutineOpertion6() {
        listOf(1,2,3,4,5,6,7).asFlow()
            .collect {
                Log.d(TAG,"Index = $it")
            }
        val flowExample = flow { // flow builder
            for (i in 1..7) {
                delay(100)
                if (i%2==0){
                    emit(i)
                }
            }
        }
        flowExample.collect {
            Log.d(TAG,"Index = $it")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun coroutineOpertion7() {
        var index = 0
        withTimeoutOrNull(2000){
            index+=1
            delay(1000)
        }
        Log.d(TAG,"Index = $index")
        listOf(1,2,3,4,5,6,7).asFlow()
            .collect {
                if (it == 3){
                    return@collect
                }
                Log.d(TAG,"Index = $it")
            }
        (1..5).asFlow()
            .map { item -> "Item = $item" }
            .collect { item -> Log.d(TAG,"Index = $item") }
        (1..10000000).asFlow()
            .take(10)
            .filter { item -> item%2 == 0 }
            .transform { item ->
                emit(item)
                emit("Index $item")
            }
            .collect { item -> Log.d(TAG,"New = $item")}
    }


    suspend fun start(){
        coroutineOpertion1()
        coroutineOpertion2()
        coroutineOpertion3()
        coroutineOpertion5()
        coroutineOpertion6()
        coroutineOpertion7()
    }
}