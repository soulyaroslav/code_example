class UserCertificateViewHolder(
    private val binding: ItemUserCertificateBinding,
    private val onClick: (UserCertificate) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val context = itemView.context
    private var item: UserCertificate? = null

    init {
        binding.root.setOnClickListener {
            item?.let(onClick)
        }
    }

    fun bind(item: UserCertificate) {
        this.item = item
        with(binding) {
            certificateTitleACTV.text = item.remark
            certificatePriceACTV.text = context.getString(
                R.string.points_format_title,
                item.pointsPrice,
                item.currencyPrice
            )
            certificateExpirationACTV.text =
                context.getString(R.string.expiration_date_title, item.expireAt)
            certificateDateACTV.text = item.createAt
        }
    }
}