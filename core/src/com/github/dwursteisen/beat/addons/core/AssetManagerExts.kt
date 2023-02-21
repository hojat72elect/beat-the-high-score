package com.github.dwursteisen.beat.addons.core

import com.badlogic.gdx.assets.AssetManager


inline operator fun <reified T> AssetManager.get(filename: String): T {
    return this.get(filename, T::class.java)
}