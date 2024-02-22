package eu.mrlubau.lobbyengine

import eu.mrlubau.lobbyengine.commands.edit
import eu.mrlubau.lobbyengine.commands.engine
import eu.mrlubau.lobbyengine.commands.setspawn
import eu.mrlubau.lobbyengine.listeners.*
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class Lobby_engine : JavaPlugin() {


    companion object {

        val guiMap: MutableMap<UUID, Inventory> = mutableMapOf()
    }

    private lateinit var editListener: EditListener


    override fun onEnable() {
        saveDefaultConfig()

        editListener = EditListener()

        val config: FileConfiguration = config
        val pluginText = config.getString("plugin", "Error")

        logger.info("===================================")
        logger.info("        $pluginText plugin       ")
        logger.info("                                   ")
        logger.info("Loading.")
        logger.info("Loading..")
        logger.info("Loading...")
        logger.info("Loaded!")
        logger.info("===================================")

        Nickname(this).register()

        registerCommands()
        registerListeners()

        val dbchost = config.getString("database.host", "Error")
        val dbcusername = config.getString("database.username", "Error")
        val dbcdatabase = config.getString("database.database", "Error")
        val dbcpassword = config.getString("database.password", "Error")

        val dburl = "jdbc:mysql://$dbchost/$dbcdatabase"
        val dbuser = "$dbcusername"
        val dbpassword = "$dbcpassword"

        val connection: Connection = DriverManager.getConnection(dburl, dbuser, dbpassword)
    }

    private fun registerListeners() {
        server.pluginManager.registerEvents(editListener, this)
        server.pluginManager.registerEvents(Email(this), this)
        server.pluginManager.registerEvents(Spawn(this), this)
        server.pluginManager.registerEvents(emojis(), this)

        logger.info("Registered Listeners")
    }

    private fun registerCommands() {
        getCommand("edit")?.setExecutor(edit(editListener, this))
        getCommand("engine")?.setExecutor(engine(this))
        getCommand("setspawn")?.setExecutor(setspawn(this))

        logger.info("Registered Commands")
    }

    override fun onDisable() {
        val config: FileConfiguration = config
        val pluginText = config.getString("plugin", "Error")

        logger.info("===================================")
        logger.info("        $pluginText plugin       ")
        logger.info("                                   ")
        logger.info("Unloaded!")
        logger.info("===================================")
    }
}
