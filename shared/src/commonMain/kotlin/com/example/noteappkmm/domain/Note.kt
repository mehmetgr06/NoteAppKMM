package com.example.noteappkmm.domain

import com.example.noteappkmm.ui.BabyBlueHex
import com.example.noteappkmm.ui.LightGreenHex
import com.example.noteappkmm.ui.RedOrangeHex
import com.example.noteappkmm.ui.RedPinkHex
import com.example.noteappkmm.ui.VioletHex
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun generateColors() = colors.random()
    }
}
