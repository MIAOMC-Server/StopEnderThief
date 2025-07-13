package com.miaomc.stopEnderThief.command;

import com.miaomc.stopEnderThief.StopEnderThief;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class commands implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("stopenderthief.admin")) {
            sender.sendMessage(StopEnderThief.getInstance().getMessage("no-permission"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§6[StopEnderThief] 用法: /enderthief <reload|enable|disable|debug>");
            return true;
        }

        StopEnderThief plugin = StopEnderThief.getInstance();

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.loadConfig();
                sender.sendMessage(plugin.getMessage("config-reloaded"));
                break;

            case "enable":
                plugin.setPluginEnabled(true);
                sender.sendMessage(plugin.getMessage("plugin-enabled"));
                break;

            case "disable":
                plugin.setPluginEnabled(false);
                sender.sendMessage(plugin.getMessage("plugin-disabled"));
                break;

            case "debug":
                boolean currentDebug = plugin.getConfig().getBoolean("debug", false);
                plugin.getConfig().set("debug", !currentDebug);
                plugin.saveConfig();

                if (!currentDebug) {
                    sender.sendMessage(plugin.getMessage("debug-enabled"));
                    plugin.debugLog("调试模式已被 " + sender.getName() + " 启用");
                } else {
                    sender.sendMessage(plugin.getMessage("debug-disabled"));
                }
                break;

            default:
                sender.sendMessage("§c未知的子命令。用法: /enderthief <reload|enable|disable|debug>");
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            List<String> subCommands = Arrays.asList("reload", "enable", "disable", "debug");

            for (String subCommand : subCommands) {
                if (subCommand.toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(subCommand);
                }
            }
            return completions;
        }
        return new ArrayList<>();
    }
}
