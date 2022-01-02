package com.github.iamtakagi.frameprotector;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FrameProtector extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}

    @EventHandler
    public void onTrigger(EntityDamageByEntityEvent event) {
        Player player;
        Entity damager = event.getDamager();
        if (event.getEntity() instanceof ItemFrame) {
            if (event.getDamager() instanceof Player) {
                player = (Player) damager;
                if (player.getGameMode() != GameMode.CREATIVE) {
                    event.setCancelled(true);
                }
            } else if (event.getDamager() instanceof FishHook) {
                player = (Player) ((FishHook) damager).getShooter();
                if (player.getGameMode() != GameMode.CREATIVE) {
                    event.setCancelled(true);
                }
            } else if (event.getDamager() instanceof Projectile) {
                player = (Player) ((Projectile) damager).getShooter();
                if (player.getGameMode() != GameMode.CREATIVE) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!event.isCancelled() && event.getRightClicked() instanceof ItemFrame && !((ItemFrame) event.getRightClicked()).getItem().getType().equals(Material.AIR)) {
            Player player = event.getPlayer();
            if (player.getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            Block block = event.getClickedBlock();
            if (block == null) return;
            if (block.getType() == Material.ITEM_FRAME) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(HangingBreakByEntityEvent event) {
        Entity entity = event.getRemover();
        if (entity instanceof Player) {
            if (((Player) entity).getGameMode() != GameMode.CREATIVE) {
                if (event.getEntity() instanceof ItemFrame) {
                    event.setCancelled(true);
                }
            }
        }
    }
}