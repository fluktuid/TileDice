package de.fluktuid.tiledice

import android.graphics.drawable.Icon
import android.os.Handler
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import java.security.SecureRandom

class SimpleDiceTileService : TileService() {

    override fun onClick() {
        // User clicked on tile
        super.onClick()
        updateTile()
    }

    private fun updateTile() {
        val tile = qsTile

        val eyes = (1..6).random()
        val dice = Dice(eyes)

        tile.icon = Icon.createWithResource(this, dice.drawableRes)
        tile.label = getString(dice.label)
        tile.contentDescription = getString(dice.contentDescription)
        tile.state = Tile.STATE_ACTIVE
        tile.updateTile()

        Handler().postDelayed({
            tile.state = Tile.STATE_INACTIVE
            tile.updateTile()
        }, 400)
    }
}

class Dice(@IntRange(from = 1, to = 6) private val eyes: Int) {
    @DrawableRes
    val drawableRes: Int = when (eyes) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_multiple
    }
    @StringRes
    val label: Int =  when (eyes) {
        1 -> R.string.one
        2 -> R.string.two
        3 -> R.string.three
        4 -> R.string.four
        5 -> R.string.five
        6 -> R.string.six
        else -> R.string.zero
    }
    @StringRes
    val contentDescription: Int = R.string.app_name
}

private fun ClosedRange<Int>.random() = SecureRandom().nextInt((endInclusive + 1) - start) + start
