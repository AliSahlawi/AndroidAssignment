package dependencies

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dependency: String) {
    add("implementation",dependency)
}
fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", platform(dependency))
}

fun DependencyHandler.testImplementation(dependency: String) {
    add("testImplementation",dependency)
}
fun DependencyHandler.androidTestImplementation(dependency: String) {
    add("androidTestImplementation",dependency)
}
fun DependencyHandler.androidTestImplementation(dependency: Any) {
    add("androidTestImplementation", platform(dependency))
}
fun DependencyHandler.debugImplementation(dependency: String) {
    add("debugImplementation",dependency)
}

fun DependencyHandler.moduleImplementation(dependency: Dependency) {
    add("implementation", dependency)
}