package com.github.dwursteisen.beat.components

import com.badlogic.ashley.core.Component
import com.github.dwursteisen.beat.game.NO_SLICE_ANIMATION
import com.github.dwursteisen.libgdx.aseprite.AnimationSlice

class AnimatedHitbox(var slices: AnimationSlice = NO_SLICE_ANIMATION) : Component
