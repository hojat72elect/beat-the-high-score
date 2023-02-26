package com.github.dwursteisen.beat.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.github.dwursteisen.beat.game.NO_TEXTURE

class EntityRender(
    var texture: TextureRegion = NO_TEXTURE,
    val zLevel: Int = 0,
    var enabled: Boolean = true,
    val offset: Vector2 = Vector2(),
    var hFlip: Boolean = false,
    var alpha: Float = 1f
) : Component
