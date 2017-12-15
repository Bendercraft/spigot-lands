package world.avatarhorizon.spigot.lands.controllers;

import org.bukkit.Location;
import org.bukkit.World;
import world.avatarhorizon.spigot.lands.exceptions.*;
import world.avatarhorizon.spigot.lands.models.ChunkLocation;
import world.avatarhorizon.spigot.lands.models.Land;
import world.avatarhorizon.spigot.lands.persistence.ILandPersister;

import java.util.*;
import java.util.logging.Logger;

public final class LandsManager
{
    private final Logger logger;
    private final ILandPersister landPersister;
    private Map<World, Map<UUID, Land>> lands;

    public LandsManager(Logger logger, ILandPersister landPersister)
    {
        this.logger = logger;
        this.landPersister = landPersister;

        this.loadDataFromFile();
    }

    private void loadDataFromFile()
    {
        this.lands = landPersister.loadAll();
    }

    /**
     * Get a Map containing all the lands of that world
     * @param world The world from which you want the lands
     * @return A unmodifiable map where the key is the name of the land and the value is the Land or null if that world does not have any land.
     */
    public Map<UUID, Land> getLandsForWorld(World world)
    {
        Map<UUID, Land> worldLands = lands.get(world);
        if (worldLands == null || worldLands.isEmpty())
        {
            return null;
        }
        return Collections.unmodifiableMap(worldLands);
    }

    /**
     * Get a land with the given name in the given world.
     * @param world The world in which lies the Land
     * @param name The name of the Land
     * @return a Land if one was found. Null otherwise.
     */
    public Land getLand(World world, String name)
    {
        Map<UUID, Land> worldLands = lands.get(world);
        if (worldLands == null || worldLands.isEmpty())
        {
            return null;
        }

        Land land = getLand(worldLands, name);
        if (land != null) return land;
        return null;
    }

    private Land getLand(Map<UUID, Land> worldLands, String name)
    {
        name = name.toLowerCase();
        for (Land land : worldLands.values())
        {
            if (land.getName().toLowerCase().equals(name))
            {
                return land;
            }
        }
        return null;
    }

    /**
     * Get the land according to the given id in the given world
     * @param world The world in which to look up
     * @param id The id matching the land
     * @return a Land if one was found. null otherwhise.
     */
    public Land getLand(World world, UUID id)
    {
        Map<UUID, Land> worldLands = lands.get(world);
        if (worldLands == null || worldLands.isEmpty())
        {
            return null;
        }
        return worldLands.get(id);
    }

    /**
     * Add a land to a world
     * @param world The world in which you want to add the land
     * @param land The land you want to add in the world
     */
    public void addLandToWorld(World world, Land land) throws LandManagementException
    {
        if (world == null)
        {
            throw new LandManagementException(ExceptionCause.NULL_WORLD);
        }
        if (land == null)
        {
            throw new LandManagementException(ExceptionCause.NULL_LAND);
        }
        if (land.getName() == null || land.getName().trim().equals(""))
        {
            throw new LandManagementException(ExceptionCause.NULL_NAME);
        }

        Map<UUID, Land> worldLands = lands.computeIfAbsent(world, k -> new HashMap<>());

        if (getLand(worldLands, land.getName()) != null)
        {
            throw new LandManagementException(ExceptionCause.NAME_USED);
        }

        worldLands.put(land.getId(), land);
        landPersister.save(world, land);
    }

