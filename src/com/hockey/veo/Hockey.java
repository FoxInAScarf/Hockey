package com.hockey.veo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Hockey implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // summon puck

        Main.puck = new Puck(((Player) sender).getLocation());
        Main.running = true;

        // start

        for (Player p : Bukkit.getOnlinePlayers()) {

            double yaw = Math.toRadians(p.getLocation().getYaw() + 90);
            Location l = new Location(p.getWorld(),
                    p.getLocation().getX() + Math.cos(yaw) * 1,
                    p.getLocation().getY(),
                    p.getLocation().getZ() + Math.sin(yaw) * 1);

            ArmorStand s = (ArmorStand) p.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
            s.setMarker(true);
            s.setGravity(false);
            s.setInvulnerable(true);
            s.setInvisible(true);

            Main.ps.put(p, s);
            Main.pl.put(p, p.getLocation());
            Main.pd.put(p, 0.0);

        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {

            for (Player p : Bukkit.getOnlinePlayers()) {

                if (Main.pl.get(p).equals(p.getLocation())) continue;

                double yaw = Math.toRadians(p.getLocation().getYaw() + 90);
                Location l = new Location(p.getWorld(),
                        p.getLocation().getX() + Math.cos(yaw) * 1,
                        p.getLocation().getY(),
                        p.getLocation().getZ() + Math.sin(yaw) * 1);

                ArmorStand s = Main.ps.get(p);
                s.teleport(l);
                //if (Main.ps.containsKey(p)) Main.ps.get(p).remove();

                Main.ps.put(p, s);
                Main.pl.put(p, p.getLocation());

            }

        }, 0L, 1L);



        return false;

    }

}
