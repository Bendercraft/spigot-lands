package world.avatarhorizon.spigot.lands.exceptions;

public enum ExceptionCause
{
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
