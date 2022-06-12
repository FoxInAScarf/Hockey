package com.hockey.veo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Listeners implements Listener {

    @EventHandler
    public void onHold(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))
            if (p.getNearbyEntities(3, 3, 3).contains(Main.puck.getEntity()) && Main.running) {

                if (p.getLocation().getPitch() >= -20 && Main.pp.get(p) - p.getLocation().getPitch() > 0) {

                    double d = (Main.pp.get(p) - p.getLocation().getPitch()) / Main.pp.get(p) * 10,
                            theta = Math.atan2(
                                    (Main.puck.getLocation().getX() - p.getLocation().getX()),
                                    (Main.puck.getLocation().getZ() - p.getLocation().getZ())
                            );
                    Main.puck.shoot(Math.toDegrees(theta), d);

                }

                Main.pp.put(p, p.getLocation().getPitch());
                Main.pl.put(p, p.getLocation());

            }


        if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))
            if (p.getNearbyEntities(3, 3, 3).contains(Main.puck.getEntity()) && Main.running) {

                Main.puck.slow(85);
                e.setCancelled(true);

            }


    }

}
