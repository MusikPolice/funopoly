package ca.jonathanfritz.funopoly.config

/**
 * Configuration class - values are loaded in at runtime via Hoplite
 * See https://github.com/sksamuel/hoplite
 */
data class Config(
    val maxRounds: Int,
    val initialPlayerFunds: Int,
    val bank: BankConfig
)