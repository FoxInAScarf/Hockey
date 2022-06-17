package com.hockey.veo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class Puck {

    private double vx = 0, vz = 0;
    private final double sink = 0.82; // approximate height of a small armor stand
    private final ArmorStand stand;

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

                Location
                        xc = new Location(stand.getWorld(),
                                stand.getLocation().getX() + vx,
                                stand.getLocation().getY() + sink + 0.03,
                                stand.getLocation().getZ() + 0),
                        zc = new Location(stand.getWorld(),
                                stand.getLocation().getX() + 0,
                                stand.getLocation().getY() + sink + 0.03,
                                stand.getLocation().getZ() + vz);

                if (xc.getBlock().getType() != Material.AIR) vx *= -1;
                if (zc.getBlock().getType() != Material.AIR) vz *= -1;

                // check player collision

                for (Entity e : stand.getNearbyEntities(Math.abs(vx), 3, Math.abs(vz)))
                    if (e instanceof Player) {

                        Location pe0 = new Location(e.getWorld(),
                                        e.getLocation().getX() - 0.5,
                                        e.getLocation().getY() + 0,
                                        e.getLocation().getZ() - 0.5),
                                pe1 = new Location(e.getWorld(),
                                        e.getLocation().getX() + 0.5,
                                        e.getLocation().getY() + 0,
                                        e.getLocation().getZ() + 0.5);

                        // adding 0 to the y components makes intellij align all the parameters correctly
                        // it's only to make my OCD stop screaming at me

                        if (Main.isInRect(xc, pe0, pe1)) vx *= -1;
                        if (Main.isInRect(zc, pe0, pe1)) vz *= -1;

                    }

                // move

                stand.teleport(new Location(stand.getWorld(),
                        stand.getLocation().getX() + vx,
                        stand.getLocation().getY() + 0,
                        stand.getLocation().getZ() + vz));

                // friction

                slow(5);

            }

        }, 0L, 1L);

    }

    public void shoot(double theta, double v) {

        vx = Math.sin(Math.toRadians(theta)) * v;
        vz = Math.cos(Math.toRadians(theta)) * v;

        // division by 20 is necessary to convert from BPS (block per second) to BPT (block per tick)

    }

    public void slow(double p) {

        vx -= vx * p / 100;
        vz -= vz * p / 100;

    }

    public Location getLocation() { return stand.getLocation().add(0, sink, 0); }
    public Entity getEntity() { return stand; }

}
