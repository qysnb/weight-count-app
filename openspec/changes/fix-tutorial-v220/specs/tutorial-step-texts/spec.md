## MODIFIED Requirements

### Requirement: Step 3 description (添加记录)

The step 3 description SHALL accurately describe where the "+" button is located and how many records to add.

**Previous text:**
```
点击右下角的 + 按钮，填写体重、日期和标签信息，添加一条体重记录。
```

**New text:**
```
点击下方中间的 + 按钮，填写体重、日期和标签信息，添加两条体重记录。
```

**Changes:**
- "右下角" → "下方中间" (button position)
- "添加一条" → "添加两条" (number of records)

#### Scenario: Step 3 displays corrected position
- **WHEN** user reaches step 3 of the tutorial
- **THEN** the description says "点击下方中间的 + 按钮" instead of "点击右下角的 + 按钮"

#### Scenario: Step 3 displays corrected record count
- **WHEN** user reaches step 3 of the tutorial
- **THEN** the description says "添加两条体重记录" instead of "添加一条体重记录"

### Requirement: Step 6 description (设置与自定义)

The step 6 description SHALL use the updated text for re-entering the tutorial.

**Previous text:**
```
• 使用本教程
```

**New text:**
```
• 再次体验本教程
```

#### Scenario: Step 6 displays corrected tutorial button label
- **WHEN** user reaches step 6 of the tutorial
- **THEN** the description says "再次体验本教程" instead of "使用本教程"
