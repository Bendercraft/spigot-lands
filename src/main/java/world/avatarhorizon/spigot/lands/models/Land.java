package world.avatarhorizon.spigot.lands.models;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.*;

public class Land
{
    private UUID id;
    private String name;
    private World world;
    private String description;
    private Location teleportLocation;
    private Set<ChunkLocation> chunks;
    private ILandOwner owner;

    private Map<String, String> attributes;

    public Land(UUID id)
    {
        this.id = id;
        initializeStructures();
    }

    public Land()
    {
        this.id = UUID.randomUUID();
        initializeStructures();
    }

    private void initializeStructures()
    {
        this.chunks = new HashSet<>();
        this.attributes = new HashMap<>();
    }

    //region Getters and Setters

    public UUID getId()
    {
        return this.id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public World getWorld()
    {
        return world;
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    public String getDescription()
    {
        return description;
    }

    public Location getTeleportLocation()
    {
        return teleportLocation;
    }

    public void setTeleportLocation(Location teleportLocation)
    {
        this.teleportLocation = teleportLocation;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void addChunks(Set<ChunkLocation> chunks)
    {
        this.chunks.addAll(chunks);
    }

    public void removeChunks(Set<ChunkLocation> chunks)
    {
        this.chunks.removeAll(chunks);
    }

    public Set<ChunkLocation> getChunks()
    {
        return Collections.unmodifiableSet(chunks);
    }

    public ILandOwner getOwner()
    {
        return owner;
    }

    public void setOwner(ILandOwner owner)
    {
        this.owner = owner;
    }

    //endregion
}
