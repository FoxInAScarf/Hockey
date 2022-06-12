package com.hockey.veo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Hockey implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // summon puck

        Main.puck = new Puck(((Player) sender).getLocation());
        Main.running = true;

        // start

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {

            for (Player p : Bukkit.getOnlinePlayers()) {

                double yaw = Math.toRadians(p.getLocation().getYaw() + 90);
                Location l = new Location(p.getWorld(),
                        p.getLocation().getX() + Math.cos(yaw) * 4,
                        p.getLocation().getY(),
                        p.getLocation().getZ() + Math.sin(yaw) * 4);

                if (Main.ps.containsKey(p)) Main.ps.get(p).remove();
                ArmorStand s = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
                s.setMarker(true);
                s.setGravity(false);
                s.setInvulnerable(true);

                Main.ps.put(p, s);

            }

        }, 0L, 1L);



        return false;

    }

}
