package me.reyad.SpigotTutorial;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private PlayerListener playerListener;

    @Override
    public void onEnable() {
        playerListener = new PlayerListener(this);
    }

    @Override
    public void onDisable() {
    }


}