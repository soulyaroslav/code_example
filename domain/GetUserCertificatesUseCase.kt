
class GetUserCertificatesUseCase @Inject constructor(
    private val repo: UserCertificateRepo
) : FlowUseCase<Status<List<UserCertificate>>>() {

    override fun execute(): Flow<Status<List<UserCertificate>>> = repo.getUserCertificates()
}