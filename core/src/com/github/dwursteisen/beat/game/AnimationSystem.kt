package com.github.dwursteisen.beat.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.github.dwursteisen.beat.game.components.Animated
import com.github.dwursteisen.beat.game.components.EntityRender
import com.github.dwursteisen.beat.addons.ashley.StateComponent
import com.github.dwursteisen.beat.addons.ashley.get


class AnimationSystem : IteratingSystem(Family.all(Animated::class.java, StateComponent::class.java, EntityRender::class.java).get()) {

    private val animatedMapper: ComponentMapper<Animated> = get()
    private val stateMapper: ComponentMapper<StateComponent> = get()
    private val renderMapper: ComponentMapper<EntityRender> = get()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val state = entity[stateMapper]
        val animated = entity[animatedMapper]
        val render = entity[renderMapper]

        render.texture = animated.animation.getKeyFrame(state.time)
    }

}