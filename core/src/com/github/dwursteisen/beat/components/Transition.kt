package com.github.dwursteisen.beat.components

import com.badlogic.ashley.core.Component

class Transition(
    val duration: Float = 0.5f,
    val wayIn: Boolean = true,
    var done: Boolean = false
) : Component