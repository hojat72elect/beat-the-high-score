package com.github.dwursteisen.beat.extensions

import com.badlogic.gdx.utils.viewport.Viewport

fun Viewport.centerCamera() {
    camera.position.set(worldWidth / 2, worldHeight / 2, 0f)
    camera.update()
}