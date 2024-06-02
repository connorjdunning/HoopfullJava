package com.example.hoopfulljava;

public class Player {
    private String name;

    private String playerID;

    private String teamID;

    public Player(String name, String id, String teamID) {
        this.name = name;
        this.playerID = id;
        this.teamID = teamID;
    }

    /**
     * Get the name of the player.
     *
     * @return a string of the player name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the ID of the player.
     *
     * @return a string of the player ID.
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Get the name of the TeamID.
     *
     * @return a string of the TeamID.
     */
    public String getTeamID() {
        return teamID;
    }

    /**
     * Set the name of the player.
     *
     * @param name - A string of the chosen player name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the name of the ID.
     *
     * @param id - A string of the chosen player ID.
     */
    public void setPlayerID(String id) {
        this.playerID = id;
    }


    @Override
    public String toString() {
        return "PlayerID: " + this.playerID + "\n" + " PlayerName: " + this.name + "\n";
    }
}
