package world.avatarhorizon.spigot.lands.persistence.serializers;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Type;

public class LocationSerializer implements JsonSerializer<Location>, JsonDeserializer<Location>
{
    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = (JsonObject) jsonElement;
        String name = root.get("world").getAsString();
        World world = Bukkit.getWorld(name);
        int x = root.get("x").getAsInt();
        int y = root.get("y").getAsInt();
        int z = root.get("z").getAsInt();

        return new Location(world, x, y, z);
    }

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();
        root.addProperty("world", location.getWorld().getName());
        root.addProperty("x", location.getBlockX());
        root.addProperty("y", location.getBlockY());
        root.addProperty("z", location.getBlockZ());
        return root;
    }
}
