package world.avatarhorizon.spigot.lands.exceptions;

public class LandCreationException extends LandManagementException
{
    public static final String CAUSE_NULL_WORLD = "error.creation.null_world";
    public static final String CAUSE_NULL_LAND = "error.creation.null_land";
    public static final String CAUSE_LAND_NO_NAME = "error.creation.land_no_name";
    public static final String CAUSE_LAND_NAME_USED = "error.creation.land_name_used";

    public LandCreationException(String causeKey)
    {
        super(causeKey);
    }
}
