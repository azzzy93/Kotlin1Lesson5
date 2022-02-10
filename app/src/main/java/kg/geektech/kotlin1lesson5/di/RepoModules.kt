package kg.geektech.kotlin1lesson5.di

import kg.geektech.kotlin1lesson5.repository.Repository
import org.koin.dsl.module

val repoModules = module {
    single { Repository(get()) }
}