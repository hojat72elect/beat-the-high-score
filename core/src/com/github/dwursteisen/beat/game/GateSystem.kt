package com.github.dwursteisen.beat.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family.all
import com.badlogic.gdx.assets.AssetManager
import com.github.dwursteisen.beat.addons.aseprite.Aseprite
import com.github.dwursteisen.beat.addons.ashley.EntityState
import com.github.dwursteisen.beat.addons.ashley.EventBus
import com.github.dwursteisen.beat.addons.ashley.EventData
import com.github.dwursteisen.beat.addons.ashley.StateComponent
import com.github.dwursteisen.beat.addons.ashley.StateMachineSystem
import com.github.dwursteisen.beat.addons.ashley.get
import ktx.ashley.has

class GateSystem(eventBus: EventBus, assets: AssetManager) :
    StateMachineSystem(eventBus, all(com.github.dwursteisen.beat.game.components.Gate::class.java).get()) {

    private val gate: ComponentMapper<com.github.dwursteisen.beat.game.components.Gate> = get()
    private val animation: ComponentMapper<com.github.dwursteisen.beat.game.components.Animated> = get()
    private val animatedHitBox: ComponentMapper<com.github.dwursteisen.beat.game.components.AnimatedHitBox> = get()
    private val state: ComponentMapper<StateComponent> = get()

    private val sprData: Aseprite = assets["sheets/gate"]

    override fun describeMachine() {

        val open = object : EntityState() {
            override fun enter(entity: Entity, machine: StateMachineSystem, eventData: EventData) {
                entity[state].time = 0f
                entity[animation].animation = sprData["open_nr"]
                entity[animatedHitBox].slices = sprData.animatedSlices("gate")["open_nr"]
                machine.eventBus.emitLater(entity[gate].openTime, GameEvent.UpdateGate.id, entity)
            }
        }

        val closed = object : EntityState() {
            override fun enter(entity: Entity, machine: StateMachineSystem, eventData: EventData) {
                entity[state].time = 0f
                entity[animation].animation = sprData["close_nr"]
                entity[animatedHitBox].slices = sprData.animatedSlices("gate")["close_nr"]

                machine.eventBus.emitLater(entity[gate].closeTime, GameEvent.UpdateGate.id, entity)
            }
        }

        startWith { entity, event ->
            if (!entity.has(gate)) {
                return@startWith
            }
            if (entity[gate].open) {
                go(open, entity, event)
            } else {
                go(closed, entity, event)
            }
        }

        onState(open).on(GameEvent.UpdateGate.id) { entity, event ->
            go(closed, entity, event)
        }


        onState(closed).on(GameEvent.UpdateGate.id) { entity, event ->
            go(open, entity, event)
        }

    }

}