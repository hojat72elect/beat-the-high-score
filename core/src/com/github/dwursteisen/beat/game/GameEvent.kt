package com.github.dwursteisen.beat.game

/**
 * All the important events that are happening in our game's world.
 */
sealed class GameEvent(val id: Int) {

    // 3 brick events
    sealed class Brick(id: Int) : GameEvent(id) {
        object Touched : Brick(1)
        object Idle : Brick(2)
        object Exploded : Brick(3)
    }

    // 2 camera events
    sealed class Camera(id: Int) : GameEvent(id) {
        object Idle : Camera(4)
        object Shake : Camera(5)
    }
}
