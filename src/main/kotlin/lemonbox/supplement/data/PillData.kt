package lemonbox.supplement.data

import io.swagger.v3.oas.annotations.media.Schema
import lemonbox.supplement.entity.Pill
import java.time.LocalDate

data class PillRequestDto(
    @Schema(description = "영양제 ID")
    var supplementId: Long,
    @Schema(description = "복용 날짜")
    var date: LocalDate,
    @Schema(description = "영양제 복용 여부(IS_CHECKED, IS_NOT_CHECKED)")
    var status: PillStatus,
) {
    constructor(pill: Pill): this(pill.supplement.id, pill.date, pill.status)
}

data class PillResponseDto(
    @Schema(description = "영양제 ID")
    var supplementId: Long,
    @Schema(description = "영양제 이름")
    var name: String,
    @Schema(description = "복용 날짜")
    var date: LocalDate,
    @Schema(description = "복용 여부")
    var status: PillStatus,
) {
    constructor(pill: Pill): this(pill.supplement.id, pill.supplement.name, pill.date, pill.status)
}

data class CheckDto(
    @Schema(description = "날짜")
    var date: LocalDate,
    @Schema(description = "전체 영양제 복용 여부")
    var isChecked: Boolean,
)