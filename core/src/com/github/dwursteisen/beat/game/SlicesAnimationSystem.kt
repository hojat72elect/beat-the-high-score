package com.github.dwursteisen.beat.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family.all
import com.badlogic.ashley.systems.IteratingSystem
import com.github.dwursteisen.beat.game.components.AnimatedHitBox
import com.github.dwursteisen.beat.game.components.HitBox
import com.github.dwursteisen.beat.addons.ashley.StateComponent
import com.github.dwursteisen.beat.addons.ashley.get

class SlicesAnimationSystem : IteratingSystem(
    all(
        AnimatedHitBox::class.java,
        HitBox::class.java
    ).get()
) {

    private val hitbox: ComponentMapper<HitBox> = get()
    private val animated: ComponentMapper<AnimatedHitBox> =
        get()
    private val state: ComponentMapper<StateComponent> = get()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val slice = entity[animated].slices.slice(entity[state].time)
        entity[hitbox].size.set(slice.w.toFloat(), slice.h.toFloat())
        entity[hitbox].offset.set(slice.x.toFloat(), slice.y.toFloat())
    }

}