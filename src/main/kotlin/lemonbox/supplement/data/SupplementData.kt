package lemonbox.supplement.data

import io.swagger.v3.oas.annotations.media.Schema
import lemonbox.supplement.entity.Supplement

data class SupplementRequestDto (
    @Schema(description = "영양제 이름")
    var name: String,
    @Schema(description = "알약 개수")
    var count: Int,
)

data class SupplementResponseDto (
    @Schema(description = "영양제 ID")
    var id: Long,
    @Schema(description = "영양제 이름")
    var name: String,
    @Schema(description = "알약 개수")
    var count: Int,
    @Schema(description = "재구매 횟수")
    var repill: Int,
) {
    constructor(supplement: Supplement): this(supplement.id, supplement.name, supplement.count, supplement.repill)
}

data class UpdateRequestDto (
    var id: Long,
    var count: Int,
)