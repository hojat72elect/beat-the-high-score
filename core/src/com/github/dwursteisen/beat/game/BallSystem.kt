package com.github.dwursteisen.beat.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.github.dwursteisen.beat.extensions.between
import com.github.dwursteisen.beat.extensions.set
import com.github.dwursteisen.beat.game.components.Ball
import com.github.dwursteisen.beat.game.components.Brick
import com.github.dwursteisen.beat.game.components.DeadZone
import com.github.dwursteisen.beat.game.components.DebugCollision
import com.github.dwursteisen.beat.game.components.HitBox
import com.github.dwursteisen.beat.game.components.Player
import com.github.dwursteisen.beat.game.components.Position
import com.github.dwursteisen.beat.addons.ashley.EntityState
import com.github.dwursteisen.beat.addons.ashley.EventBus
import com.github.dwursteisen.beat.addons.ashley.EventData
import com.github.dwursteisen.beat.addons.ashley.Rotation
import com.github.dwursteisen.beat.addons.ashley.StateComponent
import com.github.dwursteisen.beat.addons.ashley.StateMachineSystem
import com.github.dwursteisen.beat.addons.ashley.entity
import com.github.dwursteisen.beat.addons.ashley.get
import com.github.dwursteisen.libgdx.v2
import ktx.ashley.entity

operator fun Vector2.component1() = this.x
operator fun Vector2.component2() = this.y

