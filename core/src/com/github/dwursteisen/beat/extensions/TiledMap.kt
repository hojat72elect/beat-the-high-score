package com.github.dwursteisen.beat.extensions

import com.badlogic.gdx.maps.MapProperties
import kotlin.reflect.KProperty

inline operator fun <reified T> MapProperties.getValue(thisRef: Any?, property: KProperty<*>): T {
    val asStr = this[property.name].toString()
    return when (T::class) {
        Double::class -> asStr.toDouble()
        Int::class -> asStr.toInt()
        Boolean::class -> "true" == asStr
        String::class -> asStr
        else -> this[property.name]
    } as T
}

open class TiledProperties(properties: MapProperties) {
    val x: Double by properties
    val y: Double by properties
}

class BrickProperties(properties: MapProperties) : TiledProperties(properties) {
    val hit: Int by properties
}
