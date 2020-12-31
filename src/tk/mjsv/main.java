package tk.mjsv;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class main extends JavaPlugin {
    PluginDescriptionFile pdf = this.getDescription();
    @Override
    public void onEnable() {
        getLogger().info("비콘 워즈 시스템 활성화");
        pdf.getCommands().keySet().forEach($->{
            getCommand($).setExecutor(new Commands(this));
            getCommand($).setTabCompleter(new Commands(this));
        });
        Bukkit.getPluginManager().registerEvents(new Events(this),this);
        File file = new File(getDataFolder(),"config.yml");
        if(file.length()==0){
            getConfig().set("red","§f[§c빨강팀§f]");
            getConfig().set("orange","§f[§6주황팀§f]");
            getConfig().set("yellow","§f[§e노랑팀§f]");
            getConfig().set("green","§f[§a초록팀§f]");
            getConfig().set("Playerinfo","");
            saveConfig();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("비콘 워즈 시스템 비활성화");
    }
}