class BallSystem(eventBus: EventBus, private val assetManager: AssetManager) :
    StateMachineSystem(eventBus, Family.all(Ball::class.java, Position::class.java).get()) {


    private val position: ComponentMapper<Position> = get()
    private val size: ComponentMapper<Size> = get()
    private val ball: ComponentMapper<Ball> = get()
    private val collision: ComponentMapper<DebugCollision> = get()
    private val player: ComponentMapper<Player> = get()
    private val state: ComponentMapper<StateComponent> = get()
    private val rotation: ComponentMapper<Rotation> = get()
    private val hitBox: ComponentMapper<HitBox> = get()

    private val tmp = Vector2()
    private val tmp2 = Vector2()
    private val tmpRectangle = Rectangle()

    private val brickFamily = Family.all(Brick::class.java).get()
    private val deadZoneFamily = Family.all(DeadZone::class.java).get()

    private val withSfx = Config.sfx

    override fun describeMachine() {
        val idle = object : EntityState() {

            override fun update(entity: Entity, machine: StateMachineSystem, delta: Float) {
                val rot = MathUtils.cos(entity[state].time / 0.5f)
                entity[ball].direction.set((0 v2 1).rotateRad(rot))
                entity[rotation].degree = MathUtils.radiansToDegrees * rot
            }
        }

        val moving = object : EntityState() {

            override fun enter(entity: Entity, machine: StateMachineSystem, eventData: EventData) {

            }

            override fun update(entity: Entity, machine: StateMachineSystem, delta: Float) {
                if (touchDeadZone(entity)) {
                    machine.eventBus.emit(GameEvent.Result.Lose.id)
                    return
                }

                val touched = outsideArea(entity) || touchPlayer(entity) || touchBrick(entity)

                if (touched) {
                    engine.entity {
                        this.entity.add(Position(entity[position].position.cpy()))
                            .add(com.github.dwursteisen.beat.game.components.ParticleEntity())
                    }

                }

                entity[position].position.add(entity[ball].direction)
                entity[rotation].degree += 2f

            }
        }

        startWith(idle)
        onState(idle).on(EVENT_TOUCHED, EVENT_KEY) { entity, event ->
            go(moving, entity, event)
        }
    }

    private fun touchDeadZone(entity: Entity): Boolean {
        val zones = engine.getEntitiesFor(deadZoneFamily)
        return zones.filter {
            val (x, y) = entity[position].position
            val (w, h) = entity[size].size
            tmpRectangle.set(it[position].position, it[size].size)
            return tmpRectangle.contains(x + w * 0.5f, y + h * 0.5f)
        }.isNotEmpty()
    }

    private fun touchPlayer(entity: Entity): Boolean {
        val p = engine.entity(Player::class.java)
        tmp.set(p[player].offsetHitBox).add(p[position].position)

        tmpRectangle.set(tmp, p[player].hitBox)

        tmp.set(entity[position].position).add(entity[ball].direction)

        val ballHitbox = Rectangle(tmp.x, tmp.y, entity[size].size.x, entity[size].size.y)

        return if (ballHitbox.overlaps(tmpRectangle)) {
            entity[ball].direction.y *= -1

            val middle = p[position].position.x + p[size].size.x * 0.5f
            val dst = tmp.x - middle
            entity[ball].direction.x = dst * 0.2f
            p[collision].hit = hitTime

            eventBus.emit(GameEvent.Player.Touch.id, p)
            if (withSfx) {
                val sfx: Sound = assetManager["sfx/beat_sfx_0.ogg"]
                sfx.play(MathUtils.random(0.3f, 0.8f))
            }
            true
        } else {
            false
        }
    }

    private fun touchBrick(entity: Entity): Boolean {
        tmp.set(entity[position].position).add(entity[ball].direction.x, 0f)
        tmp2.set(entity[position].position).add(0f, entity[ball].direction.y)

        val moveX = Rectangle(
            tmp.x, tmp.y, entity[size].size.x, entity[size].size.y
        )

        val moveY = Rectangle(
            tmp2.x, tmp2.y, entity[size].size.x, entity[size].size.y
        )

        val bricks = engine.getEntitiesFor(brickFamily)
        val brickOnX = bricks.firstOrNull { overlaps(moveX, it) }

        var hitX = false
        var hitY = false
        if (brickOnX != null) {
            val data = eventBus.createEventData()
            data.body = entity[ball].direction.cpy()
            eventBus.emit(GameEvent.Brick.Touched.id, brickOnX, data)
            entity[ball].direction.x *= -1

            hitX = true
        }
        val brickOnY = bricks.firstOrNull { overlaps(moveY, it) }
        if (brickOnY != null) {
            val data = eventBus.createEventData()
            data.body = entity[ball].direction.cpy()
            eventBus.emit(GameEvent.Brick.Touched.id, brickOnY, data)
            entity[ball].direction.y *= -1

            hitY = true
        }

        val hit = hitX || hitY
        if (hit) {
            if (withSfx) {
                val sfx: Sound = assetManager["sfx/beat_sfx_1.ogg"]
                sfx.play(MathUtils.random(0.3f, 0.8f))
            }
            eventBus.emit(GameEvent.Camera.Shake.id)
        }
        return hit
    }

    private fun outsideArea(entity: Entity): Boolean {
        tmp.set(entity[ball].direction).add(entity[position].position)

        var touched = false
        // outside the arena ?
        if (!tmp.x.between(0f, screenWidth - entity[size].size.x)) {
            tmp.x = MathUtils.clamp(tmp.x, 0f, screenWidth - entity[size].size.x)
            entity[ball].direction.x *= -1
            touched = true
        }


        if (!tmp.y.between(0f, screenHeight)) {
            tmp.y = MathUtils.clamp(tmp.y, 0f, screenHeight)
            entity[ball].direction.y *= -1
            touched = true
        }

        if (touched) {
            entity[position].position.set(tmp)
            if (withSfx) {
                val sfx: Sound = assetManager["sfx/beat_sfx_3.ogg"]
                sfx.play(MathUtils.random(0.3f, 0.8f))
            }
        }
        return touched
    }

    private fun overlaps(ballHitbox: Rectangle, target: Entity): Boolean {
        val (x, y) = target[position].position
        val (offsetX, offsetY) = target[hitBox].offset
        val (sizeX, sizeY) = target[hitBox].size

        // ignore small hitbox
        if (sizeX + sizeY <= 0f) return false

        tmpRectangle.set(x + offsetX, y + offsetY, sizeX, sizeY)
        return ballHitbox.overlaps(tmpRectangle)
    }


}