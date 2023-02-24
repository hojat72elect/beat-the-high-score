package com.github.dwursteisen.beat.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color
import com.github.dwursteisen.beat.game.ShapeType

class ShapeToRender(var type: ShapeType, var color: Color) : Component
