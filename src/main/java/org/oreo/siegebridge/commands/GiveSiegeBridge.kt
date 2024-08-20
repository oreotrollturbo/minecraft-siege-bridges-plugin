package org.oreo.siege_ladders.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.oreo.siegebridge.itemManager.ItemManager

class GetSiegeBridge : CommandExecutor {
    /**
     * Gives the player a siege ladder if the player is OP
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can use this command")
            return true
        }

        val player: Player = sender as Player
        if (player.isOp) {
            ItemManager.siegeBridge?.let { player.inventory.addItem(it) }
            player.sendMessage("Gave you a siege bridge successfully")
        } else {
            player.sendMessage("§c You don't have permission to use this command")
        }
        return true
    }
}