package com.github.dwursteisen.beat.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body

class Brick(var hit: Int = 3, val body: Body) : Component
