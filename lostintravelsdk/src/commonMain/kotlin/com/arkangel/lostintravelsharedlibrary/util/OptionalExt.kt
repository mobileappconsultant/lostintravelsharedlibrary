package com.arkangel.lostintravelsharedlibrary.util

import com.apollographql.apollo3.api.Optional

fun<V> makeOptional(value: V?): Optional<V> {
    if (value == null) {
        return Optional.Absent
    }

    return Optional.presentIfNotNull(value)
}

fun<V> getOptionalValue(optional: Optional<V>): V? {
    return optional.getOrNull()
}