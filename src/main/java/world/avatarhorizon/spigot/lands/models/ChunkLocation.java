package world.avatarhorizon.spigot.lands.models;

import org.bukkit.Chunk;
import org.bukkit.World;

/**
 * Class to avoid loading a whole chunk's data when not necessary for players
 */
public class ChunkLocation
{
    private World world;
    private int x;
    private int y;

    public Chunk getChunk()
    {
        return world.getChunkAt(x, y);
    }

    public World getWorld()
    {
        return world;
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}
