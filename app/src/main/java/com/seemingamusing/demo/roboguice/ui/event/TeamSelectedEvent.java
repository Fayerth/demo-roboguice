package com.seemingamusing.demo.roboguice.ui.event;

/**
 * @author Eric Lee
 */
public class TeamSelectedEvent {

    public final String teamId;

    /**
     * Creates a new event providing the selected team's ID.
     *
     * @param teamId The selected team's ID
     */
    public TeamSelectedEvent(final String teamId) {
        this.teamId = teamId;
    }
}