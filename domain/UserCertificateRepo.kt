
interface UserCertificateRepo {
    fun getUserCertificates(): Flow<Status<List<UserCertificate>>>

    suspend fun refreshUserCertificates()

    /**
     * Invokes to get certificate password to be able to activate a certificate
     *
     * @param searchValue - it can be certificateNumber or link
     * @return certificate password
     */
    suspend fun getCertificateCode(searchValue: String): String?
}