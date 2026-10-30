package com.minimalist.launcher

import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import android.os.SystemClock
import android.telecom.TelecomManager

object PomodoroManager {
    var isActive = false
    var isWorkPhase = true
    var currentSession = 1
    var totalSessions = 1
    var selectedDurationMinutes = 25
    val sessionCount: Int get() = currentSession
    
    var lastLaunchTime = 0L
    var lastLaunchedAppPkg: String? = null

    var isEmergencyCallActive = false
    var lastEmergencyCallTime = 0L
    var defaultDialerPackage: String? = null

    val userSelectedApps = mutableListOf<String>()
    val allowedPackages = mutableSetOf<String>()
    val emergencyContacts = MutableList<Pair<String, String>?>(2) { null }

    private val ALWAYS_ALLOWED = setOf(
        "com.minimalist.launcher",
        "android",
        // System frameworks & Google Play Services
        "com.google.android.gms",
        "com.google.android.gsf",
        "com.android.vending",
        // Permission & security dialogs
        "com.android.permissioncontroller",
        "com.google.android.permissioncontroller",
        "com.coloros.securitypermission",
        "com.oplus.securitypermission",
        "com.coloros.safecenter",
        "com.oplus.safecenter",
        "com.android.packageinstaller",
        "com.google.android.packageinstaller"
    )

    val CALL_PACKAGES = setOf(
        // Core telecom & phone services
        "com.android.phone",
        "com.android.server.telecom",
        "com.android.telecom",
        "com.google.android.incallui",
        "com.android.incallui",
        "com.samsung.android.incallui",
        "com.vivo.incallui",
        "com.coloros.telecom",
        "com.oplus.telecom",
        "com.motorola.incallui",
        "com.xiaomi.incallui",
        "com.asus.incallui",
        // OEM dialers / In-call hosts
        "com.google.android.dialer",
        "com.android.dialer",
        "com.samsung.android.dialer",
        "com.vivo.dialer",
        "com.iqoo.phone",
        "com.oneplus.dialer",
        "com.oplus.dialer",
        "com.heytap.dialer",
        "com.coloros.phonemanager"
    )

    fun isEmergencyCallOngoing(context: Context): Boolean {
        if (!isEmergencyCallActive) return false
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
        val mode = audioManager?.mode ?: AudioManager.MODE_NORMAL
        val inCall = mode == AudioManager.MODE_IN_CALL ||
                     mode == AudioManager.MODE_IN_COMMUNICATION ||
                     mode == AudioManager.MODE_RINGTONE

        val timeSinceCall = SystemClock.uptimeMillis() - lastEmergencyCallTime
        if (inCall || timeSinceCall < 12000) {
            return true
        }
        isEmergencyCallActive = false
        return false
    }

    fun isCallRelatedPackage(pkg: String): Boolean {
        val p = pkg.lowercase()
        return CALL_PACKAGES.contains(pkg) ||
               (defaultDialerPackage != null && defaultDialerPackage.equals(pkg, ignoreCase = true)) ||
               p.contains("incallui") ||
               p.contains("telecom") ||
               (p.contains("dialer") && !p.contains("launcher"))
    }

    fun start(
        durationMinutes: Int,
        allowedApps: List<String>,
        contacts: List<Pair<String, String>?>,
        context: Context
    ) {
        selectedDurationMinutes = durationMinutes
        totalSessions = (durationMinutes / 25).coerceAtLeast(1)
        currentSession = 1
        isActive = true
        isWorkPhase = true
        isEmergencyCallActive = false

        emergencyContacts.indices.forEach { emergencyContacts[it] = contacts.getOrNull(it) }

        val safeApps = allowedApps.distinct().take(3)

        userSelectedApps.clear()
        userSelectedApps.addAll(safeApps)

        allowedPackages.clear()
        allowedPackages.addAll(ALWAYS_ALLOWED)
        allowedPackages.addAll(safeApps)

        defaultDialerPackage = context.getSystemService(TelecomManager::class.java)?.defaultDialerPackage

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? android.view.inputmethod.InputMethodManager
        imm?.enabledInputMethodList?.forEach { allowedPackages.add(it.packageName) }

        applyRestrictions(context, enable = true)
        persistState(context)
    }

