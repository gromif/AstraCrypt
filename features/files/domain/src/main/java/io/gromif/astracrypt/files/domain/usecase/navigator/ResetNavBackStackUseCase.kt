package io.gromif.astracrypt.files.domain.usecase.navigator

class ResetNavBackStackUseCase<T>(
    private val swapNavBackStackUseCase: SwapNavBackStackUseCase<T>
) {

    operator fun invoke() {
        swapNavBackStackUseCase(targetBackStack = emptyList())
    }
}
