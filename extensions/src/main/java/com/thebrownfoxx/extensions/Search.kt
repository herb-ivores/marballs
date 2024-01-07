package com.thebrownfoxx.extensions

fun <T> Collection<T>.search(
    query: String,
    ignoreCase: Boolean = true,
    key: (item: T) -> String,
) =
    filter { item ->
        key(item).contains(query, ignoreCase = ignoreCase)
    }