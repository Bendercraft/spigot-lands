package world.avatarhorizon.spigot.lands.models;

import org.bukkit.Chunk;
import org.bukkit.World;

/**
 * Class to avoid loading a whole chunk's data when not necessary for players
 */
public class ChunkLocation
{
    private int x;
    private int y;

    public Chunk getChunk(World world)
    {
        return world.getChunkAt(x, y);
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChunkLocation that = (ChunkLocation) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode()
    {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
