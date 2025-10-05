package com.robinmaneiro.orderkiosk.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40
import com.robinmaneiro.orderkiosk.ui.theme.SandyBrown40

@Composable
fun CustomDialog(
    title: String,
    body: String,
    primaryButtonLabelToAct: Pair<String, () -> Unit>,
    modifier: Modifier = Modifier,
    secondaryButtonLabelToAct: Pair<String, () -> Unit>? = null,
    onDismiss: (() -> Unit)? = null
) {
    BackHandler(enabled = true) { onDismiss?.invoke() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.40f)) // TODO: Move to an independent color?
            .clickable { onDismiss?.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RectangleShape,
            modifier = Modifier
                .clickable(enabled = false) { },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(width = 400.dp)
                    .heightIn(250.dp)
                    .padding(16.dp)
            ) {

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = body, style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    secondaryButtonLabelToAct?.let { (label, action) ->
                        TextButton(
                            onClick = action
                        ) {
                            Text(
                                text = label,
                                color = SandyBrown40,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }


                    val (primaryButtonLabel, onPrimaryButtonClick) = primaryButtonLabelToAct
                    Button(
                        modifier = Modifier.size(150.dp, 50.dp),
                        colors = buttonColors(
                            containerColor = Aquamarine40,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(5.dp),
                        onClick = { onPrimaryButtonClick.invoke() }
                    ) {
                        Text(
                            text = primaryButtonLabel,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
