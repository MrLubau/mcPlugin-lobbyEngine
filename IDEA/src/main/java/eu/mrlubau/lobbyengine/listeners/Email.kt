package eu.mrlubau.lobbyengine.listeners

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Email(private val plugin: JavaPlugin) : Listener {

    init {
        plugin.saveDefaultConfig()
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val config: FileConfiguration = plugin.config
        val player = event.player
        val prefix = config.getString("prefix", "Error")
        val pcolor = config.getString("pcolor", "Error")
        val scolor = config.getString("scolor", "Error")
        val tcolor = config.getString("tcolor", "Error")
        val playerName = event.player.name

        val dbchost = config.getString("database.host", "Error")
        val dbcusername = config.getString("database.username", "Error")
        val dbcdatabase = config.getString("database.database", "Error")
        val dbcpassword = config.getString("database.password", "Error")

        val dburl = "jdbc:mysql://$dbchost/$dbcdatabase"
        val dbuser = "$dbcusername"
        val dbpassword = "$dbcpassword"
        val connection: Connection = DriverManager.getConnection(dburl, dbuser, dbpassword)
        val query = "SELECT * FROM authme WHERE username = ?"


        try {
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, playerName)

            val resultSet = preparedStatement.executeQuery()

            if (resultSet.next()) {
                val emailValue = resultSet.getString("email")

                if (emailValue == null || emailValue.isEmpty()) {
                    player.sendMessage("$tcolor ===========================================")
                    player.sendMessage("$scolor                ⚠ UPOZORNĚNÍ ⚠")
                    player.sendMessage("$pcolor K tvému účtu nebyl nalezen žádný email")
                    player.sendMessage("$pcolor Z bezpečnostního důvodu si ho můžete přidat na")
                    player.sendMessage("$scolor https://hype-play.cz/account/profile")
                    player.sendMessage("$tcolor ===========================================")
                } else {
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            try {
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

    }
}