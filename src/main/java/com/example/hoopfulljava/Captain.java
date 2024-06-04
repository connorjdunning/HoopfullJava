package com.example.hoopfulljava;
public class Captain {
    private String name;

    private String teamID;

    public Captain(String name, String teamID) {
        this.name = name;
        this.teamID = teamID;
    }

    /**
     * Gets the name of the captain.
     *
     * @return a string of the name of the captain
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the teamID assigned to the captain.
     *
     * @return a string of the team ID of the captain
     */
    public String getTeamID() {
        return teamID;
    }

    /**
     * Set the name of the captain.
     *
     * @param name - A string of the chosen name of the captain.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the team ID of the captain.
     *
     * @param teamID - A string of the chosen team ID of the captain.
     */
    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

}
