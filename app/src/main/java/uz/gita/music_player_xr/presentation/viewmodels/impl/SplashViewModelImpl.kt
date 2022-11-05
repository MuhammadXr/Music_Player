package uz.gita.music_player_xr.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uz.gita.music_player_xr.domain.MusicsUseCase
import uz.gita.music_player_xr.presentation.viewmodels.SplashViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val musicsUseCase: MusicsUseCase
) : SplashViewModel, ViewModel() {

    override val openIntroFlow = MutableSharedFlow<Unit>()

    override val openMainFlow = MutableSharedFlow<Unit>()

    override fun openScreens() {
        viewModelScope.launch {
            musicsUseCase.refreshMusics()
            delay(1000)
            openMainFlow.emit(Unit)
        }
    }


}