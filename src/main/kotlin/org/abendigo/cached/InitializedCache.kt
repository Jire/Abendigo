package org.abendigo.cached

class InitializedCached<T>(init: () -> T, lazy: T.() -> Any, private val initialized: T = init()) : Cached<T>({
	lazy(initialized)
	initialized
}) {}

fun <T> initializedCache(initializer: () -> T, updater: T.() -> Any) = InitializedCached(initializer, updater)