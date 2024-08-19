package org.oreo.siegebridge.listeners

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.oreo.siegebridge.Siege_bridge

class BridgeBreakListener (private val plugin: Siege_bridge) : Listener {

    @EventHandler
    fun onBridgeBroken(e:BlockBreakEvent){
        val block = e.block

        if (block.type != Material.OAK_PLANKS){
            return
        }

        for (bridgeList: MutableList<Block> in plugin.listOfBridges){
            if (bridgeList.contains(block)){
                for (bridgeBlock : Block in bridgeList){
                    bridgeBlock.type = Material.AIR
                }
            }
        }
    }
}