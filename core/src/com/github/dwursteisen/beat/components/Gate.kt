package com.github.dwursteisen.beat.components

import com.badlogic.ashley.core.Component

class Gate(var open: Boolean = true, var openTime: Float = 3f, var closeTime: Float = 1f) : Component
