package com.dag.mvvmarchitectureexample.adapter

@AdapterDsl
class Builder<T> {

    var itemLayoutId: Int = 0
    var itemClickListener: ItemClickListener<T>? = null
    var itemSelectionEnabled: Boolean = false
    var list: List<T> = emptyList()
}

fun <T> basicAdapter(builder: Builder<T>.() -> Unit): BasicAdapter<T> {
    val b = Builder<T>()
    b.builder()
    return BasicAdapter.createWith(b)
}

@DslMarker
annotation class AdapterDsl
