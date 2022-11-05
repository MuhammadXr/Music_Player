package uz.gita.music_player_xr.presentation.viewmodels.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import uz.gita.music_player_xr.data.model.MusicData
import uz.gita.music_player_xr.domain.MusicsUseCase
import uz.gita.music_player_xr.presentation.viewmodels.HomeViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val musicsUseCase: MusicsUseCase
) : HomeViewModel, ViewModel() {

    override fun getAllMusics(): Flow<List<MusicData>> = musicsUseCase.getAllMusics()

    override fun refreshAllMusics() {
        viewModelScope.launch {
            musicsUseCase.refreshMusics()
        }
    }

}