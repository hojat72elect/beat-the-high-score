package com.github.dwursteisen.beat.addons.ashley.fsm

import com.badlogic.ashley.core.Entity
import com.github.dwursteisen.beat.addons.ashley.EventData

interface StateMachine {
    /**
     * Change the state of the current entity into another state.
     */
    fun go(newState: EntityState, entity: Entity)

    /**
     * Change the state of the entity into another state, using some custom attached data.
     */
    fun go(newState: EntityState, entity: Entity, event: EventData)

    /**
     * Emit a new event with some custom attached data.
     */
    fun emit(event: Event, eventData: EventData)

    /**
     * Emit a new event.
     */
    fun emit(event: Event)
}
