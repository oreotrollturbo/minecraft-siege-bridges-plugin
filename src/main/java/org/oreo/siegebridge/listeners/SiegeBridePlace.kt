import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.oreo.siegebridge.Siege_bridge
import org.oreo.siegebridge.java.GetWarClass

class SiegeBridePlace(private val plugin: Siege_bridge) : Listener {

    @EventHandler
    fun onBridgePlaced(e: PlayerInteractEvent) {
        val act = e.action

        val war = GetWarClass.isWarOn()


        if (act != Action.LEFT_CLICK_BLOCK) {
            return
        }



        val block = e.clickedBlock ?: return
        val player = e.player
        val world = block.world
        val facing = player.facing

        val x = block.x.toDouble()
        val y = block.y.toDouble()
        val z = block.z.toDouble()

        if (siegeBridge(player)) {

            if (!war){
                player.sendMessage("${ChatColor.RED}War is disabled")
            }

            for (timer in 1..3 ){  // Start a repeating task
                val checkBlock = placeBlockIndirection(world, x, y, z, timer, facing)

                var blocksTested = 0
                for (blockLocation: Location in checkBlock){
                    if (blockLocation.block.type.isSolid){
                        blocksTested++
                        if (blocksTested >= 3){
                            player.sendMessage("${ChatColor.RED}The bridge is too short")
                            return
                        }
                    }
                }
            }

            player.itemInHand.amount = player.itemInHand.amount - 1

            object : BukkitRunnable() {
                var i = 1
                val blocksPlaced = mutableListOf<Block>()
                override fun run() {
                    if (i > 15) {
                        cancel() // Stop the task after 15 iterations
                        plugin.listOfBridges.add(blocksPlaced)
                        return
                    }

                    val newBlockLocations = placeBlockIndirection(world, x, y, z, i, facing)

                    var blocksSkipped = 0
                    for (location: Location in newBlockLocations) {
                        if (location.block.type.isSolid) {
                            blocksSkipped++
                            if (blocksSkipped >= 3) {
                                cancel() // Stop the task if 3 solid blocks are encountered
                                plugin.listOfBridges.add(blocksPlaced)
                                break // This shouldn't do anything but just in case
                            }
                            continue
                        }
                        world.playSound(location, Sound.BLOCK_WOOD_PLACE, 1f, 0.4f)
                        location.block.type = Material.OAK_PLANKS
                        blocksPlaced.add(location.block)
                    }
                    i++
                }
            }.runTaskTimer(plugin, 0L, 20L) // 0L delay, 20L (1 second) interval
        }
    }

    private fun siegeBridge(player: Player): Boolean {
        val mainHand = player.inventory.itemInMainHand
        return mainHand.type == Material.GRINDSTONE && mainHand.containsEnchantment(Enchantment.LUCK)
    }

    private fun placeBlockIndirection(world: World, x: Double, y: Double, z: Double, timer: Int, face: BlockFace): List<Location> {
        return when (face) {
            BlockFace.NORTH -> listOf(Location(world, x, y, z - timer), Location(world, x + 1, y, z - timer), Location(world, x - 1, y, z - timer))
            BlockFace.EAST -> listOf(Location(world, x + timer, y, z), Location(world, x + timer, y, z + 1), Location(world, x + timer, y, z - 1))
            BlockFace.SOUTH -> listOf(Location(world, x, y, z + timer), Location(world, x + 1, y, z + timer), Location(world, x - 1, y, z + timer))
            BlockFace.WEST -> listOf(Location(world, x - timer, y, z), Location(world, x - timer, y, z + 1), Location(world, x - timer, y, z - 1))
            else -> listOf(Location(world, x, y, z))
        }
    }

}
