@file:SuppressLint("NewApi")

package io.gromif.ui.haptic

import android.annotation.SuppressLint
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

object Haptic {
    private var vibrator: Vibrator? = null

    private val effectClick by lazy { Effect.createPredefined(Effect.EFFECT_CLICK) }
    private val effectClickHeavy by lazy { Effect.createPredefined(Effect.EFFECT_HEAVY_CLICK) }
    private val effectTick by lazy { Effect.createPredefined(Effect.EFFECT_TICK) }
    private val effectToggleOn by lazy {
        Effect.startComposition().addPrimitive(Effects.PRIMITIVE_TICK, 0.8f).compose()
    }
    private val effectToggleOff by lazy {
        Effect.startComposition().addPrimitive(Effects.PRIMITIVE_TICK, 0.2f).addPrimitive(
            Effects.PRIMITIVE_LOW_TICK, 0.5f, 80).compose()
    }
    private val effectRise by lazy {
        Effect.startComposition().addPrimitive(Effects.PRIMITIVE_LOW_TICK, 0.5f).addPrimitive(
            Effects.PRIMITIVE_QUICK_RISE, 0.01f, 100).compose()
    }

    fun click() {
        try {
            vibrate(effectClick)
        } catch (_: Exception) {}
    }
    fun clickHeavy() {
        try {
            vibrate(effectClickHeavy)
        } catch (_: Exception) {}
    }
    fun tick() {
        try {
            vibrate(effectTick)
        } catch (_: Exception) {}
    }
    fun toggle(state: Boolean) {
        try {
            vibrate(if (state) effectToggleOn else effectToggleOff)
        } catch (_: Exception) {}
    }
    fun rise() {
        try {
            vibrate(effectRise)
        } catch (_: Exception) {}
    }

    fun vibrate(effect: Effect) {
        vibrator?.vibrate(effect)
    }

    fun init(context: Context) {
        if (vibrator == null) vibrator = context.getSystemService(Vibrator::class.java)
    }

}

private typealias Effect = VibrationEffect
private typealias Effects = VibrationEffect.Composition