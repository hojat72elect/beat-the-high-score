package com.github.dwursteisen.beat.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2

class Move(
    val duration: Float = 0f,
    var delay: Float,
    val from: Vector2 = Vector2(),
    val target: Vector2 = Vector2(),
    val interpolation: Interpolation = Interpolation.linear
) : Component
