package org.oreo.siegebridge

import SiegeBridePlace
import org.bukkit.plugin.java.JavaPlugin
import org.oreo.siegebridge.listeners.BridgeBreakListener

class Siege_bridge : JavaPlugin() {

    val listOfBridges: MutableList<MutableList<org.bukkit.block.Block>> = mutableListOf()

    override fun onEnable() {
        logger.info("My first kotlin plugin  :)")

        server.pluginManager.registerEvents(SiegeBridePlace(this),this)
        server.pluginManager.registerEvents(BridgeBreakListener(this),this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
