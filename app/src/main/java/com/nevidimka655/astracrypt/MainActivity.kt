package com.nevidimka655.astracrypt

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.google.android.material.color.DynamicColors
import com.nevidimka655.astracrypt.databinding.MainBinding
import com.nevidimka655.astracrypt.features.auth.AuthType
import com.nevidimka655.astracrypt.tabs.settings.security.authentication.Camouflage
import com.nevidimka655.astracrypt.ui.Main
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.appearance.AppearanceManager
import com.nevidimka655.astracrypt.utils.extensions.lazyFast
import com.nevidimka655.crypto.tink.TinkConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm by viewModels<MainVM>()
    private val binding by lazyFast { MainBinding.inflate(layoutInflater) }
    private val toolbar get() = binding.toolbar
    private val appBarConfigurationMainIds = setOf(
        R.id.homeFragment, R.id.filesFragment, R.id.starredFragment, R.id.settingsFragment,
        R.id.authFragment
    )
    val fab get() = binding.fab
    val fabLarge get() = binding.fabLarge

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Engine.init(applicationContext)
        setupDynamicColors()
        TinkConfig.init()
        with(vm) {
            if (!isDatabaseCreated()) setupForFirstUse()
            encryptionManager.loadEncryptionInfo()
        }
        setContent { Main() }
        /*setContentView(binding.root)
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
        }*/
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

    @SuppressLint("PrivateResource")
    private fun setUiStateOnFragment(fragmentDest: Int) {
        val isDestinationMain = appBarConfigurationMainIds.contains(fragmentDest)
        var uiState = UiState(
            navBarEnabled = isDestinationMain,
            navBarColorTinted = isDestinationMain
        )
        if (isDestinationMain) vm.toolsManager.releaseMemory()
        uiState = when (fragmentDest) {

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

}