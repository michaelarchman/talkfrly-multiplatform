package com.talkfrly.multiplatform.domain.core

sealed interface DataResult<out D, out E: Error> {
    data class ResultSuccess<out D> (val data: D): DataResult<D, Nothing>
    data class ResultError<out E: Error> (val error: E): DataResult<Nothing, E>
}

inline fun <T, E: Error, R> DataResult<T, E>.map(map: (T) -> R): DataResult<R, E> {
    return when(this) {
        is DataResult.ResultError -> DataResult.ResultError(error = error)
        is DataResult.ResultSuccess -> DataResult.ResultSuccess(data = map(data))
    }
}

fun <T, E: Error> DataResult<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: Error> DataResult<T, E>.onSuccess(action: (T) -> Unit): DataResult<T, E> {
    return when(this) {
        is DataResult.ResultError -> this
        is DataResult.ResultSuccess -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> DataResult<T, E>.onError(action: (E) -> Unit): DataResult<T, E> {
    return when(this) {
        is DataResult.ResultError -> {
            action(error)
            this
        }
        is DataResult.ResultSuccess -> this
    }
}
inline fun <T, E: Error> DataResult<T, E>.onFinally(action: () -> Unit): DataResult<T, E> {
    action()
    return this
}

typealias EmptyResult<E> = DataResult<Unit, E>