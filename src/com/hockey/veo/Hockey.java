package com.hockey.veo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hockey implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Puck p = new Puck(((Player) sender).getLocation());
        p.shoot(45, 3);
        System.out.println(Math.sin(Math.toRadians(45)));

        return false;

    }

}
