package com.robinmaneiro.orderkiosk.ui

import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(onDismiss: () -> Unit) {
    CustomDialog(
        title = "Error",
        body = "Something went wrong - please, try again",
        primaryButtonLabelToAct = "Close" to onDismiss
    )
}
