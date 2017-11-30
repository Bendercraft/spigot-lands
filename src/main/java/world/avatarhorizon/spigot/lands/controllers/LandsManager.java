package world.avatarhorizon.spigot.lands.controllers;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.World;
import world.avatarhorizon.spigot.lands.exceptions.LandCreationException;
import world.avatarhorizon.spigot.lands.models.Land;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class LandsManager
{

    private WorldEditPlugin worldEditPlugin;
    private Map<World, Map<String, Land>> lands;

    public LandsManager(WorldEditPlugin worldEditPlugin)
    {
        this.worldEditPlugin = worldEditPlugin;
        lands = new HashMap<>();
    }

    /**
     * Get a Map containing all the lands of that world
     * @param world The world from which you want the lands
     * @return A unmodifiable map where the key is the name of the land and the value is the Land or null if that world does not have any land.
     */
    public Map<String, Land> getLandsForWorld(World world)
    {
        Map<String, Land> worldLands = lands.get(world);
        if (worldLands == null || worldLands.isEmpty())
        {
            return null;
        }
        return Collections.unmodifiableMap(worldLands);
    }

    /**
     * Add a land to a world
     * @param world The world in which you want to add the land
     * @param land The land you want to add in the world
     * @throws LandCreationException if was not able to add the land for any reason.
     */
    public void addLandToWorld(World world, Land land) throws LandCreationException
    {
        if (world == null)
        {
            throw new LandCreationException(LandCreationException.CAUSE_NULL_WORLD);
        }
        if (land == null)
        {
            throw new LandCreationException(LandCreationException.CAUSE_NULL_LAND);
        }
        if (land.getName() == null)
        {
            throw new LandCreationException(LandCreationException.CAUSE_LAND_NO_NAME);
        }

        Map<String, Land> worldLands = lands.get(world);
        if (worldLands == null)
        {
            worldLands = new HashMap<>();
            lands.put(world, worldLands);
        }

        if (worldLands.containsKey(land.getName()))
        {
            throw new LandCreationException(LandCreationException.CAUSE_LAND_NAME_USED);
        }

        worldLands.put(land.getName(), land);
    }
}
