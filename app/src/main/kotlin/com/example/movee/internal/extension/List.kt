package com.example.movee.internal.extension

fun <T> List<T>?.thisOrEmptyList() = this ?: emptyList()