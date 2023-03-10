package com.github.dwursteisen.beat.addons.aseprite

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import java.math.BigInteger

open class AsepriteBound(val x: Int = 0, val y: Int = 0, val w: Int = 0, val h: Int = 0)

open class AsepriteSliceData(
    val frame: Int = 0,
    val bounds: AsepriteBound = AsepriteBound()
)

open class AsepriteFrameTag(
    val name: String = "",
    val from: Int = 0,
    val to: Int = 0,
    val direction: String = ""
)

open class AsepriteSlices(
    val name: String = "",
    val data: String = "",
    val color: String = "",
    val keys: Array<AsepriteSliceData> = emptyArray<AsepriteSliceData>()
) {
    fun textureRegion(texture: Texture, offset: Pair<Int, Int> = Pair(0, 0)): TextureRegion {
        val bounds = keys.first().bounds
        return TextureRegion(
            texture,
            offset.first + bounds.x,
            offset.second + bounds.y,
            bounds.w,
            bounds.h
        )
    }

    fun toAnimationSlices(parent: Aseprite) = AnimationSlices(parent, this)
}

open class AsepriteMetaData(
    val frameTags: List<AsepriteFrameTag> = emptyList(),
    val slices: List<AsepriteSlices> = emptyList()
)

open class AsepriteFrameData(
    val x: Int = 0,
    val y: Int = 0,
    val w: Int = 0,
    val h: Int = 0
)

open class AsepriteSourceSize(val w: Int = 0, val h: Int = 0) {
    operator fun component1() = w
    operator fun component2() = h
}

open class AsepriteFrame(
    val frame: AsepriteFrameData = AsepriteFrameData(),
    val sourceSize: AsepriteSourceSize = AsepriteSourceSize(),
    val duration: Int = 100
)

open class AsepriteJson(
    val frames: HashMap<String, AsepriteFrame> = HashMap(),
    val meta: AsepriteMetaData = AsepriteMetaData()
) {
    fun slices(name: String) =
        meta.slices.filter { it.name == name }.firstOrNull() ?: invalidSlice(name)

    private fun invalidSlice(name: String): Nothing =
        TODO("Invalid slice name $name. Other candidates : ${meta.slices.map { it.name }}")

    fun asFrameIndexedMap() = this.frames.entries.map {
        if (it.key.contains(" ")) {
            val id = it.key.substringAfterLast(" ").replace("\\.ase.*".toRegex(), "").toInt()
            id to it.value
        } else {
            0 to it.value
        }
    }.toMap()
}