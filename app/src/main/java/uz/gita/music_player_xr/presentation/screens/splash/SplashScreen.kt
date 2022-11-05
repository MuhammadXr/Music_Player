package uz.gita.music_player_xr.presentation.screens.splash

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.music_player_xr.R
import uz.gita.music_player_xr.presentation.viewmodels.SplashViewModel
import uz.gita.music_player_xr.presentation.viewmodels.impl.SplashViewModelImpl

// Created by Jamshid Isoqov an 10/7/2022
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()
    private val navController by lazy { findNavController() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openMainFlow.onEach {
            navController.navigate(SplashScreenDirections.actionSplashScreenToSongsPage())
        }.launchIn(lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        check()
    }


    private fun check() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    viewModel.openScreens()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    requireActivity().finish()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    }
}