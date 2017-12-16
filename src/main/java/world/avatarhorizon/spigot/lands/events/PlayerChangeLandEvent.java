package world.avatarhorizon.spigot.lands.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import world.avatarhorizon.spigot.lands.models.Land;

public class PlayerChangeLandEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Land fromLand, toLand;
    private boolean cancelled;

    public PlayerChangeLandEvent(Player p, Land fl, Land tl)
    {
        this.player = p;
        this.fromLand = fl;
        this.toLand = tl;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Land getFromLand()
    {
        return fromLand;
    }

    public Land getToLand()
    {
        return toLand;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled()
    {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b)
    {
        this.cancelled = b;
    }
}
