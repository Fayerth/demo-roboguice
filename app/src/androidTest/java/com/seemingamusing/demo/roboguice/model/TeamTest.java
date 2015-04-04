package com.seemingamusing.demo.roboguice.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Eric Lee
 */
public class TeamTest {

    private Team team;

    @Before
    public void setUp() {
        team = new Team(SAMPLE_ID, SAMPLE_NAME);
    }

    @Test
    public void testInitialization() {
        assertThat(team, is(notNullValue()));
    }

    @Test
    public void testIdReturnsExpected() {
        assertThat(team.getId(), is(equalTo(SAMPLE_ID)));
    }

    @Test
    public void testNameReturnsExpected() {
        assertThat(team.getName(), is(equalTo(SAMPLE_NAME)));
    }

    @Test
    public void testTeamMembersEmptyByDefault() {
        assertThat(team.getMembers(), is(emptyCollectionOf(String.class)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTeamMembersCannotBeModified() {
        team.getMembers().add(SAMPLE_TEAM_ID);
    }

    @Test
    public void testTeamMembersContainsExpected() {
        final String[] ids = {
                SAMPLE_TEAM_ID,
        };
        team.addMembersById(ids);

        assertThat(team.getMembers(), contains(ids));
    }

    @Test
    public void testRemoveTeamMemberByIdReturnsExpectedResults() {
        final String[] ids = {
                SAMPLE_TEAM_ID,
        };
        team.addMembersById(ids);
        team.removeTeamMemberById(SAMPLE_TEAM_ID);

        assertThat(team.getMembers(), is(emptyCollectionOf(String.class)));
    }

    private static final String SAMPLE_ID = "123456";
    private static final String SAMPLE_NAME = "Alpha Cow";
    private static final String SAMPLE_TEAM_ID = "mem_123";
}