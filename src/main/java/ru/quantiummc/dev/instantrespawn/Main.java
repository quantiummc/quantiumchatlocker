package ru.quantiummc.dev.instantrespawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class Main extends JavaPlugin implements Listener, CommandExecutor {

    private String respawnMessage;
    private String chatMessage;
    private String titleMessage;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("irespawn")).setExecutor(this);
        getLogger().info("InstantRespawn enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("InstantRespawn disabled!");
    }

    private void loadConfig() {
        respawnMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("respawn-message", "&aВоскресье!"));
        chatMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("chat-message", "&aИгрок {player} воскрес!"));
        titleMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("title-message", "&aВоскресье!"));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
                player.sendTitle(titleMessage, "", 10, 70, 20);
                Bukkit.broadcastMessage(chatMessage.replace("{player}", player.getName()));
            }
        }.runTaskLater(this, 1L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("irespawn")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Используйте: /irespawn <reload|info>");
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "reload":
                    reloadConfig();
                    loadConfig();
                    sender.sendMessage(ChatColor.GREEN + "Конфиг перезагружен!");
                    break;
                case "info":
                    sender.sendMessage(ChatColor.GREEN + "InstantRespawn v1.0");
                    sender.sendMessage(ChatColor.GREEN + "Автор: QuantiumMC");
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Неизвестная команда. Используйте: /irespawn <reload|info>");
                    break;
            }
            return true;
        }
        return false;
    }
}
