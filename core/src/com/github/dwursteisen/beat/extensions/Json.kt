package com.github.dwursteisen.beat.extensions

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Json

inline fun <reified T> Json.fromJson(json: FileHandle) = this.fromJson(T::class.java, json)!!