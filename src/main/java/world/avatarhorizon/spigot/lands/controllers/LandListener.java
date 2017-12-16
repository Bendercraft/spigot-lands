package world.avatarhorizon.spigot.lands.controllers;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import world.avatarhorizon.spigot.lands.events.PlayerChangeChunkEvent;
import world.avatarhorizon.spigot.lands.events.PlayerChangeLandEvent;
import world.avatarhorizon.spigot.lands.models.Land;
import world.avatarhorizon.spigot.lands.models.LandPlayer;

import java.util.logging.Logger;

public class LandListener implements Listener
{
    private Logger logger;

    private LandsManager landsManager;

    public LandListener(LandsManager landsManager, Logger logger)
    {
        this.landsManager = landsManager;
        this.logger = logger;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Chunk oldC = event.getFrom().getChunk();
        Chunk newC = event.getTo().getChunk();
        if (!oldC.equals(newC))
        {
            PlayerChangeChunkEvent chunkEvent = new PlayerChangeChunkEvent(event.getPlayer(), oldC, newC, event.getFrom(), event.getTo());
            Bukkit.getServer().getPluginManager().callEvent(chunkEvent);
            if (chunkEvent.isCancelled())
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChangeChunk(PlayerChangeChunkEvent event)
    {
        Player player = event.getPlayer();

        LandPlayer landPlayer = landsManager.getLandPlayer(player);
        Land fromLand = landPlayer.getCurrentLand();

        Chunk toChunk = event.getToChunk();
        Land toLand = landsManager.getLand(toChunk);

        if (toLand != fromLand)
        {
            PlayerChangeLandEvent landEvent = new PlayerChangeLandEvent(player, fromLand, toLand);
            Bukkit.getServer().getPluginManager().callEvent(landEvent);

            if (landEvent.isCancelled())
            {
                event.setCancelled(true);
            }
            else
            {
                landPlayer.setCurrentLand(toLand);
                if (toLand != null)
                {
                    player.sendTitle(toLand.getName(), toLand.getDescription(), 10, 60, 20);
                }
            }
        }
    }
}
