package com.nevidimka655.astracrypt

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.color.DynamicColors
import com.google.android.material.snackbar.Snackbar
import com.nevidimka655.astracrypt.databinding.MainBinding
import com.nevidimka655.astracrypt.features.auth.AuthType
import com.nevidimka655.astracrypt.tabs.settings.security.authentication.Camouflage
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.utils.ColorManager
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.appearance.AppearanceManager
import com.nevidimka655.astracrypt.utils.extensions.lazyFast
import com.nevidimka655.astracrypt.utils.extensions.withLifecycle
import com.nevidimka655.crypto.tink.TinkConfig
import com.nevidimka655.haptic.hapticClick
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private val vm by viewModels<MainVM>()
    private val binding by lazyFast { MainBinding.inflate(layoutInflater) }
    private val toolbar get() = binding.toolbar
    private val navController by lazyFast {
        (supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment).navController
    }
    private val appBarConfigurationMainIds = setOf(
        R.id.homeFragment, R.id.filesFragment, R.id.starredFragment, R.id.settingsFragment,
        R.id.authFragment
    )
    val fab get() = binding.fab
    val fabLarge get() = binding.fabLarge

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        Engine.init(applicationContext)
        setupDynamicColors()
        TinkConfig.init()
        with(vm) {
            if (!isDatabaseCreated()) setupForFirstUse()
            encryptionManager.loadEncryptionInfo()
        }
        ColorManager.initialize(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupSnackbarsFlow()
        savedInstanceState ?: shouldShowAuthScreen(navController)
        navController.addOnDestinationChangedListener { _, dest, _ ->
            setUiStateOnFragment(dest.id)
        }
        registerAppBarOffsetListener()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right
            )
            binding.bottomNavigationView.updatePadding(bottom = bars.bottom)
            WindowInsetsCompat.CONSUMED
        }
        with(binding) {
            bottomNavigationView.run {
                setBackgroundColor(ColorManager.navigationBarColor)
                setupWithNavController(navController)
            }
            movePanel.setBackgroundColor(ColorManager.navigationBarColor)
            move.setOnClickListener {
                vm.move(
                    itemsArr = vm.selectorManager.getSelectedItemsList(),
                    movingDirId = vm.filesNavigatorList.lastOrNull()?.id
                )
                vm.selectorManager.closeActionMode()
            }
            cancel.setOnClickListener { vm.selectorManager.closeActionMode() }
        }
        vm.uiStateFlow.withLifecycle(lifecycleScope, lifecycle) { parseUiState(it) }
        registerBuyCallback()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isPermissionsGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!isPermissionsGranted) {
                val gt = registerForActivityResult(ActivityResultContracts.RequestPermission()) {

                }
                gt.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun shouldShowAuthScreen(navController: NavController) {
        var startDestination = when (vm.authManager.info.authType) {
            AuthType.NO_AUTH -> R.id.homeFragment
            AuthType.PASSWORD -> R.id.authFragment
        }
        val camouflage = vm.authManager.info.camouflage
        if (camouflage != Camouflage.None) {
            if (camouflage is Camouflage.Calculator) {
                startDestination = R.id.calculatorFragment
                vm.toolsManager.calculatorManager.camouflage = camouflage
            }
        }
        if (startDestination != R.id.homeFragment) {
            val options = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, true)
                .build()
            navController.navigate(startDestination, null, options)
        }
    }

    private fun setupDynamicColors() {
        if (DynamicColors.isDynamicColorAvailable() && AppearanceManager.useDynamicColors) {
            DynamicColors.applyToActivityIfAvailable(this)
        }
    }

    private fun setBottomBarState(slideUp: Boolean = true) {
        val bottomBar = binding.bottomBar
        val params = (bottomBar.layoutParams as CoordinatorLayout.LayoutParams)
        val behavior = params.behavior as HideBottomViewOnScrollBehavior
        with(behavior) {
            if (slideUp && isScrolledDown) slideUp(bottomBar, true)
            else if (!slideUp && isScrolledUp) slideDown(bottomBar, true)
        }
    }

    private fun registerAppBarOffsetListener() {
        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            when (abs(verticalOffset)) {
                0 -> setBottomBarState(true)
                totalScrollRange -> setBottomBarState(false)
            }
        }
    }

    private fun registerBuyCallback() = lifecycleScope.launch {
        vm.billingManager.buyRequestParams.consumeEach {
            if (it != null) {
                vm.billingManager.launchBillingFlow(this@MainActivity, billingParams = it)
            }
        }
    }

    private fun parseUiState(state: UiState) = binding.run {
        bottomNavigationView.isVisible = state.navBarEnabled
        movePanel.isVisible = state.movePanelButtonState
        if (state.fabLargeState) fabLarge.show() else fabLarge.hide()
        if (state.fabState) fab.show() else fab.hide()
        binding.appBarLayout.setExpanded(state.toolbarState, state.toolbarStateAnimated)
    }

    @SuppressLint("PrivateResource")
    private fun setUiStateOnFragment(fragmentDest: Int) {
        val isDestinationMain = appBarConfigurationMainIds.contains(fragmentDest)
        var uiState = UiState(
            navBarEnabled = isDestinationMain,
            navBarColorTinted = isDestinationMain
        )
        if (isDestinationMain) vm.toolsManager.releaseMemory()
        uiState = when (fragmentDest) {
            R.id.detailsFragment, R.id.subscriptionsFragment -> with(vm) {
                cacheUiState()
                uiState.copy(
                    navBarColorTinted = false,
                    fabState = false
                )
            }

            R.id.filesFragment, R.id.starredFragment -> with(vm) {
                if (isUiStateCached()) restoreCachedUiState()
                else vm.getUiState().copy(
                    fabState = fragmentDest == R.id.filesFragment
                )
            }

            R.id.homeFragment, R.id.settingsFragment -> {
                vm.invalidateCachedUiState() // subsF uistate invalidating(filesF & settingsF)
                uiState
            }

            R.id.authFragment, R.id.calculatorFragment -> uiState.copy(
                toolbarState = false,
                toolbarStateAnimated = false,
                navBarEnabled = false,
                navBarColorTinted = false
            )

            R.id.aeadFragment, R.id.notesViewFragment, R.id.archiveFragment -> {
                fabLarge.setImageResource(R.drawable.ic_done)
                uiState.copy(fabLargeState = true)
            }

            R.id.notesFragment -> {
                fabLarge.setImageResource(com.google.android.material.R.drawable.material_ic_edit_black_24dp)
                uiState.copy(fabLargeState = true)
            }

            else -> uiState
        }
        vm.setUiState(uiState)
    }

    private fun setupSnackbarsFlow() = vm.snackbarChannel.withLifecycle(lifecycleScope, lifecycle) {
        showSnackbarIfPossible(it)
    }

    fun showSnackbarIfPossible(resId: Int) {
        val isFabShouldBeAnchored = when (navController.currentDestination!!.id) {
            R.id.filesFragment -> true
            else -> false
        }
        Snackbar
            .make(this, binding.root, getString(resId), 700)
            .setAnchorView(if (isFabShouldBeAnchored) fab else null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        toolbar.hapticClick()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupToolbar() = with(toolbar) {
        setSupportActionBar(this)
        setupActionBarWithNavController(
            navController,
            AppBarConfiguration.Builder(
                appBarConfigurationMainIds
            ).build()
        )
    }

}