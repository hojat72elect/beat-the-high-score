package com.github.dwursteisen.beat.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.github.dwursteisen.libgdx.ashley.get
import com.github.dwursteisen.libgdx.ashley.getNullable
import ktx.ashley.has
import ktx.graphics.circle
import ktx.graphics.rect

class DebugShapeSystem(private val batch: ShapeRenderer) : IteratingSystem(
    Family.all(com.github.dwursteisen.beat.game.components.Debuggable::class.java, com.github.dwursteisen.beat.game.components.Position::class.java, com.github.dwursteisen.beat.game.components.ShapeToRender::class.java, Size::class.java).get()
) {

    private val position: ComponentMapper<com.github.dwursteisen.beat.game.components.Position> = get()
    private val size: ComponentMapper<Size> = get()
    private val shape: ComponentMapper<com.github.dwursteisen.beat.game.components.ShapeToRender> = get()
    private val collision: ComponentMapper<com.github.dwursteisen.beat.game.components.DebugCollision> = get()
    private val player: ComponentMapper<com.github.dwursteisen.beat.game.components.Player> = get()
    private val hitbox: ComponentMapper<com.github.dwursteisen.beat.game.components.HitBox> = get()

    private val tmp = Vector2()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val shape = entity[shape]
        batch.color = if ((entity.getNullable(collision)?.hit ?: 0f) > 0f) {
            Color.RED
        } else {
            shape.color
        }

        if (entity.has(player)) {
            tmp.set(entity[position].position).add(entity[player].offsetHitBox)

            draw(shape, tmp, entity[player].hitBox)
        }

        if (entity.has(hitbox)) {

            val (x, y) = entity[position].position
            val (offx, offy) = entity[hitbox].offset

            tmp.set(x + offx, y + offy)
            if (entity[hitbox].size.x <= 0.1f) {
                batch.color = Color.WHITE
            }
            draw(shape, tmp, entity[hitbox].size)
        }
        draw(shape, entity[position].position, entity[size].size)
    }

    private fun draw(shape: com.github.dwursteisen.beat.game.components.ShapeToRender, pos: Vector2, siz: Vector2) {
        when (shape.type) {
            is ShapeType.FilledCircle -> batch.circle(pos, siz.x)
            is ShapeType.Circle -> batch.circle(pos, siz.x)
            is ShapeType.FilledRectangle -> batch.rect(pos, siz)
            is ShapeType.Rectangle -> batch.rect(pos, siz)
        }
    }

    override fun update(deltaTime: Float) {

        val groupBy = super.getEntities().groupBy { it[shape].type.filled }
        batch.begin(ShapeRenderer.ShapeType.Filled)
        groupBy[true]?.forEach { processEntity(it, deltaTime) }
        batch.end()
        batch.begin(ShapeRenderer.ShapeType.Line)
        groupBy[false]?.forEach { processEntity(it, deltaTime) }
        batch.end()
    }
}