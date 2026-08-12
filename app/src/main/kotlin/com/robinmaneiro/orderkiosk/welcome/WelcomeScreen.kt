package com.robinmaneiro.orderkiosk.welcome

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.menu.model.DiningOption
import com.robinmaneiro.orderkiosk.ui.OrdlyBranding
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.util.extensions.noRippleClickable
import com.robinmaneiro.orderkiosk.util.extensions.showToast
import com.robinmaneiro.orderkiosk.welcome.model.LanguageData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<WelcomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(WelcomeViewModel.UiState())
    val action by viewModel.actions.collectAsStateWithLifecycle(null)
    action?.let {
        HandleAction(it, context)
    }

    WelcomeScreenContent(
        modifier = modifier,
        languageOptions = uiState.languageOptions.toImmutableList(),
        onLanguageClick = { languageCode -> viewModel.onHandleEvent(WelcomeViewModel.UiEvent.OnLanguageSelected(languageCode)) },
        onEatInClick = { mainUiEvent.invoke(NavigationEvent.NavigateToDestination(Screens.MenuScreen(DiningOption.EAT_IN.toString()).route)) },
        onTakeAwayClick = { mainUiEvent.invoke(NavigationEvent.NavigateToDestination(Screens.MenuScreen(DiningOption.TAKE_AWAY.toString()).route)) }
    )
}

@Suppress("NonSkippableComposable")
@Composable
fun HandleAction(action: WelcomeViewModel.Actions, context: Context) {
    when (action) {
        WelcomeViewModel.Actions.ShowErrorDialog -> {
            val activity = LocalActivity.current
            var showErrorDialog by remember { mutableStateOf(true) }
//            if (showErrorDialog) {
//                ErrorDialog {
//                    showErrorDialog = false
//                    activity?.finishAffinity() // Close the app, since this is the only screen visible to the user
//                }
//            }
        }

        is WelcomeViewModel.Actions.ShowToastMessage -> context.showToast(action.message)
    }
}

@Composable
fun WelcomeScreenContent(
    languageOptions: ImmutableList<LanguageData>,
    onLanguageClick: (languageCode: String) -> Unit,
    onEatInClick: () -> Unit,
    onTakeAwayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        OrdlyBranding(
            logoSize = 38.dp,
            appNameColor = MaterialTheme.colorScheme.onBackground,
            brandNameColor = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.TopStart)
                .padding(start = 24.dp, top = 12.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.welcome_dining_question),
                style = MaterialTheme.typography.displaySmall.copy(
                    textAlign = TextAlign.Center,
                    lineHeight = 56.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(height = 44.dp))

            Row {
                DeliveryTypeCard(
                    text = DiningOption.EAT_IN.uiText,
                    diningOption = DiningOption.EAT_IN,
                    onClick = onEatInClick
                )
                Spacer(Modifier.width(36.dp))
                DeliveryTypeCard(
                    text = DiningOption.TAKE_AWAY.uiText,
                    diningOption = DiningOption.TAKE_AWAY,
                    onClick = onTakeAwayClick
                )
            }

            Spacer(Modifier.height(height = 44.dp))

            LanguageSection(
                languageOptions = languageOptions,
                onOptionClick = onLanguageClick
            )
        }

        LegalSection(Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun LanguageSection(
    languageOptions: ImmutableList<LanguageData>,
    onOptionClick: (languageAlpha2Code: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        languageOptions.forEach { option ->
            LanguageCard(option, onOptionClick)
        }
    }
}

@Composable
fun DeliveryTypeCard(
    text: String,
    diningOption: DiningOption,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = when (diningOption) {
        DiningOption.EAT_IN -> MaterialTheme.colorScheme.secondaryContainer
        DiningOption.TAKE_AWAY -> MaterialTheme.colorScheme.primaryContainer
    }
    val borderColor = when (diningOption) {
        DiningOption.EAT_IN -> MaterialTheme.colorScheme.secondary
        DiningOption.TAKE_AWAY -> MaterialTheme.colorScheme.primary
    }
    val textColor = when (diningOption) {
        DiningOption.EAT_IN -> MaterialTheme.colorScheme.onSecondaryContainer
        DiningOption.TAKE_AWAY -> MaterialTheme.colorScheme.onPrimaryContainer
    }

    Card(
        modifier = modifier
            .size(width = 320.dp, height = 260.dp)
            .noRippleClickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(2.dp, borderColor.copy(alpha = 0.35f))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 26.sp),
                color = textColor
            )
        }
    }
}

@Composable
fun LegalSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(R.string.terms_of_service),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(R.string.nutritional_values_allergens),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun LanguageCard(
    languageOption: LanguageData,
    onLanguageClick: (languageCode: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderWidth = if (languageOption.isSelected) 3.dp else 1.dp
    val borderColor = if (languageOption.isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }

    Column(
        modifier = modifier
            .noRippleClickable { onLanguageClick.invoke(languageOption.languageAlpha2Code) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(LocalContext.current)
                .data(languageOption.countryFlag)
                .build(),
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(borderWidth, borderColor, CircleShape),
            contentDescription = null
        )
        Spacer(Modifier.height(8.dp))
        Text(
            languageOption.languageLabel,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@PreviewPixelTablet
@Composable
private fun WelcomeScreenPreview() {
    val languageOptions = listOf(
        LanguageData(R.drawable.flag_gb, languageAlpha2Code = "en", "English", true),
        LanguageData(R.drawable.flag_es, "es", "Spanish", false),
        LanguageData(R.drawable.flag_de, "de", "German", false),
        LanguageData(R.drawable.flag_fr, "fr", "French", false)
    )
    WelcomeScreenContent(languageOptions.toImmutableList(), onLanguageClick = {}, onEatInClick = {}, onTakeAwayClick = {})
}
