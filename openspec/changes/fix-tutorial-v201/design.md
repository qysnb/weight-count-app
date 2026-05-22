## Context

The TutorialOverlay currently manages sample data creation/deletion (isSample field, WeightRepository/TagRepository dependencies), auto-creates records in steps 2-3, and only navigates to tabs after the welcome step. This creates complexity and a poor UX — users don't learn how to use the app because data is created for them. The trigger logic in MainActivity works correctly in theory but fails if DataStore emits slowly.

## Goals / Non-Goals

**Goals:**
- Tutorial is pure guidance: no auto-creation, no sample deletion
- Tutorial immediately navigates to 记录 tab on start
- Each step shows a visual arrow/pointer to the relevant UI area and explains what to do
- Remove all `isSample`-related code (entity field, migration, DAO, repository)
- Fix trigger: use explicit initial-value check instead of relying on DataStore emission timing
- Version v2.0.1 in AboutScreen and build.gradle.kts

**Non-Goals:**
- No actual arrow/composable drawing over specific UI elements (complexity too high for current scope)
- No animation or video tutorial

## Decisions

- **Remove isSample entirely**: Since we no longer auto-create data, the `isSample` field, migration, DAO methods, and repository methods are dead code. Clean them up.
- **Use LaunchedEffect + navigate on tutorial open**: When `showTutorial` becomes true, immediately navigate to `Screen.Record.route` so the user sees the recording tab behind the overlay.
- **Step content as pure guidance text**: Each step describes what the user should do (e.g., "点击底部导航栏的【记录】图标" / "点击右下角的 + 按钮添加体重") rather than doing it for them.
- **Remove isSample DB migration**: Since we're removing the column, we can revert `AppDatabase` back to version 2 and remove `MIGRATION_2_3`. Users who already migrated to v3 will see a destructive migration (fallbackToDestructiveMigration) or we keep migration for safety.

## Risks / Trade-offs

- [Existing users on v3 DB] → Keep `MIGRATION_2_3` but remove the entity field and DAO methods. Room won't read the column if not mapped. Or use `fallbackToDestructiveMigration()` to rebuild the DB cleanly.
- [Tutorial overlay blocks interaction] → User must dismiss/skip the overlay before they can input data. This is intentional — each step guides them to interact.
