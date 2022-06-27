package lemonbox.supplement

import lemonbox.supplement.config.property.JwtProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(JwtProperty::class)
class SupplementApplication

fun main(args: Array<String>) {
	runApplication<SupplementApplication>(*args)
}
