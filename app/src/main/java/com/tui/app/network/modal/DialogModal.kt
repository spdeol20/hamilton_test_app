package com.tui.app.network.modal

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

data class DialogModal(var showDialog :Boolean = false, val content:  @Composable () -> Unit={ Text(text = "")})