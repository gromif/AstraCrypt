package io.gromif.astracrypt.profile.domain.usecase

class SetNameUseCase(
    private val setProfileUsecase: SetProfileUseCase,
    private val getProfileUsecase: GetProfileUseCase,
) {

    suspend operator fun invoke(name: String) {
        val newProfile = getProfileUsecase().copy(name = name)
        setProfileUsecase(newProfile)
    }

}