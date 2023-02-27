package com.github.dwursteisen.beat.intro.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align

class TextRender(
    var text: String = "",
    var color: Color = Color.WHITE,
    var scale: Float = 1f,
    var halign: Int = Align.left
) : Component
