package lemonbox.supplement.data

import lemonbox.supplement.entity.Pill
import java.time.LocalDateTime

data class PillDto(
    var supplementId: Long,
    var date: LocalDateTime,
    var status: PillStatus,
) {
    constructor(pill: Pill): this(pill.supplement.id, pill.date, pill.status)
}
