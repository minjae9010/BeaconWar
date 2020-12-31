package tk.mjsv;

import ca.wacos.nametagedit.NametagAPI;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
    private static final String index = "§f[§bBeacon §cWars§f] ";
    public static main pl;

    public Events(main pl){
        this.pl=pl;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if(pl.getConfig().contains("Playerinfo."+p.getName())) {
            String team = pl.getConfig().getString("Playerinfo." + p.getName());
            switch (team) {
                case "red":
                    Commands.team1.addPlayer(p);
                    break;
                case "orange":
                    Commands.team2.addPlayer(p);
                    break;
                case "yellow":
                    Commands.team3.addPlayer(p);
                    break;
                case "green":
                    Commands.team4.addPlayer(p);
                    break;
            }
            NametagAPI.setPrefix(p.getName(),pl.getConfig().getString(team));
            p.setDisplayName(pl.getConfig().getString(team)+p.getName());
        }
        else {
            NametagAPI.setPrefix(p.getName(),"");
            p.setDisplayName(p.getName());
        }
        event.setJoinMessage(index+p.getDisplayName()+" 님이 접속하셨습니다");
    }
    @EventHandler
    public void onLeft(PlayerQuitEvent event){
        Player p = event.getPlayer();
        event.setQuitMessage(index+p.getDisplayName()+" 님이 나가셨습니다");

    }
    @EventHandler
    public void onDameger(EntityDamageByEntityEvent event){
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if(damager instanceof Arrow){
            CraftArrow arrow = (CraftArrow) damager;
            damager = (Entity) arrow.getShooter();
        }
        else if(damager instanceof Snowball){
            Snowball snowball = (Snowball) damager;
            damager = (Entity) snowball.getShooter();
        }
        if(damager == entity){
            event.setCancelled(false);
        }
        else if(damager instanceof Player & entity instanceof Player){
            if(Timer.setting.equals("평화")) {
                ((Player) damager).sendMessage(index + "평화시간에는 싸우실 수 없습니다");
                event.setCancelled(true);
            }
            else if(((Player) damager).getScoreboard().getTeams() == ((Player) entity).getScoreboard().getTeams()){
                ((Player) damager).sendMessage(index+"같은 팀끼리 싸울 수 없습니다.");
                event.setCancelled(true);
            }
        }

    }

}

