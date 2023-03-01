package com.github.dwursteisen.beat.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family.all
import com.badlogic.gdx.assets.AssetManager
import com.github.dwursteisen.beat.extensions.pickOne
import com.github.dwursteisen.beat.game.components.Animated
import com.github.dwursteisen.beat.game.components.Player
import com.github.dwursteisen.beat.addons.aseprite.Aseprite
import com.github.dwursteisen.libgdx.ashley.EntityState
import com.github.dwursteisen.libgdx.ashley.EventBus
import com.github.dwursteisen.libgdx.ashley.EventData
import com.github.dwursteisen.libgdx.ashley.StateComponent
import com.github.dwursteisen.libgdx.ashley.StateMachineSystem
import com.github.dwursteisen.libgdx.ashley.get

class PlayerSystem(eventBus: EventBus, val assets: AssetManager) :
    StateMachineSystem(eventBus, all(Player::class.java).get()) {
    private val animation: ComponentMapper<Animated> = get()
    private val state: ComponentMapper<StateComponent> = get()

    private val idles = listOf("idle", "idle3")

    override fun describeMachine() {
        val idle = object : EntityState() {
            override fun enter(entity: Entity, machine: StateMachineSystem, eventData: EventData) {
                val chicken: Aseprite = assets["sheets/chicken"]
                val anim = if (eventData.event == GameEvent.Player.Touch.id) {
                    chicken["bounce"]
                } else {
                    // chose a random animation
                    chicken[idles.pickOne()]
                }

                entity[animation].animation = anim
                entity[state].time = 0f
            }

            override fun update(entity: Entity, machine: StateMachineSystem, delta: Float) {
                if (entity[animation].animation.isAnimationFinished(entity[state].time)) {
                    machine.eventBus.emit(GameEvent.Player.Idle.id, entity)
                }
            }
        }

        val move = object : EntityState() {
            override fun enter(entity: Entity, machine: StateMachineSystem, eventData: EventData) {
                // chose an random animation
                val chicken: Aseprite = assets["sheets/chicken"]

                val anim = if (eventData.event == GameEvent.Player.Touch.id) {
                    chicken["bounce"]
                } else {
                    chicken["idle2"]
                }

                entity[animation].animation = anim
                entity[state].time = 0f
            }

            override fun update(entity: Entity, machine: StateMachineSystem, delta: Float) {
                if (entity[animation].animation.isAnimationFinished(entity[state].time)) {
                    machine.eventBus.emit(GameEvent.Player.Idle.id, entity)
                }
            }
        }

        startWith(idle)
        onState(idle).on(GameEvent.Player.Idle.id, GameEvent.Player.Touch.id) { entity, event ->
            go(idle, entity, event)
        }

        onState(idle).on(EVENT_KEY) { entity, event ->
            go(move, entity, event)
        }

        onState(move).on(EVENT_KEY_UP) { entity, event ->
            go(idle, entity, event)
        }

        onState(idle).on(EVENT_TOUCHED) { entity, event ->
            go(move, entity, event)
        }

        onState(move).on(EVENT_SLIDE) { entity, event ->
            go(idle, entity, event)
        }

        onState(move).on(GameEvent.Player.Touch.id) { entity, event ->
            go(move, entity, event)
        }
    }
}
