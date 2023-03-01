package com.github.dwursteisen.beat.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.github.dwursteisen.beat.addons.ashley.get

abstract class MapRenderSystem(val layers: IntArray) : IteratingSystem(Family.all(com.github.dwursteisen.beat.game.components.MapLayer::class.java).get()) {
    private val map: ComponentMapper<com.github.dwursteisen.beat.game.components.MapLayer> = get()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[map].map.render(layers)
    }
}

class FirstLayerMapRendersystem(layers: IntArray) : MapRenderSystem(layers)
class SecondLayerMapRendersystem(layers: IntArray) : MapRenderSystem(layers)

