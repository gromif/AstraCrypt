package io.gromif.astracrypt.files.files

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.work.WorkManager
import coil.ImageLoader
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ValidationRulesDto
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.domain.provider.PagingProvider
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.CreateFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.DeleteUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.MoveUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.RenameUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.SetStateUseCase
import io.gromif.astracrypt.files.domain.usecase.preferences.GetListViewModeUseCase
import io.gromif.astracrypt.utils.io.FilesUtil
import io.gromif.astracrypt.utils.io.WorkerSerializer
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FilesViewModelTest {
    private val testDispatcher = spyk(UnconfinedTestDispatcher())
    private lateinit var viewModel: FilesViewModel

    // Mock dependencies
    private val state: SavedStateHandle = SavedStateHandle()
    private val pagingProvider: PagingProvider<PagingData<Item>> = mockk(relaxed = true)
    private val createFolderUseCase: CreateFolderUseCase = mockk(relaxed = true)
    private val deleteUseCase: DeleteUseCase = mockk(relaxed = true)
    private val moveUseCase: MoveUseCase = mockk(relaxed = true)
    private val renameUseCase: RenameUseCase = mockk(relaxed = true)
    private val setStateUseCase: SetStateUseCase = mockk(relaxed = true)
    private val workManager: WorkManager = mockk(relaxed = true)
    private val workerSerializer: WorkerSerializer = mockk(relaxed = true)
    private val filesUtil: FilesUtil = mockk(relaxed = true)
    private val imageLoader: ImageLoader = mockk(relaxed = true)
    private val getListViewModeUseCase: GetListViewModeUseCase = mockk(relaxed = true)
    private val getValidationRulesUseCase: GetValidationRulesUseCase = mockk(relaxed = true)

    @Before
    fun setup() {
        every { testDispatcher.limitedParallelism(any()) } returns testDispatcher

        every { getListViewModeUseCase() } returns flowOf(ViewMode.Grid)
        every { getValidationRulesUseCase() } returns ValidationRulesDto(
            minNameLength = 1,
            maxNameLength = 32,
            maxBackstackNameLength = 32
        )
        every { pagingProvider.provide(any()) } returns flowOf<PagingData<Item>>(PagingData.empty())
        every { pagingProvider.provide(any(), any()) } returns flowOf<PagingData<Item>>(PagingData.empty())

        viewModel = FilesViewModel(
            defaultDispatcher = testDispatcher,
            state = state,
            pagingProvider = pagingProvider,
            createFolderUseCase = createFolderUseCase,
            deleteUseCase = deleteUseCase,
            moveUseCase = moveUseCase,
            renameUseCase = renameUseCase,
            setStateUseCase = setStateUseCase,
            workManager = workManager,
            workerSerializer = workerSerializer,
            filesUtil = filesUtil,
            imageLoader = imageLoader,
            getListViewModeUseCase = getListViewModeUseCase,
            getValidationRulesUsecase = getValidationRulesUseCase
        )
    }

    @Test
    fun `openDirectory updates state and triggers pagingProvider`() {
        viewModel.openDirectory(1L, "Documents")

        assert(viewModel.parentBackStack.last().id == 1L)
        verify { pagingProvider.invalidate() }
    }

    @Test
    fun `createFolder calls use case with correct parameters`() {
        viewModel.createFolder("New Folder")
        coVerify { createFolderUseCase("New Folder", parentId = 0L) }
    }

    @Test
    fun `delete calls use case with provided ids`() {
        val ids = listOf(1L, 2L, 3L)
        viewModel.delete(ids)
        coVerify { deleteUseCase(ids) }
    }

    @Test
    fun `move calls moveUseCase with correct parameters`() {
        val ids = listOf(1L, 2L)
        viewModel.move(ids)
        coVerify { moveUseCase(ids, parentId = 0L) }
    }

    @Test
    fun `rename calls renameUseCase with correct parameters`() {
        viewModel.rename(1L, "Renamed")
        coVerify { renameUseCase(1L, "Renamed") }
    }

    @Test
    fun `setStarred calls setStateUseCase with correct parameters`() {
        val targetIdList = listOf<Long>()

        viewModel.setStarred(true, targetIdList)
        coVerify(exactly = 1) { setStateUseCase(targetIdList, ItemState.Starred) }

        viewModel.setStarred(false, targetIdList)
        coVerify(exactly = 1) { setStateUseCase(targetIdList, ItemState.Default) }
    }
}