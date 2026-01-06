package uk.co.softlantic.orderkiosk.welcome

import android.content.Context
import androidx.activity.compose.LocalActivity
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel
import uk.co.softlantic.orderkiosk.NavigationEvent
import uk.co.softlantic.orderkiosk.R
import uk.co.softlantic.orderkiosk.Screens
import uk.co.softlantic.orderkiosk.menu.model.DiningOption
import uk.co.softlantic.orderkiosk.ui.PreviewPixelTablet
import uk.co.softlantic.orderkiosk.ui.theme.Aquamarine40
import uk.co.softlantic.orderkiosk.ui.theme.DarkGrey
import uk.co.softlantic.orderkiosk.ui.theme.Iceberg
import uk.co.softlantic.orderkiosk.util.extensions.noRippleClickable
import uk.co.softlantic.orderkiosk.util.extensions.showToast
import uk.co.softlantic.orderkiosk.welcome.model.LanguageData

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
        onLanguageClick = { languageCode -> viewModel.updateLanguage(languageCode) },
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
            .background(color = Iceberg.copy(alpha = 0.5F))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Where you want to enjoy your meal?",
                fontSize = 64.sp,
                color = DarkGrey,
                textAlign = TextAlign.Center,
                lineHeight = 90.sp
            )

            Spacer(Modifier.height(height = 32.dp))

            Row {
                DeliveryTypeCard(text = DiningOption.EAT_IN.uiText, onClick = onEatInClick)
                Spacer(Modifier.width(40.dp))
                DeliveryTypeCard(text = DiningOption.TAKE_AWAY.uiText, onClick = onTakeAwayClick)
            }

            Spacer(Modifier.height(height = 32.dp))

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
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        languageOptions.forEach { option ->
            LanguageCard(option, onOptionClick)
        }
    }
}

@Composable
fun DeliveryTypeCard(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(width = 360.dp, height = 300.dp)
            .noRippleClickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Aquamarine40.copy(alpha = 0.8F),
            contentColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 48.sp)
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
            text = "Terms of Service",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray)
        )
        Text(
            text = "Nutritional Values & Allergens",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray)
        )
    }
}

@Composable
fun LanguageCard(
    languageOption: LanguageData,
    onLanguageClick: (languageCode: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .run {
                if (!languageOption.isSelected) return@run this
                border(1.dp, Color.DarkGray)
            }
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
                .size(70.dp)
                .clip(CircleShape),
            contentDescription = null
        )
        Spacer(Modifier.height(8.dp))
        Text(
            languageOption.languageLabel,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@PreviewPixelTablet
@Composable
private fun WelcomeScreenPreview() {
    val languageOptions = listOf(
        LanguageData(
            R.drawable.flag_gb,
            languageAlpha2Code = "en",
            "English",
            true
        ),
        LanguageData(
            R.drawable.flag_es,
            "es",
            "Spanish",
            false
        ),
        LanguageData(
            R.drawable.flag_de,
            "de",
            "German",
            false
        ),
        LanguageData(
            R.drawable.flag_fr,
            "fr",
            "French",
            false
        )
    )
    WelcomeScreenContent(languageOptions.toImmutableList(), onLanguageClick = {}, onEatInClick = {}, onTakeAwayClick = {})
}
