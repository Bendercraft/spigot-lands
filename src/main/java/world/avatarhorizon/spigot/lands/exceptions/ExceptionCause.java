package world.avatarhorizon.spigot.lands.exceptions;

public enum ExceptionCause
{
    NAME_USED("error.name_already_used"),
    NULL_LAND("error.null_land"),
    NULL_NAME("error.null_name"),
    NULL_OLD_NAME("error.null_old_name"),
    NULL_NEW_NAME("error.null_new_name"),
    NULL_WORLD("error.null_world"),
    NO_LAND("error.no_land");

    private String key;
    ExceptionCause(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
}
