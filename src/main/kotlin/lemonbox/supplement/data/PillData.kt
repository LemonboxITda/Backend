package lemonbox.supplement.data

import lemonbox.supplement.entity.Pill
import java.time.LocalDate

data class PillRequestDto(
    var supplementId: Long,
    var date: LocalDate,
    var status: PillStatus,
) {
    constructor(pill: Pill): this(pill.supplement.id, pill.date, pill.status)
}

data class PillResponseDto(
    var supplementId: Long,
    var name: String,
    var date: LocalDate,
    var status: PillStatus,
) {
    constructor(pill: Pill): this(pill.supplement.id, pill.supplement.name, pill.date, pill.status)
}

data class CheckDto(
    var date: LocalDate,
    var isChecked: Boolean,
)