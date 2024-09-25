package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import test.TestDependencies


fun DependencyHandler.androidx() {
    implementation(Dependencies.ANDROIDX_CORE)
    implementation(Dependencies.ANDROIDX_LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.ANDROIDX_ACTIVITY_COMPOSE)
    implementation(platform(Dependencies.ANDROIDX_COMPOSE_BOM))
    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_GRAPHICS)
    implementation(Dependencies.TOOLING_PREVIEW)
    implementation(Dependencies.MATERIAL3)
}

fun DependencyHandler.testDeps() {
    testImplementation(TestDependencies.JUNIT)
}

fun DependencyHandler.testImplDeps() {
    androidTestImplementation(TestDependencies.ANDROIDX_JUNIT)
    androidTestImplementation(TestDependencies.ANDROIDX_ESPRESSO_CORE)
    androidTestImplementation(platform(Dependencies.ANDROIDX_COMPOSE_BOM))
    androidTestImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TEST)
}

fun DependencyHandler.testDebugDeps() {
    debugImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TOOLING)
    debugImplementation(TestDependencies.ANDROIDX_COMPOSE_UI_TEST_MANIFEST)
}
