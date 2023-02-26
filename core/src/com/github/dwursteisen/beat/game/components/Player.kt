package com.github.dwursteisen.beat.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class Player(
    val hitBox: Vector2 = Vector2(),
    val offsetHitBox: Vector2 = Vector2(),
    val direction: Vector2 = Vector2(),
    var win: Boolean = false
) : Component
