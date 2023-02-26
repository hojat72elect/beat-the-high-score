package com.github.dwursteisen.beat.components

import com.badlogic.ashley.core.Component
import com.github.dwursteisen.libgdx.aseprite.AnimationSlice


private val NO_SLICE_ANIMATION = AnimationSlice(0f)

class AnimatedHitbox(var slices: AnimationSlice = NO_SLICE_ANIMATION) : Component
