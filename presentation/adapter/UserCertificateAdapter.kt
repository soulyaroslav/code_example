
class UserCertificateAdapter(
    private val onClick: (UserCertificate) -> Unit
) : ListAdapter<UserCertificate, UserCertificateViewHolder>(UserCertificateDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCertificateViewHolder {
        val binding =
            ItemUserCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserCertificateViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: UserCertificateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object UserCertificateDiffCallback : DiffUtil.ItemCallback<UserCertificate>() {
        override fun areItemsTheSame(oldItem: UserCertificate, newItem: UserCertificate) =
            oldItem.certificateNumber == newItem.certificateNumber

        override fun areContentsTheSame(oldItem: UserCertificate, newItem: UserCertificate) =
            oldItem.remark == newItem.remark && oldItem.description == newItem.description
                    && oldItem.certificateKind == newItem.certificateKind && oldItem.certificateType == newItem.certificateType
                    && oldItem.expireAt == newItem.expireAt && oldItem.cost == newItem.cost
                    && oldItem.pointsPrice == newItem.pointsPrice && oldItem.pointsPrice == newItem.pointsPrice
                    && oldItem.currencyPrice == newItem.currencyPrice && oldItem.link == newItem.link
                    && oldItem.password == newItem.password && oldItem.createAt == newItem.createAt
    }
}