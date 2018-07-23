package de.fluktuid.tiledice

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Handler
import android.preference.PreferenceManager
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.DrawableRes
import androidx.core.animation.addListener
import java.security.SecureRandom


class SimpleDiceTileService : TileService() {

    override fun onClick() {
        // User clicked on tile
        super.onClick()
        updateTile()
    }

    private fun updateTile() {
        val tile = qsTile

        val dsp = PreferenceManager.getDefaultSharedPreferences(this)

        val diceType = dsp.getString("diceType", "6")

        val max =  getMax(diceType)
        val eyes = (1..max).random()

        val dice: Dice = getDice(diceType, eyes)

        tile.label = getString(dice.label)
        tile.contentDescription = getString(dice.contentDescription)
        tile.state = Tile.STATE_ACTIVE

        val animate = dsp.getBoolean("diceAnimate", true)

        if (animate) {
            val animType = dsp.getString("diceAnimateType", "rotate")
            when (animType) {
                "flip" -> updateTileByFlipAnimation(dice)
                "rotate" -> {
                    val bmp = tile.icon.loadDrawable(this)
                    updateTileByRotateAnimation(bmp, dice.drawableRes)
                }
            }
        } else qsTile.updateTo(dice.drawableRes, this)

        Handler().postDelayed({
            tile.state = Tile.STATE_INACTIVE
            tile.updateTile()
        }, 400)
    }

    private fun Tile.updateTo(@DrawableRes res: Int, ctx: Context) {
        icon = Icon.createWithResource(ctx, res)
        updateTile()
    }

    private fun Tile.updateTo(bmp: Bitmap) {
        icon = Icon.createWithBitmap(bmp)
        updateTile()
    }


    private fun updateTileByFlipAnimation(dice: Dice) {
        val anim = ValueAnimator.ofInt(0, 2)
        anim.duration = 150L

        anim.addUpdateListener {
            val d = dice.cloneRandom()

            //get tile and change icon
            qsTile.updateTo(d.drawableRes, this)
        }

        anim.addListener(onEnd = {
            qsTile.updateTo(dice.drawableRes, this)
        })

        anim.start()
    }

    private fun updateTileByRotateAnimation(oldDrawable: Drawable, @DrawableRes newRes: Int) {
        val rotationIcon = ValueAnimator.ofInt(0, 360)
        rotationIcon.duration = 150L

        rotationIcon.addUpdateListener { animation ->
            val angle = (animation.animatedValue as Int).toFloat()
            val oldBitmap = oldDrawable.toBitmap()

            val matrix = Matrix()
            matrix.postRotate(angle)
            val iconBitmap = Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.width, oldBitmap.height, matrix, true)

            //get tile and change icon
            qsTile.updateTo(iconBitmap)
        }

        rotationIcon.addListener(onEnd = {
            qsTile.updateTo(newRes, this)
        })

        rotationIcon.start()
    }

    private fun Drawable.toBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(intrinsicWidth,
                intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        this.setBounds(0, 0, canvas.width, canvas.height)
        this.draw(canvas)

        return bitmap
    }

    private fun getMax(string: String): Int = when (string) {
        "2" -> 2
        "8" -> 8
        "12" -> 12
        "20" -> 20
        else -> 6
    }

    private fun getDice(type: String, value: Int): Dice = when (type) {
        "2" -> Coin(value)
        "8" -> EightSideDice(value)
        "12" -> TwelveSideDice(value)
        "20" -> TwentySideDice(value)
        else -> SixSideDice(value)
    }
}

interface Dice {
    // @DrawableRes
    val drawableRes: Int
    // @StringRes
    val label: Int
    // @StringRes
    val contentDescription: Int

    fun cloneRandom(): Dice
}

fun ClosedRange<Int>.random() = SecureRandom().nextInt((endInclusive + 1) - start) + start
