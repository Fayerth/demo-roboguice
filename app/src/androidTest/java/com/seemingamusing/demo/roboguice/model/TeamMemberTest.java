package com.seemingamusing.demo.roboguice.model;

import org.junit.Before;
import org.junit.Test;
import com.seemingamusing.demo.roboguice.R;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Eric Lee
 */
public class TeamMemberTest {

    private TeamMember member;

    @Before
    public void setUp() {
        member = new TeamMember(SAMPLE_ID);
        member.setName(SAMPLE_NAME);
        member.setSurname(SAMPLE_SURNAME);
        member.setTeamId(SAMPLE_TEAM_ID);
        member.setSkills(SAMPLE_SKILLS);
        member.setPicture(R.drawable.ic_pic_1);
    }

    @Test
    public void testInitialization() {
        assertThat(member, is(notNullValue()));
    }

    @Test
    public void testId() {
        assertThat(member.getId(), is(equalTo(SAMPLE_ID)));
    }

    @Test
    public void testName() {
        assertThat(member.getName(), is(equalTo(SAMPLE_NAME)));
    }

    @Test
    public void testSurname() {
        assertThat(member.getSurname(), is(equalTo(SAMPLE_SURNAME)));
    }

    @Test
    public void testTeamId() {
        assertThat(member.getTeamId(), is(equalTo(SAMPLE_TEAM_ID)));
    }

    @Test
    public void testSkills() {
        assertThat(member.getSkills(), is(equalTo(SAMPLE_SKILLS)));
    }

    @Test
    public void testPicture() {
        assertThat(member.getPicture(), is(equalTo(R.drawable.ic_pic_1)));
    }


    private static final String SAMPLE_ID = "mem_123";
    private static final String SAMPLE_NAME = "Omega";
    private static final String SAMPLE_SURNAME = "Point";
    private static final String SAMPLE_TEAM_ID = "123456";
    private static final String SAMPLE_SKILLS = "Android, not iOS";
}