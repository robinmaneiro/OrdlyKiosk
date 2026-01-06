package uk.co.softlantic.orderkiosk.ui

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/**
 * This annotation will show a solid background, show system bars, and render it in a Pixel Tablet device.
 */
@Preview(
    showBackground = true,
    device = Devices.PIXEL_TABLET
)
annotation class PreviewPixelTablet
