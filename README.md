# MISS Minimal

**A distraction-free Android launcher. Text only. No icons. Pure focus.**

[![License: MIT](https://img.shields.io/badge/License-MIT-white.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%208.0%2B-black.svg)](https://developer.android.com)
[![Version](https://img.shields.io/badge/version-2.0-white)](https://github.com/sak21shi23/MissMInimal/releases)

---

## What is MISS Minimal?

MISS Minimal is a minimalist Android home screen launcher that strips your phone down to its bare essentials. No app icons. No colorful UI. No distractions. Just text — clean, black and white.

The idea is simple: most of the time you unlock your phone, you don't actually need it. The moment you see a colorful grid of icons, your brain starts wandering. MISS Minimal removes that trigger entirely. Five apps max on the home screen, listed as plain text. Everything else stays in the drawer — out of sight, out of mind.

---

## What's New in v2.0

### Redesigned UI / UX
- Upgraded typography to **Plus Jakarta Sans** for a cleaner, modern editorial aesthetic
- Bottom navigation bar redesigned with a subtle pill-shaped card container
- Focus Mode tab indicator with smooth animated underline
- Redesigned EXIT button with badge styling
- Enhanced backgrounds for drawer items and interactive controls
- High-contrast clock display (85sp) and massive fullscreen overlay (140sp)
- Refined padding and vertical alignment across all screen sizes

### New Bottom Bar — 3 Toggles
The home screen bottom bar features three immediate actions:
- **FOCUS** — Opens Focus Mode (Stopwatch, Timer, Strict Mode, Pomodoro)
- **SOT** — Opens Screen On Time tracker (today's application usage breakdown)
- **TIME** — Opens fullscreen immersive clock overlay (tap anywhere to dismiss)

### SOT — Screen On Time
Dedicated screen tracking active device and application usage today:
- Real-time usage aggregation via Android's `UsageStatsManager`
- Direct summary on the home screen in hours and minutes
- Per-app breakdown sorted by screen time duration

### Focus Mode — 4 Powerful Tools
Four distinct focus utilities to help reclaim attention:

1. **Stopwatch**
   - Clean Start / Pause / Resume / Stop workflow
   - Flag feature to record lap timestamps during active sessions
   - Scrollable lap history

2. **Timer**
   - Intuitive HH : MM : SS pickers
   - Persistent countdown with vibration on completion

3. **Strict Mode**
   - 25-minute distraction-free lockdown session
   - Automatically activates Do Not Disturb (DND)
   - Temporary camera lock via Device Admin API
   - Notification and swipe interference blocking
   - Uninterrupted fullscreen countdown

4. **Pomodoro Mode**
   - Custom session durations: 25 / 50 / 75 / 100 minutes
   - Up to 3 configurable allowed apps during work phase
   - Emergency contact quick dial with direct call integration
   - Dialing restrictions ensure only configured emergency contacts can be called
   - Distinct work and rest phase detection with gentle haptic alerts

### Quick Settings Panel
A lightweight 9-toggle control center accessible via swipe:
- **WiFi** — Quick toggle panel & direct settings access
- **Data** — Internet connectivity shortcut
- **Bluetooth** — System Bluetooth controls
- **Flashlight** — Direct camera torch toggle
- **DND** — Instant Do Not Disturb mode
- **Rotate** — Auto-rotation toggle
- **Hotspot** — Tethering controls
- **Location** — Location services shortcut
- **Airplane** — Network panel toggle
- **Sound & Display** — Volume mode (Normal / Vibrate / Silent), volume slider, auto-brightness toggle, and brightness slider

### Launcher Settings
- **Typography:** Global font size adjustment (8–20sp) with real-time UI scaling
- **System Controls:** Quick management of DND, flashlight, volume, and brightness
- **Safety & Transparency:** Embedded open-source terms, privacy commitment, and permission explanations

---

## All Features

### Home Screen
- High-contrast time and date presentation
- Dynamic daily Screen On Time counter
- 5 primary slots: Phone & Camera fixed, 3 user-customizable
- Long-press any slot to assign or clear apps
- Gesture navigation:
  - **Swipe Up:** Open text App Drawer
  - **Swipe Down:** Open Quick Settings
  - **EXIT:** Open system launcher selector

### App Drawer
- Fast, alphabetical plain-text list of installed applications
- Instant incremental search
- Long-press contextual options: Add to Home, Set Time Limit, Rename, App Info, Uninstall

### Application Time Limits
- Set per-app daily or session limits (1m, 5m, 10m, 15m, or custom)
- Gracefully redirects user back to launcher once limit expires

---

## Screenshots

<table align="center">
  <tr>
    <th>Home Screen</th>
    <th>App Drawer</th>
    <th>Quick Settings</th>
    <th>Timer</th>
  </tr>
  <tr>
    <td><img src="https://res.cloudinary.com/dpuitxsuh/image/upload/v1776881372/Screenshot_20260422_233549_ju08on.jpg" width="180"/></td>
    <td><img src="https://res.cloudinary.com/dpuitxsuh/image/upload/v1776881375/Screenshot_20260422_233262_rbcxze.jpg" width="180"/></td>
    <td><img src="https://res.cloudinary.com/dpuitxsuh/image/upload/v1776881373/Screenshot_20260422_233621_gnewve.jpg" width="180"/></td>
    <td><img src="https://res.cloudinary.com/dpuitxsuh/image/upload/v1776881372/Screenshot_20260422_233612_9x0clt.jpg" width="180"/></td>
  </tr>
  <tr>
    <th>Strict Mode</th>
    <th>Pomodoro Mode</th>
    <th>Stopwatch</th>
    <th>Time</th>
  </tr>
  <tr>
    <td><img src="https://res.cloudinary.com/dpuitxsuh/image/upload/v1757618285/STRICT_MODE_aushsh.jpg" width="180"/></td>
    <td><img src="https://res.cloudinary.com/dpuitxsuh/image/upload/v1776881374/Screenshot_20260422_233614_mkwrex.jpg" width="180"/></td>
    <td><img src="https://res.cloudinary.com/dpuitxsuh/image/upload/v1776881372/Screenshot_20260422_233601_anan4a.jpg" width="180"/></td>
    <td><img src="https://res.cloudinary.com/dpuitxsuh/image/upload/v1776881372/Screenshot_20260422_233062_wiqt4e.jpg" width="180"/></td>
  </tr>
</table>

---

## Getting Started

### Option 1 — Install Pre-built APK

1. Head over to [**Releases**](https://github.com/sak21shi23/MissMInimal/releases).
2. Download the latest `MISS-Minimal.apk`.
3. Open the APK on your device, enable installation from unknown sources if prompted, and install.
4. Press your Home button, choose **MISS Minimal**, and select **Always**.

> **Note:** If Google Play Protect warns against installing standalone sideloaded APKs:
> 1. Open Google Play Store → Profile Icon → **Play Protect**.
> 2. Open Settings (top right gear).
> 3. Temporarily pause **Scan apps with Play Protect** during manual installation.

---

### Option 2 — Build from Source

**Prerequisites:**
- Android Studio Iguana / Ladybug or newer
- Android SDK (API Level 34)
- JDK 17+

```bash
# Clone the repository
git clone https://github.com/sak21shi23/MissMInimal.git

# Enter the project directory
cd MissMInimal
```

Open the project in Android Studio, allow Gradle Sync to finish, connect an Android device (Android 8.0+ / API 26+), and click **Run**.

---

## Permissions Overview

| Permission | Purpose |
|---|---|
| `QUERY_ALL_PACKAGES` | Enumerate installed applications for the text drawer |
| `SYSTEM_ALERT_WINDOW` | Keep focus and block interruptions during strict sessions |
| `ACCESS_NOTIFICATION_POLICY` | Enable Do Not Disturb during focus sessions |
| `BIND_ACCESSIBILITY_SERVICE` | Intercept distraction gestures while launcher is active |
| `BIND_DEVICE_ADMIN` | Disable camera during Strict Mode focus |
| `CAMERA` | Open default camera app from home shortcut & flashlight toggle |
| `MODIFY_AUDIO_SETTINGS` | Adjust volume directly from quick settings |
| `WRITE_SETTINGS` | Manage brightness control from settings |
| `PACKAGE_USAGE_STATS` | Calculate on-device Screen On Time metrics |
| `READ_CONTACTS` & `CALL_PHONE` | Direct dialing for designated Pomodoro emergency contacts |
| `VIBRATE` | Haptic feedback for timers and session transitions |
| `RECEIVE_BOOT_COMPLETED` | Automatic service initialization after device reboot |
| `POST_NOTIFICATIONS` | Ongoing background timer notifications |

---

## Privacy Policy & Data Guarantee

MISS Minimal adheres to a strict zero-telemetry principle:
- **No Internet Required:** The application requires no network permissions for its core features.
- **No Analytics or Trackers:** Zero third-party SDKs, telemetry, advertising networks, or usage collection.
- **100% Local Storage:** Configuration, app limits, and usage metrics never leave your device and reside solely in private application sandbox storage.

---

## Tech Stack & Architecture

- **Language:** Kotlin
- **Minimum SDK:** API 26 (Android 8.0 Oreo)
- **Target SDK:** API 34 (Android 14)
- **UI:** ViewBinding, Custom Layouts, ConstraintLayout, RecyclerView, BottomSheetDialog
- **Font:** Plus Jakarta Sans
- **Storage:** Local Encrypted SharedPreferences
- **Components:** Foreground Services, Accessibility Service, NotificationListenerService, DeviceAdminReceiver

---

## Contributing

Contributions, bug reports, and suggestions are welcome!
1. Fork the Project.
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the Branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

---

## License

Distributed under the MIT License. See [`LICENSE`](LICENSE) for more details.

```text
MIT License

Copyright (c) 2026 sak21shi23
```

---

*MISS Minimal — because your phone should work for you, not against you.*
