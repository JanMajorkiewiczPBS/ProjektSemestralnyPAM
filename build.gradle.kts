// W pliku: build.gradle.kts (na poziomie projektu)
plugins {
    // Używamy STABILNEJ i ZGODNEJ wersji wtyczki Androida
    id("com.android.application") version "8.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22" apply false
}
