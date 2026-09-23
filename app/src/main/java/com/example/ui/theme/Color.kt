package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// flo warm organic sage, cream & olive palette matching reference image
val FloOliveDark = Color(0xFF384E1B)      // Deep mossy olive green (primary buttons, dark titles, active pill)
val FloOliveMedium = Color(0xFF557028)    // Rich olive green
val FloOliveLight = Color(0xFF86A838)     // Fresh lime-olive green (accent words: "Good", "Event", etc.)
val FloOliveSoft = Color(0xFFA6C55F)      // Pale olive green
val FloLimeHighlight = Color(0xFF98BC3B)  // Pill tag highlight for words like [Salted air], [soft beats]
val FloLimeHighlightText = Color(0xFFFFFFFF)

val FloAmberGold = Color(0xFFF3B728)      // Warm amber gold (Rockstar Perform Event card)
val FloAmberDark = Color(0xFFD69814)

val FloBgCream = Color(0xFFF8F9F3)        // Pale warm cream screen top background
val FloBgSage = Color(0xFFEDF3E2)         // Soft pale sage gradient mid
val FloBgPistachio = Color(0xFFE2ECCC)    // Gentle pistachio gradient bottom

val FloCardBg = Color(0xFFFFFFFF)         // White card surface
val FloCardWarm = Color(0xFFFBFDF7)       // Warm frosted card
val FloCardBorder = Color(0xFFE2ECC9)     // Soft sage-lime outline
val FloCardBorderSubtle = Color(0xFFEBF1D9)

val FloTextDark = Color(0xFF202A14)       // Deep charcoal olive for primary headings
val FloTextBody = Color(0xFF3A472A)       // Primary readable body text
val FloTextMuted = Color(0xFF71805E)      // Muted sage for subtitles & metadata
val FloNavBg = Color(0xFFE5EED6)          // Floating pill navigation bar background
val FloNavActive = Color(0xFF384E1B)      // Active circular icon background (dark olive)

// Legacy alias mappings for seamless compatibility
val FloBlue = FloOliveDark
val FloBlueDark = FloOliveDark
val FloBlueLight = FloBgSage
val FloBlueSurface = FloBgCream
val FloCyan = FloOliveLight

val Slate900 = FloTextDark
val Slate800 = FloTextDark
val Slate700 = FloTextBody
val Slate600 = FloTextMuted
val Slate500 = FloTextMuted
val Slate400 = Color(0xFF9AA886)
val Slate300 = Color(0xFFCAD7B8)
val Slate200 = FloCardBorder
val Slate100 = FloBgSage
val Slate50 = FloBgCream

val AccentGreen = FloOliveMedium
val AccentOrange = FloAmberGold
val AccentRed = Color(0xFFE05252)

// Theme compatibility
val Purple80 = FloBgSage
val PurpleGrey80 = FloCardBorder
val Pink80 = FloOliveLight

val Purple40 = FloOliveDark
val PurpleGrey40 = FloTextMuted
val Pink40 = FloOliveMedium
