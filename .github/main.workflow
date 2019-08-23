workflow "Run tests" {
  on = "push"
  resolves = ["Raul6469/android-gradle-action@1.0.0"]
}

action "Raul6469/android-gradle-action@1.0.0" {
  uses = "Raul6469/android-gradle-action@1.0.0"
  secrets = ["ANDROID_LICENCE"]
}
