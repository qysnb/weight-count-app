## 1. Multi-Select Tag Filter

- [x] 1.1 Changed to `selectedTagIds: Set<Long>`, added `toggleTag()` and `selectAllTags()`, filter uses OR set membership.
- [x] 1.2 Updated chip row: "全部" → `selectAllTags()`, tag chips → `toggleTag()`, selection via `tag.id in selectedTagIds`.

## 2. About Page GitHub Link

- [x] 2.1 Removed "GitHub: " prefix, added `fillMaxWidth()` + `TextAlign.Center`.
