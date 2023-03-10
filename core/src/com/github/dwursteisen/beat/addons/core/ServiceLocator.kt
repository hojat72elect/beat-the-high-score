package com.github.dwursteisen.beat.addons.core

import kotlin.reflect.KProperty


inline fun <reified T> locate() : Locator<T> = Locator(T::class.java)

class Locator<T>(private val clazz: Class<T>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return ServiceLocator.get(clazz)
    }
}

object ServiceLocator {

    private var cache: Map<Class<out Any>, Any> = emptyMap()

    operator fun <T> get(clazz: Class<T>): T {
        val result = cache[clazz] ?: TODO("Unknow service register with the class $clazz")
        return result as T
   }

   inline fun <reified T> locate(): T {
       return this[T::class.java]
   }

   fun register(instance: Any, clazz: Class<out Any>) {
        cache += clazz to instance
   }
}