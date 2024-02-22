package eu.mrlubau.lobbyengine.listeners

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet


class Nickname(private val plugin: JavaPlugin) : PlaceholderExpansion() {
    override fun getIdentifier(): String {
        return "engine"
    }

    override fun getAuthor(): String {
        return "lubau"
    }

    override fun getVersion(): String {
        return "1.0"
    }

    init {
        plugin.saveDefaultConfig()
    }

    private fun getPlayerNameFromDatabase(playerName: String): String? {
        val config: FileConfiguration = plugin.config

        val dbchost = config.getString("database.host", "Error")
        val dbcusername = config.getString("database.username", "Error")
        val dbcdatabase = config.getString("database.database", "Error")
        val dbcpassword = config.getString("database.password", "Error")

        val jdbcUrl = "jdbc:mysql://$dbchost/$dbcdatabase"
        val username = "$dbcusername"
        val password = "$dbcpassword"

        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            val connection: Connection = DriverManager.getConnection(jdbcUrl, username, password)
            val query = "SELECT nick FROM authme WHERE username = ?"
            val statement: PreparedStatement = connection.prepareStatement(query)
            statement.setString(1, playerName)

            val resultSet: ResultSet = statement.executeQuery()

            if (resultSet.next()) {
                return resultSet.getString("nick")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    override fun onPlaceholderRequest(player: Player?, identifier: String): String? {
        if (player != null) {
            if (identifier.equals("player", ignoreCase = true)) {
                val playerNameFromDatabase = getPlayerNameFromDatabase(player.name)

                if (playerNameFromDatabase == null || playerNameFromDatabase.isEmpty()) {
                    return player.name
                } else {
                    return playerNameFromDatabase
                }

            }
        }
        return null
    }
}