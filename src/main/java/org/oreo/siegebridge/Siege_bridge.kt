package org.oreo.siegebridge

import SiegeBridePlace
import org.bukkit.plugin.java.JavaPlugin

class Siege_bridge : JavaPlugin() {

    val listOfBridges: MutableList<MutableList<org.bukkit.block.Block>> = mutableListOf()

    override fun onEnable() {
        logger.info("My first kotlin plugin  :)")

        server.pluginManager.registerEvents(SiegeBridePlace(this),this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
