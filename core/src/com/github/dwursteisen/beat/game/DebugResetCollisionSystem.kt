package com.github.dwursteisen.beat.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.github.dwursteisen.beat.game.components.DebugCollision
import com.github.dwursteisen.beat.game.components.Debugable
import com.github.dwursteisen.libgdx.ashley.get

class DebugResetCollisionSystem :
    IteratingSystem(Family.all(com.github.dwursteisen.beat.game.components.DebugCollision::class.java, com.github.dwursteisen.beat.game.components.Debugable::class.java).get()) {
    private val collision: ComponentMapper<com.github.dwursteisen.beat.game.components.DebugCollision> = get()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[collision].hit -= deltaTime.coerceAtLeast(0f)
    }
}