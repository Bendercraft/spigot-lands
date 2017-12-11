package world.avatarhorizon.spigot.lands.exceptions;

public class LandRenameException extends LandManagementException
{
    public static final String CAUSE_NULL_WORLD = "error.rename.invalid_word";
    public static final String CAUSE_NULL_OLD_NAME = "error.rename.invalid_old_name";
    public static final String CAUSE_NULL_NEW_NAME = "error.rename.invalid_new_name";
    public static final String CAUSE_NO_LAND = "error.rename.no_land";
    public static final String CAUSE_LAND_NAME_USED = "error.rename.land_name_used";

    public LandRenameException(String causeKey)
    {
        super(causeKey);
    }
}