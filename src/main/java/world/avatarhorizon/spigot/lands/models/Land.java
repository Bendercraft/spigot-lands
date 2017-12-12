package world.avatarhorizon.spigot.lands.models;

import net.minecraft.server.v1_12_R1.Chunk;

import java.util.*;

public class Land
{
    private UUID id;
    private String name;
    private String description;
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

    public String getDescription()
    {
        return description;
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
