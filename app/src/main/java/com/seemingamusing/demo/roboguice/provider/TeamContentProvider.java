package com.seemingamusing.demo.roboguice.provider;

import android.content.Context;
import android.support.annotation.Nullable;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.seemingamusing.demo.roboguice.model.Team;
import com.seemingamusing.demo.roboguice.model.TeamMember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eric Lee
 */
@Singleton
public class TeamContentProvider {

    @Inject
    private DataService mService;

    private final List<Team> mTeams = new ArrayList<>();
    private final Map<String, TeamMember> mMembers = new HashMap<>();

    /**
     * Retrieve the list of teams stored in the system.
     *
     * @param context The context hosting the request
     * @return The available teams
     */
    public List<Team> getTeams(final Context context) {
        initializeTeams(context);
        return Collections.unmodifiableList(mTeams);
    }

    /**
     * Retrieve a team by its ID.
     *
     * @param context The context hosting the request
     * @param teamId  The team ID
     * @return The team, or null if none was found
     */
    @Nullable
    public Team getTeamById(final Context context, final String teamId) {
        initializeTeams(context);
        for (final Team team : mTeams) {
            if (teamId.equals(team.getId())) {
                return team;
            }
        }
        return null;
    }

    private void initializeTeams(final Context context) {
        if (mTeams.isEmpty()) {
            final String raw = mService.getTeamContent(context);
            mTeams.addAll(DataParser.parseTeams(raw));
        }
    }

    /**
     * Retrieve the team members for the provided team.
     *
     * @param context The context hosting the request
     * @param team    The team
     * @return The list of team members for the provided team
     */
    public List<TeamMember> getTeamMembers(final Context context, final Team team) {
        initializeMembers(context);
        final List<TeamMember> members = new ArrayList<>();
        for (final String memberId : team.getMembers()) {
            final TeamMember member = mMembers.get(memberId);
            if (member != null) {
                members.add(member);
            }
        }
        return members;
    }

    /**
     * Retrieve the team members for the provided team.
     *
     * @param context  The context hosting the request
     * @param memberId The team member ID
     * @return The team member, or null if not found
     */
    @Nullable
    public TeamMember getTeamMember(final Context context, final String memberId) {
        initializeMembers(context);
        return mMembers.get(memberId);
    }

    private void initializeMembers(final Context context) {
        if (mMembers.isEmpty()) {
            final String raw = mService.getTeamMemberContent(context);
            mMembers.putAll(DataParser.parseMember(raw));
        }
    }
}