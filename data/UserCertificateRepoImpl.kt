
@ExperimentalCoroutinesApi
@FlowPreview
class UserCertificateRepoImpl(
    private val api: NetworkSource,
    private val database: ShellDatabase,
    io: CoroutineDispatcher,
    channelRecreateObserver: ChannelRecreateObserver,
    connectivityProvider: ConnectivityProvider,
    networkErrorHandler: NetworkErrorHandler
) : BaseRepo(networkErrorHandler), UserCertificateRepo {

    private val userCertificatesChannel by repoChannel<List<UserCertificate>>(
        io,
        connectivityProvider,
        channelRecreateObserver
    ) {
        networkConfig {
            get = {
                api.getUserCertificates().data
                    .userCertificates.map {
                        UserCertificate.from(it)
                    }
            }
        }
        storageConfig {
            save = { userCertificates ->
                database.userCertificatesDao().clearTable()
                database.userCertificatesDao()
                    .insert(userCertificates.map { UserCertificateEntity.from(it) })
            }
            get = {
                database.userCertificatesDao().getUserCertificates().map { UserCertificate.from(it) }
            }
        }
    }

    override fun getUserCertificates(): Flow<Status<List<UserCertificate>>> =
        userCertificatesChannel.flow

    override suspend fun refreshUserCertificates() = userCertificatesChannel.refresh()

    override suspend fun getCertificateCode(searchValue: String): String? = runWithErrorHandler {
        api.getCertificatePassword(searchValue).data.code
    }
}