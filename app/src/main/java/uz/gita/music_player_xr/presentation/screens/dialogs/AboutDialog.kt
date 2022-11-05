package uz.gita.music_player_xr.presentation.screens.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import uz.gita.music_player_xr.R

class AboutDialog(context: Context): AlertDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_dialog)
    }
}