@file:Suppress("unused")

package com.thebrownfoxx.components.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy

@Composable
fun <T> rememberMutableStateOf(
    value: T,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy(),
) = remember { mutableStateOf(value, policy) }

@Composable
fun rememberMutableIntStateOf(value: Int) = remember { mutableIntStateOf(value) }

@Composable
fun rememberMutableLongStateOf(value: Long) = remember { mutableLongStateOf(value) }

@Composable
fun rememberMutableFloatStateOf(value: Float) = remember { mutableFloatStateOf(value) }

@Composable
fun rememberMutableDoubleStateOf(value: Double) = remember { mutableDoubleStateOf(value) }

@Composable
fun <T> rememberDerivedStateOf(calculation: () -> T) = remember { derivedStateOf(calculation) }