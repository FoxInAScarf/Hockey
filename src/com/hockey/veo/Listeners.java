package com.hockey.veo;

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
            if (Main.running
                &&
                p.getNearbyEntities(3, 3, 3).contains(Main.puck.getEntity())) {

                double d = Math.sqrt(

                    Math.pow(Main.puck.getLocation().getX() - Main.ps.get(p).getLocation().getX(), 2)
                    +
                    Math.pow(Main.puck.getLocation().getZ() - Main.ps.get(p).getLocation().getZ(), 2)

                );

                if (d < Main.pd.get(p)) {

                    double ratio = Math.abs(d - Main.pd.get(p)) / Main.pd.get(p) * 10,
                            theta = Math.atan2(
                                    (Main.puck.getLocation().getX() - p.getLocation().getX()),
                                    (Main.puck.getLocation().getZ() - p.getLocation().getZ())
                            );
                    Main.puck.shoot(Math.toDegrees(theta), ratio);
                    p.sendMessage("" + ratio);

                }

                Main.pd.put(p, d);

            }

        if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))
            if (p.getNearbyEntities(3, 3, 3).contains(Main.puck.getEntity())) {

            Main.puck.slow(85);
            e.setCancelled(true);

        }


    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        /*Player p = e.getPlayer();

        double theta = Math.atan2(
                (Main.puck.getLocation().getX() - p.getLocation().getX()),
                (Main.puck.getLocation().getZ() - p.getLocation().getZ())
        );
        p.sendMessage((Math.toDegrees(theta) + 180) + "");*/

    }

}
