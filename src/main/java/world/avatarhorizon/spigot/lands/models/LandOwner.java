package world.avatarhorizon.spigot.lands.models;

import java.util.List;

/**
 * Base class to help overriding ILandOwner more easily
 * @param <T> The Type of Owner
 */
public abstract class LandOwner<T> implements ILandOwner
{
    protected T owner;
    protected List<Land> ownedLands;

    @Override
    public List<Land> getLands()
    {
        return ownedLands;
    }
}
