package com.nevidimka655.astracrypt.view.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nevidimka655.astracrypt.view.Main
import com.nevidimka655.crypto.tink.TinkConfig
import com.nevidimka655.haptic.Haptic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Haptic.init(context = applicationContext)
        TinkConfig.init()
        setContent { Main() }
        /*savedInstanceState ?: shouldShowAuthScreen(navController)
        navController.addOnDestinationChangedListener { _, dest, _ ->
            setUiStateOnFragment(dest.id)
        }
        with(binding) {
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

    /*private fun shouldShowAuthScreen(navController: NavController) {
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
    }*/

    /*@SuppressLint("PrivateResource")
    private fun setUiStateOnFragment(fragmentDest: Int) {
        uiState = when (fragmentDest) {

            R.id.aeadFragment, R.id.notesViewFragment, R.id.archiveFragment -> {
                fabLarge.setImageResource(R.drawable.ic_done)
            }

            R.id.notesFragment -> {
                fabLarge.setImageResource(com.google.android.material.R.drawable.material_ic_edit_black_24dp)
            }
        }
    }*/

}