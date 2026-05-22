package com.weightcount.app.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class PeriodConfig(
    val label: String,
    val days: Int
)

class SettingsDataStore(private val context: Context) {

    companion object {
        private val CHART_7D_KEY = intPreferencesKey("chart_7d")
        private val CHART_30D_KEY = intPreferencesKey("chart_30d")
        private val CHART_90D_KEY = intPreferencesKey("chart_90d")
        private val CHART_YEAR_KEY = intPreferencesKey("chart_year")
        private val CHART_180D_KEY = intPreferencesKey("chart_180d")
        private val CHART_7D_LABEL_KEY = stringPreferencesKey("chart_7d_label")
        private val CHART_30D_LABEL_KEY = stringPreferencesKey("chart_30d_label")
        private val CHART_90D_LABEL_KEY = stringPreferencesKey("chart_90d_label")
        private val CHART_180D_LABEL_KEY = stringPreferencesKey("chart_180d_label")
        private val CHART_YEAR_LABEL_KEY = stringPreferencesKey("chart_year_label")
    }

    val chart7dDays: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[CHART_7D_KEY] ?: 7
    }

    val chart30dDays: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[CHART_30D_KEY] ?: 30
    }

    val chart90dDays: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[CHART_90D_KEY] ?: 90
    }

    val chartYearDays: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[CHART_YEAR_KEY] ?: 365
    }

    val chart180dDays: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[CHART_180D_KEY] ?: 180
    }

    val chart7dLabel: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CHART_7D_LABEL_KEY] ?: "7日"
    }

    val chart30dLabel: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CHART_30D_LABEL_KEY] ?: "30日"
    }

    val chart90dLabel: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CHART_90D_LABEL_KEY] ?: "90日"
    }

    val chartYearLabel: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CHART_YEAR_LABEL_KEY] ?: "年度"
    }

    val chart180dLabel: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CHART_180D_LABEL_KEY] ?: "180日"
    }

    private val dayConfigs: Flow<Map<String, Int>> = combine(
        chart7dDays, chart30dDays, chart90dDays, chart180dDays, chartYearDays
    ) { d7, d30, d90, d180, dYear ->
        mapOf("7d" to d7, "30d" to d30, "90d" to d90, "180d" to d180, "year" to dYear)
    }

    private val labelConfigs: Flow<Map<String, String>> = combine(
        chart7dLabel, chart30dLabel, chart90dLabel, chart180dLabel, chartYearLabel
    ) { l7, l30, l90, l180, lYear ->
        mapOf("7d" to l7, "30d" to l30, "90d" to l90, "180d" to l180, "year" to lYear)
    }

    val allPeriodConfigs: Flow<Map<String, PeriodConfig>> = combine(dayConfigs, labelConfigs) { days, labels ->
        days.mapValues { (key, dayValue) ->
            PeriodConfig(labels[key] ?: key, dayValue)
        }
    }

    suspend fun setChartPeriod(key: String, days: Int) {
        context.dataStore.edit { prefs ->
            when (key) {
                "7d" -> prefs[CHART_7D_KEY] = days
                "30d" -> prefs[CHART_30D_KEY] = days
                "90d" -> prefs[CHART_90D_KEY] = days
                "180d" -> prefs[CHART_180D_KEY] = days
                "year" -> prefs[CHART_YEAR_KEY] = days
            }
        }
    }

    suspend fun setChartPeriodLabel(key: String, label: String) {
        context.dataStore.edit { prefs ->
            when (key) {
                "7d" -> prefs[CHART_7D_LABEL_KEY] = label
                "30d" -> prefs[CHART_30D_LABEL_KEY] = label
                "90d" -> prefs[CHART_90D_LABEL_KEY] = label
                "180d" -> prefs[CHART_180D_LABEL_KEY] = label
                "year" -> prefs[CHART_YEAR_LABEL_KEY] = label
            }
        }
    }
}
