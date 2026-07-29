package com.robinmaneiro.orderkiosk.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.robinmaneiro.orderkiosk.R

@Composable
fun ErrorDialog(onDismiss: () -> Unit) {
    CustomDialog(
        title = stringResource(R.string.error_dialog_title),
        body = stringResource(R.string.error_dialog_body),
        primaryButtonLabelToAct = stringResource(R.string.btn_close) to onDismiss
    )
}
