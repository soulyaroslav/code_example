
class UserCertificatesFragment : InjectableFragment(R.layout.fragment_user_certificates) {

    private val viewModelActivity by injectedActivityViewModels<MainViewModel>()
    private val viewModel by injectedViewModels<UserCertificatesViewModel>()

    private lateinit var binding: FragmentUserCertificatesBinding
    private val args: UserCertificatesFragmentArgs by navArgs()

    private val userCertificateAdapter by lazy {
        UserCertificateAdapter { item ->
            openUserCertificateDetails(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserCertificatesBinding.bind(view)
        initViews()
        subscribe()
        onClicked()
    }

    private fun initViews() {
        with(binding) {
            toolbar.setTextAppearance(R.style.AppTheme_Text_Headline6_Black)
            val imageId = if (args.routeType == CertificateRouteType.FROM_SIDE_MENU) R.drawable.ic_menu_black else R.drawable.ic_arrow_back_black
            toolbar.setStartDrawable(ContextCompat.getDrawable(requireContext(), imageId))
            userCertificatesRV.apply {
                adapter = userCertificateAdapter
                layoutManager = LinearLayoutManager(requireContext())
                dividerHorizontal(dividerRes = R.drawable.divider_40dp, divideByNearby = true)
                dividerVertical(
                    dividerRes = R.drawable.divider_vertical_20dp,
                    divideByNearby = true
                )
            }
        }
    }

    private fun subscribe() {
        with(binding) {
            viewModel.userCertificates.observe(viewLifecycleOwner) {
                val result = it.data?.obj
                when (it.status) {
                    WorkResult.Status.SUCCESS -> {
                        if (result.isNullOrEmpty()) {
                            emptyStateGroup.show()
                            userCertificatesRV.hide()
                        } else {
                            emptyStateGroup.hide()
                            userCertificatesRV.show()
                            userCertificateAdapter.submitList(result)
                        }
                        userCertificatePB.hide()
                        pullToRefreshSRL.isRefreshing = false
                    }
                    WorkResult.Status.LOADING -> if (userCertificateAdapter.currentList.isEmpty()) {
                        userCertificatePB.show()
                    }
                    WorkResult.Status.ERROR -> {
                        pullToRefreshSRL.isRefreshing = false
                        emptyStateGroup.show()
                        userCertificatesRV.hide()
                        userCertificatePB.hide()
                        handleException(it.exception) {
                            openError()
                        }
                    }
                }
            }
        }
    }

    private fun onClicked() {
        with(binding) {
            pullToRefreshSRL.setOnRefreshListener {
                viewModel.refreshUserCertificates()
            }
            makeCertificateOrderMB.setOnClickListener {
                openCatalogFragment()
            }
            toolbar.setStartDrawableClick {
                if (args.routeType == CertificateRouteType.FROM_SIDE_MENU) viewModelActivity.openDrawer()
                else navigateUp()
            }
        }
    }

    private fun openUserCertificateDetails(item: UserCertificate) {
        UserCertificatesFragmentDirections
            .actionMyPurchaseFragmentToUserCertificateDetailFragment(item)
            .navigate()
    }

    private fun openCatalogFragment() {
        UserCertificatesFragmentDirections
            .actionUserCertificatesFragmentToCertificateNavGraph()
            .navigate()
    }
}