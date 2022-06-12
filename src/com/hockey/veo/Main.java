package com.hockey.veo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    public static boolean running = false;
    public static Puck puck;
    public static HashMap<Player, Location> pl = new HashMap<>();
    public static HashMap<Player, Float> pp = new HashMap<>();
    public static HashMap<Player, List<ZParticle>> pa = new HashMap<>();

    public static ZParticle test;
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

        if (p.getX() >= e0.getX() && p.getX() <= e1.getX())
            return p.getZ() >= e0.getZ() && p.getZ() <= e1.getZ();

        return false;

    }

}
