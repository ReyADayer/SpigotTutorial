package me.reyad.SpigotTutorial;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    public PlayerListener(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        StatusRecord statusRecord = new StatusRecord();
        Status status = statusRecord.getStatus(player);
        player.sendMessage(ChatColor.GOLD + status.name + "さん(" + status.level + "Lv)がログインしました");
    }
}
