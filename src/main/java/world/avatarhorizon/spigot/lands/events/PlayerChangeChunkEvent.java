package world.avatarhorizon.spigot.lands.events;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChangeChunkEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Chunk fromChunk, toChunk;
    private Location fromLocation, toLocation;
    private boolean shouldCancelMove;

    public PlayerChangeChunkEvent(Player p, Chunk fc, Chunk tc, Location fl, Location tl)
    {
        this.player = p;
        this.fromChunk = fc;
        this.toChunk = tc;
        this.fromLocation = fl;
        this.toLocation = tl;
        this.shouldCancelMove = false;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Chunk getFromChunk()
    {
        return fromChunk;
    }

    public Chunk getToChunk()
    {
        return toChunk;
    }

    public Location getFromLocation()
    {
        return fromLocation;
    }

    public Location getToLocation()
    {
        return toLocation;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    @Override
    public boolean isCancelled()
    {
        return shouldCancelMove;
    }

    @Override
    public void setCancelled(boolean b)
    {
        this.shouldCancelMove = b;
    }
}
