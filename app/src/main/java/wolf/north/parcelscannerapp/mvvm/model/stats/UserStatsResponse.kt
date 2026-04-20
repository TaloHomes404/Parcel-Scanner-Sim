package wolf.north.parcelscannerapp.mvvm.model.stats

import wolf.north.parcelscannerapp.mvvm.view.profile.DayActivity

data class UserStatsResponse(
    val weeklyActivity: List<DayActivity>,
    val totalPackages: Int,
    val totalForms: Int
)