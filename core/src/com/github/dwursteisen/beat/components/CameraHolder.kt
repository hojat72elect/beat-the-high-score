package com.github.dwursteisen.beat.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2

class CameraHolder(val camera: Camera, val amplitude: Vector2 = Vector2()) : Component
