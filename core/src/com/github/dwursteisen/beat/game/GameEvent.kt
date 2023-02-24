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

    // 2 player events
    sealed class Player(id: Int) : GameEvent(id) {
        object Idle : Player(6)
        object Touch : Player(7)
    }

    // 2 game result events
    sealed class Result(id: Int) : GameEvent(id) {
        object Win : Result(8)
        object Lose : Result(9)
    }
}

