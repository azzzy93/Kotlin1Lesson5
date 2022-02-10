package kg.geektech.kotlin1lesson5.di

import kg.geektech.kotlin1lesson5.core.network.networkModules

val koinModules = listOf(
    networkModules,
    repoModules,
    viewModules
)