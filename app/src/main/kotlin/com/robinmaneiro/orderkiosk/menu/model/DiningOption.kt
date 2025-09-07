package com.robinmaneiro.orderkiosk.menu.model

enum class DiningOption {
    EAT_IN {
        override val uiText = "Eat In"
    }, TAKE_AWAY {
        override val uiText = "Take Away"
    };
    abstract val uiText: String
}
