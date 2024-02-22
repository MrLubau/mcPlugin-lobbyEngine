package eu.mrlubau.lobbyengine.listeners

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.*
import org.bukkit.event.hanging.HangingBreakByEntityEvent
import org.bukkit.event.hanging.HangingPlaceEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.*
import org.bukkit.plugin.java.JavaPlugin

class EditListener : Listener {

    private val editingPlayers = mutableSetOf<Player>()

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player: Player = event.player
        if (!isEditing(player)) {
            event.isCancelled = true
        }
    }


    @EventHandler
    fun onHangingPlace(event: HangingPlaceEvent) {
        val hanging = event.entity
        val player: Player? = event.player

        if (hanging is ItemFrame && player != null) {
            if (!isEditing(player)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player: Player = event.player
        if (!isEditing(player)) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.damager is Player && event.entity is Player) {
            val attacker: Player = event.damager as Player
            if (!isEditing(attacker)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onHangingBreak(event: HangingBreakByEntityEvent) {
        val entity = event.entity
        val remover = event.remover

        if (entity is ItemFrame && remover is Player) {
            if (!isEditing(remover)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        val damagedEntity = event.entity
        val damager = event.damager

        if (damager is Player) {
            val attacker: Player = damager

            if (!isEditing(attacker)) {
                event.isCancelled = true
            }
        }

        if (event.entity is org.bukkit.entity.Player) {
            val player = event.entity as org.bukkit.entity.Player

            if (!player.gameMode.equals(GameMode.CREATIVE)) {
                when (event.cause) {
                    EntityDamageEvent.DamageCause.FALL -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.LAVA -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.FIRE, EntityDamageEvent.DamageCause.FIRE_TICK -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.DROWNING -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.KILL -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.WORLD_BORDER -> {
                        event.isCancelled = false
                    }
                    EntityDamageEvent.DamageCause.CONTACT -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.ENTITY_ATTACK -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.PROJECTILE -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.SUFFOCATION -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.MELTING -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.BLOCK_EXPLOSION -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.ENTITY_EXPLOSION -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.VOID -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.LIGHTNING -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.SUICIDE -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.STARVATION -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.POISON -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.MAGIC -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.WITHER -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.FALLING_BLOCK -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.THORNS -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.DRAGON_BREATH -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.CUSTOM -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.FLY_INTO_WALL -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.HOT_FLOOR -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.CRAMMING -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.DRYOUT -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.FREEZE -> {
                        event.isCancelled = true
                    }
                    EntityDamageEvent.DamageCause.SONIC_BOOM -> {
                        event.isCancelled = true
                    }
                }
            }
        }
    }


    @EventHandler
    fun onArmorStandSpawn(event: EntitySpawnEvent) {
        val entity = event.entity

        if (entity is ArmorStand) {
            if (entity.customName != null && entity.customName == "EditPlayer") {
                return
            }

            if (entity is Player) {
                val attacker: Player = entity

                if (!isEditing(attacker)) {
                    event.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun onArmorStandManipulate(event: PlayerArmorStandManipulateEvent) {
        val armorStand = event.rightClicked

        if (armorStand != null && armorStand is ArmorStand) {
            val attacker: Player = event.player

            if (!isEditing(attacker)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        val entity = event.rightClicked

        if (entity is ItemFrame || entity is ArmorStand) {
            val player = event.player

            if (!isEditing(player)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val action = event.action
        val clickedBlock = event.clickedBlock

        if (action == Action.RIGHT_CLICK_BLOCK && clickedBlock != null && clickedBlock.type == Material.ITEM_FRAME) {
            val player = event.player

            if (!isEditing(player)) {
                event.isCancelled = true
            }
        }

        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            val player = event.player
            val block = event.clickedBlock
            if (block != null && isEditableSign(block.type.name)) {
                if (!isEditing(player)) {
                    event.isCancelled = true
                }
            }
            }

        if (event.hasBlock() && isBlockedContainer(clickedBlock!!.type)) {
            val player = event.player
            if (!isEditing(player)) {
                event.isCancelled = true
            }
        }

        if (event.hasBlock() && isBlockedTrapdoorOrGate(clickedBlock!!.type)) {
            val player = event.player
            if (!isEditing(player)) {
                event.isCancelled = true
            }
        }
    }


    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val whoClicked = event.whoClicked

        if (whoClicked is Player) {
            val player: Player = whoClicked

            if (!isEditing(player)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onInventoryDrag(event: InventoryDragEvent) {
        val whoClicked = event.whoClicked

        if (whoClicked is Player) {
            val player: Player = whoClicked

            if (!isEditing(player)) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player
        if (!isEditing(player)) {
            event.isCancelled = true
        }
    }


    private fun isBlockedTrapdoorOrGate(material: Material): Boolean {
        val blockedTrapdoorsAndGates = setOf(
            Material.OAK_TRAPDOOR,
            Material.SPRUCE_TRAPDOOR,
            Material.BIRCH_TRAPDOOR,
            Material.JUNGLE_TRAPDOOR,
            Material.ACACIA_TRAPDOOR,
            Material.DARK_OAK_TRAPDOOR,
            Material.BAMBOO_TRAPDOOR,
            Material.WARPED_TRAPDOOR,
            Material.CRIMSON_TRAPDOOR,
            Material.CHERRY_TRAPDOOR,
            Material.MANGROVE_TRAPDOOR,

            Material.OAK_FENCE_GATE,
            Material.SPRUCE_FENCE_GATE,
            Material.BIRCH_FENCE_GATE,
            Material.JUNGLE_FENCE_GATE,
            Material.ACACIA_FENCE_GATE,
            Material.DARK_OAK_FENCE_GATE,
            Material.BAMBOO_FENCE_GATE,
            Material.WARPED_FENCE_GATE,
            Material.CRIMSON_FENCE_GATE,
            Material.CHERRY_FENCE_GATE,
            Material.MANGROVE_FENCE_GATE,

            Material.REDSTONE_WIRE,
            Material.LEVER,
            Material.DAYLIGHT_DETECTOR,
        )

        return blockedTrapdoorsAndGates.contains(material)
    }

    private fun isEditableSign(blockType: String): Boolean {
        val forbiddenSignTypes = setOf("OAK_SIGN", "SPRUCE_SIGN", "BIRCH_SIGN", "JUNGLE_SIGN", "ACACIA_SIGN", "DARK_OAK_SIGN", "MANGROVE_SIGN", "CHERRY_SIGN", "BAMBOO_SIGN", "WARPED_SIGN", "CRIMSON_SIGN", "OAK_HANGING_SIGN", "SPRUCE_HANGING_SIGN", "BIRCH_HANGING_SIGN", "JUNGLE_HANGING_SIGN", "ACACIA_HANGING_SIGN", "DARK_OAK_HANGING_SIGN", "MANGROVE_HANGING_SIGN", "CHERRY_HANGING_SIGN", "BAMBOO_HANGING_SIGN", "WARPED_HANGING_SIGN", "CRIMSON_HANGING_SIGN", "OAK_WALL_SIGN", "SPRUCE_WALL_SIGN", "BIRCH_WALL_SIGN", "JUNGLE_WALL_SIGN", "ACACIA_WALL_SIGN", "DARK_OAK_WALL_SIGN", "MANGROVE_WALL_SIGN", "CHERRY_WALL_SIGN", "BAMBOO_WALL_SIGN", "WARPED_WALL_SIGN", "CRIMSON_WALL_SIGN", "OAK_WALL_HANGING_SIGN", "SPRUCE_WALL_HANGING_SIGN", "BIRCH_WALL_HANGING_SIGN", "JUNGLE_WALL_HANGING_SIGN", "ACACIA_WALL_HANGING_SIGN", "DARK_OAK_WALL_HANGING_SIGN", "MANGROVE_WALL_HANGING_SIGN", "CHERRY_WALL_HANGING_SIGN", "BAMBOO_WALL_HANGING_SIGN", "WARPED_WALL_HANGING_SIGN", "CRIMSON_WALL_HANGING_SIGN")

        return forbiddenSignTypes.contains(blockType)
    }

    private fun isBlockedContainer(material: Material): Boolean {
        val blockedContainers = setOf(
            Material.BARREL,
            Material.CHEST,
            Material.TRAPPED_CHEST,
            Material.ENDER_CHEST,
            Material.FURNACE,
            Material.BLAST_FURNACE,
            Material.SMOKER,
            Material.LECTERN,
            Material.HOPPER,
            Material.DISPENSER,
            Material.DROPPER,
            Material.CRAFTING_TABLE,
            Material.LOOM,
            Material.STONECUTTER,
            Material.RESPAWN_ANCHOR,
            Material.CARTOGRAPHY_TABLE,
            Material.FLETCHING_TABLE,
            Material.SMITHING_TABLE,
            Material.GRINDSTONE,
            Material.ANVIL,
            Material.CHIPPED_ANVIL,
            Material.DAMAGED_ANVIL,
            Material.NOTE_BLOCK,
            Material.JUKEBOX,
            Material.ENCHANTING_TABLE,
            Material.BREWING_STAND,
            Material.BEACON,
            Material.DECORATED_POT,
            Material.BLUE_BED,
            Material.BLACK_BED,
            Material.BROWN_BED,
            Material.GREEN_BED,
            Material.CYAN_BED,
            Material.GRAY_BED,
            Material.LIGHT_BLUE_BED,
            Material.LIGHT_GRAY_BED,
            Material.LIME_BED,
            Material.MAGENTA_BED,
            Material.ORANGE_BED,
            Material.PINK_BED,
            Material.PURPLE_BED,
            Material.RED_BED,
            Material.WHITE_BED,
            Material.YELLOW_BED,
            Material.SHULKER_BOX,
            Material.RED_SHULKER_BOX,
            Material.BLUE_SHULKER_BOX,
            Material.PURPLE_SHULKER_BOX,
            Material.GREEN_SHULKER_BOX,
            Material.LIME_SHULKER_BOX,
            Material.PINK_SHULKER_BOX,
            Material.LIGHT_GRAY_SHULKER_BOX,
            Material.GRAY_SHULKER_BOX,
            Material.LIGHT_BLUE_SHULKER_BOX,
            Material.BLACK_SHULKER_BOX,
            Material.CYAN_SHULKER_BOX,
            Material.ORANGE_SHULKER_BOX,
            Material.BROWN_SHULKER_BOX,
            Material.MAGENTA_SHULKER_BOX,
            Material.WHITE_SHULKER_BOX,
            Material.YELLOW_SHULKER_BOX,
        )

        return blockedContainers.contains(material)
    }

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        if (event.entityType == EntityType.PRIMED_TNT) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        event.foodLevel = 20
    }


    private fun isEditing(player: Player): Boolean {
        return editingPlayers.contains(player)
    }

    fun enableEditing(player: Player) {
        editingPlayers.add(player)
    }

    fun disableEditing(player: Player) {
        editingPlayers.remove(player)
    }

    fun isPlayerEditing(player: Player): Boolean {
        return isEditing(player)
    }
}
