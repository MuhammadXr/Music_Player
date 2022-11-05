package uz.gita.music_player_xr.domain.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.music_player_xr.data.model.MusicData
import uz.gita.music_player_xr.data.room.dao.MusicDao
import uz.gita.music_player_xr.domain.GetAllMusicsUseCase
import uz.gita.music_player_xr.domain.MusicsUseCase
import javax.inject.Inject

class MusicsUseCaseImpl @Inject constructor(
    private val dao: MusicDao,
    private val getAllMusicsUseCase: GetAllMusicsUseCase
) : MusicsUseCase {


    override suspend fun deleteMusic(musicData: MusicData) =
        dao.deleteMusic(musicData)

    override suspend fun refreshMusics() {
        dao.clearAndUpdateData(getAllMusicsUseCase.getAllMusics())
    }

    override fun getAllMusics(): Flow<List<MusicData>> = dao.getAllMusics()

}