
class InitCertificatePaymentTypesUseCase @Inject constructor() :
    UseCaseParams<List<CertificatePriceItem>, InitCertificatePaymentTypesUseCase.Params>() {

    class Params private constructor(val certificate: Certificate) {
        companion object {
            fun toParams(certificate: Certificate) = Params(certificate)
        }
    }

    override suspend fun execute(params: Params): List<CertificatePriceItem> = with(params) {
        if (certificate.certificatePrices.any { it.isCurrentPrice }) {
            certificate.certificatePrices
                .map {
                    CertificatePriceItem.fromWithMultiplier(
                        certificatePrice = it,
                        isCurrentPrice = it.isCurrentPrice,
                        multiplier = certificate.selectedCount
                    )
                }
        } else {
            certificate.certificatePrices
                .map {
                    CertificatePriceItem.fromWithMultiplier(
                        certificatePrice = it,
                        isCurrentPrice = false,
                        multiplier = certificate.selectedCount
                    )
                }.mapIndexed { index, paymentPrice ->
                    if (index == 0) paymentPrice.isCurrentPrice = true
                    paymentPrice
                }
        }
    }
}