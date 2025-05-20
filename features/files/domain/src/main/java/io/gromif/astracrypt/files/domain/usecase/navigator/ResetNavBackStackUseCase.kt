package io.gromif.astracrypt.files.domain.usecase.navigator

class ResetNavBackStackUseCase(
    private val swapNavBackStackUseCase: SwapNavBackStackUseCase
) {

    operator fun invoke() {
        swapNavBackStackUseCase(targetBackStack = emptyList())
    }
}
