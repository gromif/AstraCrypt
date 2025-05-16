package io.gromif.astracrypt.files.files

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.work.WorkManager
import coil.ImageLoader
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ValidationRulesDto
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.files.domain.usecase.preferences.GetListViewModeUseCase
import io.gromif.astracrypt.files.files.util.ActionUseCases
import io.gromif.astracrypt.files.files.util.DataUseCases
import io.gromif.astracrypt.utils.io.FilesUtil
import io.gromif.astracrypt.utils.io.WorkerSerializer
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FilesViewModelTest {
    private val testDispatcher = spyk(UnconfinedTestDispatcher())
    private lateinit var vm: FilesViewModel

    private val state: SavedStateHandle = SavedStateHandle()
    private val dataUseCases: DataUseCases<PagingData<Item>> = mockk(relaxed = true)
    private val actionUseCasesMock: ActionUseCases = mockk(relaxed = true)
    private val workManager: WorkManager = mockk()
    private val workerSerializer: WorkerSerializer = mockk()
    private val filesUtil: FilesUtil = mockk()
    private val imageLoader: ImageLoader = mockk()
    private val getListViewModeUseCase: GetListViewModeUseCase = mockk()
    private val getValidationRulesUseCase: GetValidationRulesUseCase = mockk()

    @Before
    fun setup() {
        every { testDispatcher.limitedParallelism(any()) } returns testDispatcher

        every { getListViewModeUseCase() } returns flowOf(ViewMode.Grid)
        every { getValidationRulesUseCase() } returns ValidationRulesDto(
            minNameLength = 1,
            maxNameLength = 32,
            maxBackstackNameLength = 32
        )
        every { dataUseCases.getFilesDataFlow(any()) } returns flowOf(PagingData.empty<Item>())
        every { dataUseCases.getStarredDataFlow(any()) } returns flowOf(PagingData.empty<Item>())

        vm = FilesViewModel(
            defaultDispatcher = testDispatcher,
            state = state,
            dataUseCases = dataUseCases,
            actionUseCases = actionUseCasesMock,
            workManager = workManager,
            workerSerializer = workerSerializer,
            filesUtil = filesUtil,
            imageLoader = imageLoader,
            getListViewModeUseCase = getListViewModeUseCase,
            getValidationRulesUsecase = getValidationRulesUseCase
        )

        verifyAll {
            dataUseCases.getFilesDataFlow(any())
            dataUseCases.getStarredDataFlow(any())
        }
    }

    @Test
    fun `openDirectory updates state and triggers pagingProvider`() {
        vm.openDirectory(1L, "Documents")

        assert(vm.parentBackStack.last().id == 1L)
        verify { dataUseCases.invalidateDataSourceUseCase() }
    }

    @Test
    fun `createFolder calls use case with correct parameters`() {
        val targetName = "New Folder"
        val targetRootId = 0L

        vm.createFolder(targetName)

        coVerify(exactly = 1) { actionUseCasesMock.createFolderUseCase(targetName, targetRootId) }
    }

    @Test
    fun `delete calls use case with provided ids`() {
        val ids = listOf(1L, 2L, 3L)

        vm.delete(ids)

        coVerify(exactly = 1) { actionUseCasesMock.deleteUseCase(ids) }
    }

    @Test
    fun `move calls moveUseCase with correct parameters`() {
        val ids = listOf(1L, 2L)

        vm.move(ids)

        coVerify(exactly = 1) { actionUseCasesMock.moveUseCase(ids, parentId = 0L) }
    }

    @Test
    fun `rename calls renameUseCase with correct parameters`() {
        val targetName = "New Name"

        vm.rename(1L, targetName)

        coVerify(exactly = 1) { actionUseCasesMock.renameUseCase(1L, targetName) }
    }

    @Test
    fun `setStarred calls setStateUseCase with correct parameters`() {
        val targetIdList = listOf<Long>()

        vm.setStarred(true, targetIdList)
        vm.setStarred(false, targetIdList)
        coVerifySequence {
            actionUseCasesMock.setStateUseCase(targetIdList, ItemState.Starred)
            actionUseCasesMock.setStateUseCase(targetIdList, ItemState.Default)
        }
    }

    @After
    fun tearDown() {
        confirmVerified(actionUseCasesMock, dataUseCases)
    }
}