    fun startBreak(context: Context) {
        isWorkPhase = false
        isEmergencyCallActive = false
        applyRestrictions(context, enable = false)
        persistState(context)
    }

    fun resumeNextWorkSession(context: Context) {
        currentSession++
        isWorkPhase = true
        isEmergencyCallActive = false
        applyRestrictions(context, enable = true)
        persistState(context)
    }

    fun stop(context: Context) {
        isActive = false
        isWorkPhase = true
        currentSession = 1
        totalSessions = 1
        isEmergencyCallActive = false
        userSelectedApps.clear()
        allowedPackages.clear()

        applyRestrictions(context, enable = false)
        context.getSharedPreferences("pomodoro_prefs", Context.MODE_PRIVATE).edit()
            .putBoolean("is_active", false)
            .apply()
    }

    private fun applyRestrictions(context: Context, enable: Boolean) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        if (nm != null && nm.isNotificationPolicyAccessGranted) {
            nm.setInterruptionFilter(
                if (enable) NotificationManager.INTERRUPTION_FILTER_NONE
                else NotificationManager.INTERRUPTION_FILTER_ALL
            )
        }
        if (enable) {
            TopBarBlockerService.start(context)
            StrictModeService.instance?.startBlocking()
        } else {
            TopBarBlockerService.stop(context)
            StrictModeService.instance?.stopBlocking()
        }
    }

    private fun persistState(context: Context) {
        context.getSharedPreferences("pomodoro_prefs", Context.MODE_PRIVATE).edit()
            .putBoolean("is_active", isActive)
            .putBoolean("is_work_phase", isWorkPhase)
            .putInt("current_session", currentSession)
            .putInt("total_sessions", totalSessions)
            .putInt("duration_minutes", selectedDurationMinutes)
            .putStringSet("selected_apps", userSelectedApps.toSet())
            .putString("contact_0_name", emergencyContacts[0]?.first)
            .putString("contact_0_num", emergencyContacts[0]?.second)
            .putString("contact_1_name", emergencyContacts[1]?.first)
            .putString("contact_1_num", emergencyContacts[1]?.second)
            .apply()
    }

    fun restoreFromPrefs(context: Context) {
        val prefs = context.getSharedPreferences("pomodoro_prefs", Context.MODE_PRIVATE)
        if (!prefs.getBoolean("is_active", false)) return
        val dur = prefs.getInt("duration_minutes", 25)
        val curSess = prefs.getInt("current_session", 1)
        val totSess = prefs.getInt("total_sessions", 1)
        val isWork = prefs.getBoolean("is_work_phase", true)
        val apps = prefs.getStringSet("selected_apps", emptySet())?.toList() ?: emptyList()
        val c0 = prefs.getString("contact_0_num", null)?.let { (prefs.getString("contact_0_name", it) ?: it) to it }
        val c1 = prefs.getString("contact_1_num", null)?.let { (prefs.getString("contact_1_name", it) ?: it) to it }

        selectedDurationMinutes = dur
        totalSessions = totSess
        currentSession = curSess
        isActive = true
        isWorkPhase = isWork

        if (c0 != null) emergencyContacts[0] = c0
        if (c1 != null) emergencyContacts[1] = c1

        userSelectedApps.clear()
        userSelectedApps.addAll(apps)

        allowedPackages.clear()
        allowedPackages.addAll(ALWAYS_ALLOWED)
        allowedPackages.addAll(apps)
        defaultDialerPackage = context.getSystemService(TelecomManager::class.java)?.defaultDialerPackage

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? android.view.inputmethod.InputMethodManager
        imm?.enabledInputMethodList?.forEach { allowedPackages.add(it.packageName) }

        applyRestrictions(context, enable = isWork)
    }

    fun isWorkSessionActive() = isActive && isWorkPhase
    fun isBreakActive() = isActive && !isWorkPhase
}
