package com.robinmaneiro.orderkiosk.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.robinmaneiro.orderkiosk.R

@Composable
fun ErrorDialog(
    @StringRes bodyRes: Int = R.string.error_dialog_body,
    onDismiss: () -> Unit
) {
    CustomDialog(
        title = stringResource(R.string.error_dialog_title),
        body = stringResource(bodyRes),
        primaryButtonLabelToAct = stringResource(R.string.btn_close) to onDismiss
    )
}
