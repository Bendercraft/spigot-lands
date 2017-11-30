package world.avatarhorizon.spigot.lands.models;

import org.bukkit.Chunk;

import java.util.*;

public class Land
{
    private UUID id;
    private String name;
    private String description;
    private List<Chunk> chunks;
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
        this.chunks = new LinkedList<>();
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

    public List<Chunk> getChunks()
    {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks)
    {
        this.chunks = chunks;
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
