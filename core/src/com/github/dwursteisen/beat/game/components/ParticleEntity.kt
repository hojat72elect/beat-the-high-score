package com.github.dwursteisen.beat.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool

class ParticleEntity(var particle: ParticleEffectPool.PooledEffect? = null) : Component
