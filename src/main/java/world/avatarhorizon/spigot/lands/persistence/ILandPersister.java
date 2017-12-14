package world.avatarhorizon.spigot.lands.persistence;

import org.bukkit.World;
import world.avatarhorizon.spigot.lands.models.Land;

import java.util.Map;
import java.util.UUID;

public interface ILandPersister
{
    public boolean save(World world, Land land);
    public Map<World, Map<UUID, Land>> loadAll();
    public void delete(World world, Land land);
}
