package de.fluktuid.tiledice

import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.annotation.StringRes


class Coin(@IntRange(from = 1, to = 2) private val side: Int) : Dice {
    @DrawableRes
    override val drawableRes: Int = when (side) {
        1 -> R.drawable.coin_head
        2 -> R.drawable.coin_number
        else -> R.drawable.coin_blank
    }
    @StringRes
    override val label: Int = when (side) {
        1 -> R.string.head
        2 -> R.string.number
        else -> R.string.zero
    }
    @StringRes
    override val contentDescription: Int = R.string.app_name

    override fun cloneRandom(): Dice = Coin((1..2).random())
}

class SixSideDice(@IntRange(from = 1, to = 6) private val eyes: Int) : Dice {
    @DrawableRes
    override val drawableRes: Int = when (eyes) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_multiple
    }
    @StringRes
    override val label: Int = when (eyes) {
        1 -> R.string.one
        2 -> R.string.two
        3 -> R.string.three
        4 -> R.string.four
        5 -> R.string.five
        6 -> R.string.six
        else -> R.string.zero
    }
    @StringRes
    override val contentDescription: Int = R.string.app_name

    override fun cloneRandom(): Dice = SixSideDice((1..6).random())
}

class EightSideDice(@IntRange(from = 1, to = 8) private val eyes: Int) : Dice {
    @DrawableRes
    override val drawableRes: Int = when (eyes) {
        1 -> R.drawable.dice_d8_1
        2 -> R.drawable.dice_d8_2
        3 -> R.drawable.dice_d8_3
        4 -> R.drawable.dice_d8_4
        5 -> R.drawable.dice_d8_5
        6 -> R.drawable.dice_d8_6
        7 -> R.drawable.dice_d8_7
        8 -> R.drawable.dice_d8
        else -> R.drawable.dice_d8_blank
    }
    @StringRes
    override val label: Int = when (eyes) {
        1 -> R.string.one
        2 -> R.string.two
        3 -> R.string.three
        4 -> R.string.four
        5 -> R.string.five
        6 -> R.string.six
        7 -> R.string.seven
        8 -> R.string.eight
        else -> R.string.zero
    }
    @StringRes
    override val contentDescription: Int = R.string.app_name

    override fun cloneRandom(): Dice = EightSideDice((1..8).random())
}

class TwelveSideDice(@IntRange(from = 1, to = 12) private val eyes: Int) : Dice {
    @DrawableRes
    override val drawableRes: Int = when (eyes) {
        1 -> R.drawable.dice_d12_1
        2 -> R.drawable.dice_d12_2
        3 -> R.drawable.dice_d12_3
        4 -> R.drawable.dice_d12_4
        5 -> R.drawable.dice_d12_5
        6 -> R.drawable.dice_d12_6
        7 -> R.drawable.dice_d12_7
        8 -> R.drawable.dice_d12_8
        9 -> R.drawable.dice_d12_9
        10 -> R.drawable.dice_d12_10
        11 -> R.drawable.dice_d12_11
        12 -> R.drawable.dice_d12_12
        else -> R.drawable.dice_d12_blank
    }
    @StringRes
    override val label: Int = when (eyes) {
        1 -> R.string.one
        2 -> R.string.two
        3 -> R.string.three
        4 -> R.string.four
        5 -> R.string.five
        6 -> R.string.six
        7 -> R.string.seven
        8 -> R.string.eight
        9 -> R.string.nine
        10 -> R.string.ten
        11 -> R.string.eleven
        12 -> R.string.twelve
        else -> R.string.zero
    }
    @StringRes
    override val contentDescription: Int = R.string.app_name

    override fun cloneRandom(): Dice = TwelveSideDice((1..12).random())
}

class TwentySideDice(@IntRange(from = 1, to = 20) private val eyes: Int) : Dice {
    @DrawableRes
    override val drawableRes: Int = when (eyes) {
        1 -> R.drawable.dice_d20_1
        2 -> R.drawable.dice_d20_2
        3 -> R.drawable.dice_d20_3
        4 -> R.drawable.dice_d20_4
        5 -> R.drawable.dice_d20_5
        6 -> R.drawable.dice_d20_6
        7 -> R.drawable.dice_d20_7
        8 -> R.drawable.dice_d20_8
        9 -> R.drawable.dice_d20_9
        10 -> R.drawable.dice_d20_10
        11 -> R.drawable.dice_d20_11
        12 -> R.drawable.dice_d20_12
        13 -> R.drawable.dice_d20_13
        14 -> R.drawable.dice_d20_14
        15 -> R.drawable.dice_d20_15
        16 -> R.drawable.dice_d20_16
        17 -> R.drawable.dice_d20_17
        18 -> R.drawable.dice_d20_18
        19 -> R.drawable.dice_d20_19
        20 -> R.drawable.dice_d20_20
        else -> R.drawable.dice_multiple
    }
    @StringRes
    override val label: Int = when (eyes) {
        1 -> R.string.one
        2 -> R.string.two
        3 -> R.string.three
        4 -> R.string.four
        5 -> R.string.five
        6 -> R.string.six
        7 -> R.string.seven
        8 -> R.string.eight
        9 -> R.string.nine
        10 -> R.string.ten
        11 -> R.string.eleven
        12 -> R.string.twelve
        13 -> R.string.thirteen
        14 -> R.string.fourteen
        15 -> R.string.fifteen
        16 -> R.string.sixteen
        17 -> R.string.seventeen
        18 -> R.string.eighteen
        19 -> R.string.nineteen
        20 -> R.string.twenty
        else -> R.string.zero
    }
    @StringRes
    override val contentDescription: Int = R.string.app_name

    override fun cloneRandom(): Dice = TwentySideDice((1..20).random())
}