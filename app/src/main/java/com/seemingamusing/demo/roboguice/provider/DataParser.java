package com.seemingamusing.demo.roboguice.provider;

import com.seemingamusing.demo.roboguice.model.Team;
import com.seemingamusing.demo.roboguice.model.TeamMember;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eric Lee
 */
final class DataParser {

    static List<Team> parseTeams(final String rawData) {
        try {
            final JSONArray rawTeams = new JSONArray(rawData);
            final int size = rawTeams.length();
            final List<Team> teams = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                final JSONObject rawObj = rawTeams.getJSONObject(i);
                teams.add(parseTeam(rawObj));
            }
            return teams;

        } catch (final JSONException e) {
            return Collections.emptyList();
        }
    }

    private static Team parseTeam(final JSONObject raw) throws JSONException {
        final Team team = new Team(raw.getString("id"), raw.getString("name"));
        final JSONArray members = raw.getJSONArray("members");
        final int size = members.length();
        for (int i = 0; i < size; i++) {
            team.addMembersById(members.getString(i));
        }
        return team;
    }

    static Map<String, TeamMember> parseMember(final String rawData) {
        try {
            final JSONArray rawMembers = new JSONArray(rawData);
            final int size = rawMembers.length();
            final Map<String, TeamMember> members = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                final JSONObject rawObj = rawMembers.getJSONObject(i);
                members.put(rawObj.getString("id"), parseMember(rawObj));
            }
            return members;

        } catch (final JSONException e) {
            return Collections.emptyMap();
        }
    }

    private static TeamMember parseMember(final JSONObject raw) throws JSONException {
        final TeamMember member = new TeamMember(raw.getString("id"));
        member.setName(raw.getString("name"));
        member.setSurname(raw.getString("surname"));
        member.setTeamId(raw.getString("teamId"));
        member.setSkills(raw.getString("skills"));
        member.setPicture(raw.getInt("picture"));
        return member;
    }

    private DataParser() {
        // Ninja constructor!!!!!!
    }
}