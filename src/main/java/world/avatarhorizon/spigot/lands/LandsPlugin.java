package world.avatarhorizon.spigot.lands;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import world.avatarhorizon.spigot.lands.commands.LandCommandExecutor;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;

import java.util.logging.Logger;

public class LandsPlugin extends JavaPlugin
{
    private Logger logger;
    private LandsManager landsManager;

    @Override
    public void onEnable()
    {
        logger = getLogger();
        WorldEditPlugin worldEditPlugin = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldEditPlugin == null)
        {
            logger.severe("Was not able to load WorldEdit plugin");
        }
        else
        {
            landsManager = new LandsManager();
            LandCommandExecutor commandExecutor = new LandCommandExecutor(logger, landsManager, worldEditPlugin);

            getCommand("lands").setExecutor(commandExecutor);
            getServer().getServicesManager().register(LandsManager.class, landsManager, this, ServicePriority.Normal);
        }
    }

    @Override
    public void onDisable()
    {

    }
}
