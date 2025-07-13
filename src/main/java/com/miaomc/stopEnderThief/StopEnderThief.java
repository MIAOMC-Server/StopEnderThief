package com.miaomc.stopEnderThief;

import com.miaomc.stopEnderThief.command.commands;
import com.miaomc.stopEnderThief.listener.entitiesOnBlockChange;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class StopEnderThief extends JavaPlugin {

    private static StopEnderThief instance;
    private boolean pluginEnabled;

    @Override
    public void onEnable() {
        instance = this;

        // 保存默认配置文件
        saveDefaultConfig();

        // 加载配置
        loadConfig();

        // 调试信息：插件启动
        debugLog("插件正在启动...");
        debugLog("配置已加载 - 插件启用状态: " + pluginEnabled + ", 调试模式: " + isDebugMode());

        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new entitiesOnBlockChange(), this);
        debugLog("事件监听器已注册");

        // 注册命令
        getCommand("enderthief").setExecutor(new commands());
        debugLog("命令处理器已注册");

        getLogger().info("StopEnderThief 插件已启用！");
        debugLog("插件启动完成");
    }

    @Override
    public void onDisable() {
        getLogger().info("StopEnderThief 插件已禁用！");
    }

    public static StopEnderThief getInstance() {
        return instance;
    }

    public void loadConfig() {
        reloadConfig();
        pluginEnabled = getConfig().getBoolean("enabled", true);
    }

    public boolean isPluginEnabled() {
        return pluginEnabled;
    }

    public void setPluginEnabled(boolean enabled) {
        this.pluginEnabled = enabled;
        getConfig().set("enabled", enabled);
        saveConfig();
    }

    public boolean isDebugMode() {
        return getConfig().getBoolean("debug", false);
    }

    public void debugLog(String message) {
        if (isDebugMode()) {
            getLogger().info("[DEBUG] " + message);
        }
    }

    public String getMessage(String key) {
        String message = getConfig().getString("messages." + key);
        return message != null ? ChatColor.translateAlternateColorCodes('&', message) : "";
    }
}
