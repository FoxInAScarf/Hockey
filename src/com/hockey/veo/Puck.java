package com.hockey.veo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Puck {

    private double vx = 0, vz = 0;
    private final double df = 0.01;
    private final long f = 1L;
    private Endermite mite;

    public Puck(Location l) {

        mite = (Endermite) l.getWorld().spawnEntity(l, EntityType.ENDERMITE);
        mite.setInvulnerable(true);
        mite.setSilent(true);
        mite.setAI(false);
        mite.setCollidable(false);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {

            if (vx != 0 || vz != 0) {

                // check block collision

                Location l0 = mite.getLocation().add(Math.abs(vx) * f / 20, 0, 0),
                        l1 = mite.getLocation().subtract(Math.abs(vx) * f / 20, 0, 0),
                        l2 = mite.getLocation().add(0, 0, Math.abs(vz) * f / 20),
                        l3 = mite.getLocation().subtract(0, 0, Math.abs(vz) * f / 20);

                if (l0.getBlock().getType() != Material.AIR
                        ||
                        l1.getBlock().getType() != Material.AIR) vx *= (-1);
                if (l2.getBlock().getType() != Material.AIR
                        ||
                        l3.getBlock().getType() != Material.AIR) vz *= (-1);

                // check player collision

                for (Entity e : mite.getNearbyEntities(vx * f / 20, 1, vz * f / 20))
                    if (e instanceof Player) {

                        Location pe0 = new Location(e.getWorld(),
                                e.getLocation().getX() - 0.5,
                                e.getLocation().getY(),
                                e.getLocation().getZ() - 0.5),
                                pe1 = new Location(e.getWorld(),
                                        e.getLocation().getX() + 0.5,
                                        e.getLocation().getY(),
                                        e.getLocation().getZ() + 0.5);
                        if (Main.isInRect(l0, pe0, pe1) || Main.isInRect(l1, pe0, pe1)) {
                            vx *= (-1);
                        }
                        if (Main.isInRect(l2, pe0, pe1) || Main.isInRect(l3, pe0, pe1)) {
                            vz *= (-1);
                        }

                    }

                // move

                mite.setTicksLived(1);
                mite.teleport(new Location(mite.getWorld(),
                        mite.getLocation().getX() + vx * f / 20,
                        mite.getLocation().getY(),
                        mite.getLocation().getZ() + vz * f / 20));

                // friction

                vx -= (vx >= 0 ? df : -df);
                vz -= (vz >= 0 ? df : -df);

            }

        }, 0L, f);

    }

    public void shoot(double theta, double v) {

        vx = Math.sin(Math.toRadians(theta)) * v;
        vz = Math.cos(Math.toRadians(theta)) * v;

    }

}
