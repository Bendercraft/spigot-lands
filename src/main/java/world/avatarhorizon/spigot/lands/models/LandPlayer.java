package world.avatarhorizon.spigot.lands.models;


import org.bukkit.entity.Player;

public class LandPlayer
{
    private Player player;
    private Land currentLand; //The land the player is standing in

    public LandPlayer(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Land getCurrentLand()
    {
        return currentLand;
    }

    /**
     * /!\ You should not call this method. The current land is updated by the LandListener.
     * @param currentLand
     */
    public void setCurrentLand(Land currentLand)
    {
        this.currentLand = currentLand;
    }
}
