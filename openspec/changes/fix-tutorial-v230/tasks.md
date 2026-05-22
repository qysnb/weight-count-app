## 1. Fix Tutorial First-Trigger Disappearance

- [x] 1.1 In `MainActivity.kt`, remove pre-navigation from `onRestartTutorial` — only `showTutorial = true`
- [x] 1.2 In `TutorialOverlay.kt`, set step 0 `navigateTo = null` — no auto-navigation on welcome step
