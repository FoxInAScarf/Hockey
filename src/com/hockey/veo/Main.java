package com.hockey.veo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public boolean running = false;
    private static Main main;

    public void onEnable() {

        System.out.println("\n");
        System.out.println("[HockeyPlugin]: ||\\      //|");
        System.out.println("[HockeyPlugin]: || \\    // |");
        System.out.println("[HockeyPlugin]: ||  \\  //  |");
        System.out.println("[HockeyPlugin]: ||   \\//   |");
        System.out.println("[HockeyPlugin]: ||         |");
        System.out.println("[HockeyPlugin]: \\\\ \\     / /");
        System.out.println("[HockeyPlugin]:  \\\\ \\   / /  Veo.");
        System.out.println("[HockeyPlugin]:   \\\\_____/");
        System.out.println("\n");

        System.out.println("[HockeyPlugin]: Loading...");

        main = this;
        this.getCommand("hockey").setExecutor(new Hockey());
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);

        System.out.println("[HockeyPlugin]: Done!");

    }

    public static Main getInstance() { return main; }

    public static boolean isInRect(Location p, Location e0, Location e1) {

        // debug this

        if (p.getX() >= e0.getX() && p.getX() <= e1.getX())
            if (p.getZ() >= e0.getZ() && p.getZ() <= e1.getZ()) return true;

        return false;

    }

}
