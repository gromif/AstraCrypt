package io.gromif.astracrypt.profile.domain.usecase

class SetNameUsecase(
    private val setProfileUsecase: SetProfileUseCase,
    private val getProfileUsecase: GetProfileUsecase,
) {

    suspend operator fun invoke(name: String) {
        val newProfile = getProfileUsecase().copy(name = name)
        setProfileUsecase(newProfile)
    }

}