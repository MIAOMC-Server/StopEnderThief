package com.miaomc.stopEnderThief.listener;

import com.miaomc.stopEnderThief.StopEnderThief;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class entitiesOnBlockChange implements Listener {
    @EventHandler
    public void onEndermanPickupBlock(org.bukkit.event.entity.EntityChangeBlockEvent event) {
        StopEnderThief plugin = StopEnderThief.getInstance();

        // 调试信息：记录所有实体方块变化事件
        plugin.debugLog("实体方块变化事件触发 - 实体类型: " + event.getEntityType() +
                ", 位置: " + event.getBlock().getLocation() +
                ", 方块类型: " + event.getBlock().getType());

        // 检查插件是否启用
        if (!plugin.isPluginEnabled()) {
            plugin.debugLog("插件已禁用，跳过处理");
            return;
        }

        if (event.getEntityType() == org.bukkit.entity.EntityType.ENDERMAN) {
            plugin.debugLog("检测到末影人尝试搬运方块，已阻止！");
            event.setCancelled(true);
        }
    }
}
