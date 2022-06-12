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

        for (Player p : Bukkit.getOnlinePlayers()) Main.pp.put(p, 90.0F);
        for (Player p : Bukkit.getOnlinePlayers()) Main.pl.put(p, p.getLocation());

        return false;

    }

}
