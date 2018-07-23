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

        val diceType = PreferenceManager.getDefaultSharedPreferences(this).getString("diceType", "6")

        val max = getMax(diceType)
        val eyes = (1..max).random()

        val dice: Dice = getDice(diceType, eyes)

        tile.label = getString(dice.label)
        tile.contentDescription = getString(dice.contentDescription)
        tile.state = Tile.STATE_ACTIVE

        val animate = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("diceAnimate", true)

        if (animate) {
            val bmp = tile.icon.loadDrawable(this)
            updateTileByAnimation(bmp, dice.drawableRes)
        } else qsTile.updateTo(dice.drawableRes, this)

        Handler().postDelayed({
            tile.state = Tile.STATE_INACTIVE
            tile.updateTile()
        }, 400)
    }

    private fun Tile.updateTo(@DrawableRes res: Int, ctx: Context) {
        this.icon = Icon.createWithResource(ctx, res)
        this.updateTile()
    }

    private fun updateTileByAnimation(oldDrawable: Drawable, @DrawableRes newRes: Int) {
        val rotationIcon = ValueAnimator.ofInt(0, 360)
        rotationIcon.duration = 150L

        rotationIcon.addUpdateListener { animation ->
            val angle = (animation.animatedValue as Int).toFloat()
            val oldBitmap = oldDrawable.toBitmap()

            val matrix = Matrix()
            matrix.postRotate(angle)

            val iconBitmap = Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.width, oldBitmap.height, matrix, true)

            //get tile and change icon
            qsTile.icon = Icon.createWithBitmap(iconBitmap)
            qsTile.updateTile()
        }

        rotationIcon.addListener(onEnd = {
            qsTile.icon = Icon.createWithResource(this, newRes)
            qsTile.updateTile()
        })

        rotationIcon.start()
    }

    private fun Drawable.toBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(this.intrinsicWidth,
                this.intrinsicHeight, Bitmap.Config.ARGB_8888)
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
}

private fun ClosedRange<Int>.random() = SecureRandom().nextInt((endInclusive + 1) - start) + start
