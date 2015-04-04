package com.seemingamusing.demo.roboguice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Eric Lee
 */
public class Team {

    private final String id;
    private final String name;
    private final List<String> memberIds = new ArrayList<>();

    /**
     * Create a new Team object.
     *
     * @param id The team ID
     * @param name The team name
     */
    public Team(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Return the team ID.
     *
     * @return The team ID
     */
    public String getId() {
        return id;
    }

    /**
     * Return the team name.
     *
     * @return The team name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the list of team members.
     *
     * @return The team members
     */
    public List<String> getMembers() {
        return Collections.unmodifiableList(memberIds);
    }

    /**
     * Adds new team members to the teams by using the member's ID.
     *
     * @param ids The list of team member IDs
     */
    public void addMembersById(final String... ids) {
        Collections.addAll(memberIds, ids);
    }

    /**
     * Attempt to remove a team member from the team.
     *
     * @param id The team member ID
     */
    public void removeTeamMemberById(final String id) {
        final Iterator<String> iterator = memberIds.iterator();
        while (iterator.hasNext()) {
            if (id.equals(iterator.next())) {
                iterator.remove();
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}