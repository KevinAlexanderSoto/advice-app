package com.kalex.adviceapp.common

data class AdviceState(
    val isLoading: Boolean = false,
    val Advice : String = "",
    val isError: Boolean = false,
    val Error : String = ""
)