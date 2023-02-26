package com.github.dwursteisen.beat.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion


val NO_ANIMATION = Animation<TextureRegion>(0f)

class Animated(var animation: Animation<TextureRegion> = com.github.dwursteisen.beat.game.components.NO_ANIMATION) : Component