    /**
     * Rename a land in a world
     * @param world The world in which the land we want to edit lies
     * @param oldName The name of the land we want to edit
     * @param newName The new name we want to set to the land
     */
    public void renameLandInWorld(World world, String oldName, String newName) throws LandManagementException
    {
        if (world == null)
        {
            throw new LandManagementException(ExceptionCause.NULL_WORLD);
        }
        if (oldName == null || oldName.trim().equals(""))
        {
            throw new LandManagementException(ExceptionCause.NULL_OLD_NAME);
        }
        if (newName == null || newName.trim().equals(""))
        {
            throw new LandManagementException(ExceptionCause.NULL_NEW_NAME);
        }

        Map<UUID, Land> worldLands = lands.get(world);
        if (worldLands == null || worldLands.isEmpty())
        {
            throw new LandManagementException(ExceptionCause.NO_LAND);
        }

        Land land = getLand(worldLands, oldName);
        if (land == null)
        {
            throw new LandManagementException(ExceptionCause.NO_LAND);
        }

        Land other = getLand(worldLands, newName);
        if (other != null && other != land)
        {
            throw new LandManagementException(ExceptionCause.NAME_USED);
        }

        land.setName(newName);
        landPersister.save(world, land);
    }

    /**
     * Define the description of a Land in a world
     * @param world The world in which the Land lies
     * @param name The name of the Land
     * @param description The description to set
     */
    public void setLandDescription(World world, String name, String description) throws LandManagementException
    {
        if (world == null)
        {
            throw new LandManagementException(ExceptionCause.NULL_WORLD);
        }
        if (name == null || name.trim().equals(""))
        {
            throw new LandManagementException(ExceptionCause.NULL_NAME);
        }

        //No need to check if description is empty or not. Optional attribute
        Land land = getLand(world, name);
        if (land == null)
        {
            throw new LandManagementException(ExceptionCause.NO_LAND);
        }

        land.setDescription(description);
        landPersister.save(world, land);
    }

    /**
     * Add the chunks in the Land and save its file
     * @param land The Land in which you want to add the chunks
     * @param chunks The Chunks you want to add in the Land.
     */
    public void addChunksToLand(World world, Land land, Set<ChunkLocation> chunks)
    {
        if (land != null && chunks != null)
        {
            land.addChunks(chunks);
            landPersister.save(world, land);
        }
        else
        {
            logger.warning("Tried to pass a null land or null chunks in addChunksToLands in LandsManager");
        }
    }

    /**
     * Remove the chunks in the Land and save its file
     * @param land The Land in which you want to add the chunks
     * @param chunks The Chunks you want to add in the Land.
     */
    public void removeChunksFromLand(World world, Land land, Set<ChunkLocation> chunks)
    {
        if (world != null && land != null && chunks != null)
        {
            land.removeChunks(chunks);
            landPersister.save(world, land);
        }
        else
        {
            logger.warning("Tried to pass a null land or null chunks in removeChunksToLands in LandsManager");
        }
    }

    /**
     * Delete a land
     * @param world The world in which lies the land
     * @param name The name of the land to delete
     */
    public void deleteLand(World world, String name) throws LandManagementException
    {
        if (world == null)
        {
            throw new LandManagementException(ExceptionCause.NULL_WORLD);
        }
        if (name == null || name.trim().equals(""))
        {
            throw new LandManagementException(ExceptionCause.NULL_NAME);
        }

        Map<UUID, Land> worldLands = lands.get(world);
        if (worldLands == null || worldLands.isEmpty())
        {
            throw new LandManagementException(ExceptionCause.NO_LAND);
        }

        Land land = getLand(worldLands, name);
        if (land == null)
        {
            throw new LandManagementException(ExceptionCause.NO_LAND);
        }

        landPersister.delete(world, land);
        worldLands.remove(land.getId());
    }

    /**
     *
     * @param world
     * @param name
     * @param loc
     */
    public void setLandTeleportLocation(World world, String name, Location loc) throws LandManagementException
    {
        if (world == null)
        {
            throw new LandManagementException(ExceptionCause.NULL_WORLD);
        }
        if (name == null || name.trim().equals(""))
        {
            throw new LandManagementException(ExceptionCause.NULL_NAME);
        }

        Land land = getLand(world, name);
        if (land == null)
        {
            throw new LandManagementException(ExceptionCause.NO_LAND);
        }

        land.setTeleportLocation(loc);
        landPersister.save(world, land);
    }
}
