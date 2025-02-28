package io.gromif.astracrypt.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nevidimka655.haptic.Haptic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Haptic.init(context = applicationContext)
        setContent { AstraCryptApp() }
        /*with(binding) {
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

}