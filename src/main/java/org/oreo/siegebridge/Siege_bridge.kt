package org.oreo.siegebridge

import SiegeBridePlace
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import org.oreo.siege_ladders.commands.GetSiegeBridge
import org.oreo.siegebridge.itemManager.ItemManager
import org.oreo.siegebridge.listeners.BridgeBreakListener
import sun.jvm.hotspot.opto.Block

class Siege_bridge : JavaPlugin() {

    val listOfBridges: MutableList<MutableList<org.bukkit.block.Block>> = mutableListOf()

    override fun onEnable() {
        logger.info("My first kotlin plugin  :)")

        ItemManager.init(this) // Register items

        server.pluginManager.registerEvents(SiegeBridePlace(this),this)
        server.pluginManager.registerEvents(BridgeBreakListener(this),this)

        getCommand("siege-bridge")!!.setExecutor(GetSiegeBridge()) //Register the command to get a siegeLadder
    }

    override fun onDisable() {
        for (bridgeList: MutableList<org.bukkit.block.Block> in listOfBridges){
            for (bridgeBlock: org.bukkit.block.Block in bridgeList) {
                bridgeBlock.type = Material.AIR
            }
        }
    }
}
