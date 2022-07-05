package lemonbox.supplement.data

import lemonbox.supplement.entity.Supplement

data class SupplementRequestDto (
    var name: String,
    var userId: String,
    var count: Int,
)

data class SupplementResponseDto (
    var id: Long,
    var name: String,
    var count: Int,
) {
    constructor(supplement: Supplement): this(supplement.id, supplement.name, supplement.count)
}