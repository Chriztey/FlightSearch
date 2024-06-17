// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    extra.apply {
        set("nav_version", "2.5.3")
        set("room_version", "2.6.1")
    }
}