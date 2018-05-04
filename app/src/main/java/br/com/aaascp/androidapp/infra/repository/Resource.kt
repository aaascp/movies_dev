package br.com.aaascp.androidapp.infra.repository

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

enum class ResourceState {
    LOADING,
    EMPTY,
    ERROR,
    FILLED
}

data class Resource<T>(
        val value: Flowable<T>,
        val networkState: Subject<NetworkState>
) {
    private val dbIsEmpty: Subject<Boolean> = PublishSubject.create()
    var state: Observable<ResourceState>

    init {
        value.subscribe({
            dbIsEmpty.onNext(false)
        }, {
            dbIsEmpty.onNext(true)
        })

        state = Observable.combineLatest<Boolean, NetworkState, ResourceState>(
                dbIsEmpty,
                networkState,
                BiFunction { dbIsEmpty, networkState ->
                    getResourceState(dbIsEmpty, networkState)
                })

    }

    private fun getResourceState(
            dbIsEmpty: Boolean,
            networkState: NetworkState
    ) = when (networkState.status) {
        Status.RUNNING -> if (dbIsEmpty) ResourceState.LOADING else ResourceState.FILLED
        Status.FAILED -> if (dbIsEmpty) ResourceState.ERROR else ResourceState.FILLED
        Status.SUCCESS -> if (dbIsEmpty) ResourceState.EMPTY else ResourceState.FILLED
    }

}

