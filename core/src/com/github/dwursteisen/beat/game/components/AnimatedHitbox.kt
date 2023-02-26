package com.github.dwursteisen.beat.game.components

import com.badlogic.ashley.core.Component
import com.github.dwursteisen.libgdx.aseprite.AnimationSlice


private val NO_SLICE_ANIMATION = AnimationSlice(0f)

class AnimatedHitbox(var slices: AnimationSlice = com.github.dwursteisen.beat.game.components.NO_SLICE_ANIMATION) : Component
