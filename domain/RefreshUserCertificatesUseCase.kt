
class RefreshUserCertificatesUseCase @Inject constructor(
    private val repo: UserCertificateRepo
) : UseCase<Unit>() {

    override suspend fun execute() = repo.refreshUserCertificates()

}