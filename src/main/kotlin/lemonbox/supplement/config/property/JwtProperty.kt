package lemonbox.supplement.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix="jwt")
@ConstructorBinding
data class JwtProperty(
    val secret: String,
    val refresh: String,
)
