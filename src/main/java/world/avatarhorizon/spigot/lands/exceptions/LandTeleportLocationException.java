package world.avatarhorizon.spigot.lands.exceptions;

public class LandTeleportLocationException extends LandManagementException
{
    public static final String CAUSE_NULL_WORLD = "error.setteleport.null_world";
    public static final String CAUSE_NULL_NAME = "error.setteleport.null_name";
    public static final String CAUSE_NO_LAND = "error.setteleport.no_land";

    public LandTeleportLocationException(String causeKey)
    {
        super(causeKey);
    }
}
