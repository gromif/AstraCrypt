package com.nevidimka655.astracrypt.view.purchases

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.theme.icons.BackgroundExtreme
import com.nevidimka655.astracrypt.app.theme.icons.BackgroundStarter
import com.nevidimka655.astracrypt.app.theme.icons.Purchases

@Preview
@Composable
fun PurchaseCard(
    @StringRes nameRes: Int = R.string.plan_starter,
    @ColorRes nameColorRes: Int = R.color.plan_starter_textColor,
    backgroundVector: ImageVector = Icons.Purchases.BackgroundStarter,
    @ColorRes crownIconColorRes: Int = R.color.plan_starter_crownColor,
    isPurchased: Boolean = false,
    onCardClick: ((planNameRes: Int) -> Unit)? = null
) = Card(modifier = Modifier.height(200.dp), elevation = CardDefaults.cardElevation(1.dp)) {
    Box(
        modifier = Modifier.clickable(enabled = onCardClick != null) {
            onCardClick?.invoke(nameRes)
        }
    ) {
        Image(
            painter = rememberVectorPainter(image = backgroundVector),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        if (isPurchased) Icon(
            painter = painterResource(id = R.drawable.ic_crown_circle),
            contentDescription = null,
            tint = colorResource(id = crownIconColorRes),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(40.dp)
        )
        Text(
            text = stringResource(id = nameRes),
            color = colorResource(id = nameColorRes),
            style = MaterialTheme.typography.displayLarge,
            fontSize = 70.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun PurchasePremiumCard(
    isPurchased: Boolean = false,
    onCardClick: ((planNameRes: Int) -> Unit)? = null
) = PurchaseCard(
    nameRes = R.string.plan_premium,
    nameColorRes = R.color.plan_extreme_textColor,
    backgroundVector = Icons.Purchases.BackgroundExtreme,
    crownIconColorRes = R.color.plan_extreme_crownColor,
    isPurchased = isPurchased,
    onCardClick = onCardClick
)