package com.github.dwursteisen.beat.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family.all
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.github.dwursteisen.beat.addons.ashley.StateComponent
import com.github.dwursteisen.beat.addons.ashley.get

class CloudSystem : IteratingSystem(all(com.github.dwursteisen.beat.game.components.Cloud::class.java, com.github.dwursteisen.beat.game.components.Position::class.java, StateComponent::class.java).get()) {

    private val position: ComponentMapper<com.github.dwursteisen.beat.game.components.Position> = get()
    private val state: ComponentMapper<StateComponent> = get()
    private val cloud: ComponentMapper<com.github.dwursteisen.beat.game.components.Cloud> = get()

    override fun processEntity(entity: Entity, deltaTime: Float) {

        val pos = entity[position].position.set(entity[cloud].origin)
        val time = entity[state].time + entity[cloud].offset
        pos.x += MathUtils.sin(time / 3f) * 8f
    }

}