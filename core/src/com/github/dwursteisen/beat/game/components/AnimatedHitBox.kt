package com.github.dwursteisen.beat.game.components

import com.badlogic.ashley.core.Component
import com.github.dwursteisen.beat.addons.aseprite.AnimationSlice

private val NO_SLICE_ANIMATION = AnimationSlice(0f)

class AnimatedHitBox(var slices: AnimationSlice = NO_SLICE_ANIMATION) : Component
