package com.github.dwursteisen.beat.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.github.dwursteisen.beat.components.DebugCollision
import com.github.dwursteisen.beat.components.Debugable
import com.github.dwursteisen.libgdx.ashley.get

class DebugResetCollisionSystem :
    IteratingSystem(Family.all(DebugCollision::class.java, Debugable::class.java).get()) {
    private val collision: ComponentMapper<DebugCollision> = get()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[collision].hit -= deltaTime.coerceAtLeast(0f)
    }
}