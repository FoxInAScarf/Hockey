package com.hockey.veo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hockey implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // summon puck

        Main.puck = new Puck(((Player) sender).getLocation());
        Main.running = true;

        // start

        for (Player p : Bukkit.getOnlinePlayers()) Main.pp.put(p, 90.0F);


        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {

            for (Player p : Bukkit.getOnlinePlayers()) Main.pl.put(p, p.getLocation());

            /*if (!Main.pa.isEmpty()) {

                Iterator i = Main.pa.entrySet().iterator();
                while (i.hasNext()) {

                    Map.Entry pair = (Map.Entry) i.next();
                    for (ZParticle p : pair.getValue()) p.

                }

            }*/
            /*for (Player p : Bukkit.getOnlinePlayers()) {

                //List<ZParticle> ps = new ArrayList<>();
                double theta = Math.atan2(
                        (Main.puck.getLocation().getX() - p.getLocation().getX()),
                        (Main.puck.getLocation().getZ() - p.getLocation().getZ())
                );
                for (double i = 0; i <= 1; i += 0.1) {

                    Location l = new Location(p.getWorld(),
                            Main.puck.getLocation().getX() + Math.sin(theta) * i,
                            Main.puck.getLocation().getY() + 0.3,
                            Main.puck.getLocation().getZ() + Math.cos(theta) * i);
                    new ZParticle(Particles.FLAME, l, 1, new double[]{0.01, 0.01, 0.01}).playParticle(false, p);

                }

            }*/

        }, 0L, 1L);

        return false;

    }

}
