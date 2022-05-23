
class UserCertificatesViewModel @Inject constructor(
    getUserCertificatesUseCase: GetUserCertificatesUseCase,
    private val refreshUserCertificatesUseCase: RefreshUserCertificatesUseCase,
    private val getUserCertificatePasswordUseCase: GetUserCertificatePasswordUseCase
) : BaseViewModel() {

    val userCertificates = getUserCertificatesUseCase.execute().toWorkLiveData()

    private val _certificateCode = MutableLiveData<WorkResult<String?>>()
    val certificatePassword: LiveData<WorkResult<String?>>
        get() = _certificateCode

    fun refreshUserCertificates() {
        ioToUnit {
            refreshUserCertificatesUseCase.execute()
        }
    }

    fun getCertificatePassword(searchValue: String) {
        val params = GetUserCertificatePasswordUseCase.Params.toParams(searchValue)
        ioToUiWorkData(
            io = { getUserCertificatePasswordUseCase.execute(params) },
            ui = { _certificateCode.postValue(it) }
        )
    }
}