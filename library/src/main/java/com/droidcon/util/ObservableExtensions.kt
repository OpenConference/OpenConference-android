package com.droidcon.util

import android.database.Cursor
import com.squareup.sqlbrite.QueryObservable
import rx.Observable
import rx.functions.Func1


fun <T : MutableList<L>, R : List<L>, L> Observable<T>.asImmutableList(): Observable<R> = map { it as R }

fun <T, R> QueryObservable.mapToImmutableList(
    mapper: Func1<Cursor, T>): Observable<List<R>> = mapToList(mapper).map { it as List<R> }