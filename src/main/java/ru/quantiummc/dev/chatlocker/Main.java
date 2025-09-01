package ru.quantiummc.dev.chatlocker;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.ChatColor;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ChatLocker включен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatLocker выключен!");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cВы не можете отправлять сообщения в лобби."));
    }
}
