package com.robinmaneiro.orderkiosk.util

import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview


/**
 * This annotation will show a solid background, show system bars, and render it in a Pixel Tablet device.
 */
@Preview(
    showBackground = true,
    device = PIXEL_TABLET
)
annotation class PixelTabletPreview