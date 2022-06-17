package com.hockey.veo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class Puck {

    private double vx = 0, vz = 0;
    private final double df = 0.98, sink = 0.82;
    private final long f = 1L;
    private ArmorStand stand;

    public Puck(Location l) {

        stand = (ArmorStand) l.getWorld().spawnEntity(l.subtract(0, sink + 0.02, 0), EntityType.ARMOR_STAND);
        stand.setMarker(true);
        stand.setGravity(false);
        stand.setInvisible(true);
        stand.setSmall(true);
        stand.getEquipment().setHelmet(new ItemStack(Material.COAL_BLOCK));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {

            if (vx != 0 || vz != 0) {

                // check block collision

                Location l0 = stand.getLocation().add(Math.abs(vx) * f / 20, sink + 0.03, 0),
                        l1 = stand.getLocation().subtract(Math.abs(vx) * f / 20, 0, 0).add(0, sink + 0.03, 0),
                        l2 = stand.getLocation().add(0, sink + 0.03, Math.abs(vz) * f / 20),
                        l3 = stand.getLocation().subtract(0, 0, Math.abs(vz) * f / 20).add(0, sink + 0.03, 0);

                if (l0.getBlock().getType() != Material.AIR
                        ||
                        l1.getBlock().getType() != Material.AIR) vx *= (-1);
                if (l2.getBlock().getType() != Material.AIR
                        ||
                        l3.getBlock().getType() != Material.AIR) vz *= (-1);

                // check player collision

                for (Entity e : stand.getNearbyEntities(vx * f / 20, 3, vz * f / 20))
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

                stand.teleport(new Location(stand.getWorld(),
                        stand.getLocation().getX() + vx * f / 20,
                        stand.getLocation().getY(),
                        stand.getLocation().getZ() + vz * f / 20));

                // friction

                /*vx = (vx >= 0 ? (Math.abs(vx) * df) : -(Math.abs(vx) * df));
                vz = (vz >= 0 ? (Math.abs(vz) * df) : -(Math.abs(vz) * df));*/

                slow(3);

            }

        }, 0L, f);

    }

    public void shoot(double theta, double v) {

        vx = Math.sin(Math.toRadians(theta)) * v;
        vz = Math.cos(Math.toRadians(theta)) * v;

    }

    public void slow(double p) {

        vx -= vx * p / 100;
        vz -= vz * p / 100;

    }

    public Location getLocation() { return stand.getLocation().add(0, sink, 0); }
    public Entity getEntity() { return stand; }

}
