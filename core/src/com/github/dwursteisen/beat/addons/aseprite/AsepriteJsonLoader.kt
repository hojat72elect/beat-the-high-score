package com.github.dwursteisen.beat.addons.aseprite

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Json
import com.github.dwursteisen.beat.extensions.fromJson

class AsepriteJsonParameter : AssetLoaderParameters<AsepriteJson>()

class AsepriteJsonLoader(resolver: FileHandleResolver) :
    AsynchronousAssetLoader<AsepriteJson, AsepriteJsonParameter>(resolver) {

    private val json = Json().apply {
        ignoreUnknownFields = true
    }

    private var data: AsepriteJson? = null

    override fun loadSync(
        manager: AssetManager,
        fileName: String,
        file: FileHandle,
        parameter: AsepriteJsonParameter?
    ): AsepriteJson? {
        val copy = data
        data = null
        return copy
    }

    override fun getDependencies(
        fileName: String?,
        file: FileHandle?,
        parameter: AsepriteJsonParameter?
    ): Array<AssetDescriptor<Any>>? = null

    override fun loadAsync(
        manager: AssetManager,
        fileName: String,
        file: FileHandle,
        parameter: AsepriteJsonParameter?
    ) {
        data = json.fromJson(file)
    }

}