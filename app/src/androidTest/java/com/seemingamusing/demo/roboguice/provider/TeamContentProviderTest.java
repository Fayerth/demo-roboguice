package com.seemingamusing.demo.roboguice.provider;

import android.content.Context;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.seemingamusing.demo.roboguice.model.Team;
import com.seemingamusing.demo.roboguice.model.TeamMember;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author Eric Lee
 */
public class TeamContentProviderTest {

    private TeamContentProvider provider;
    @Mock
    private Context mockedContext;
    @Mock
    private DataService mockedService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        final Injector injector = Guice.createInjector(new TestModule());

        provider = injector.getProvider(TeamContentProvider.class).get();
    }

    @Test
    public void testInitialization() {
        assertThat(provider, is(notNullValue()));
    }

    @Test
    public void testGetTeamsReturnsEmptyList() {
        when(mockedService.getTeamContent(eq(mockedContext))).thenReturn(EMPTY);

        assertThat(provider.getTeams(mockedContext), is(emptyCollectionOf(Team.class)));
    }

    @Test
    public void testGetTeamsReturnsExpected() {
        when(mockedService.getTeamContent(eq(mockedContext))).thenReturn(SAMPLE_TEAMS);

        assertThat(provider.getTeams(mockedContext), contains(expectedTeam(SAMPLE_TEAM_ID)));
    }

    @Test
    public void testGetTeamByIdReturnsNull() {
        when(mockedService.getTeamContent(eq(mockedContext))).thenReturn(EMPTY);

        assertThat(provider.getTeamById(mockedContext, SAMPLE_TEAM_ID), is(nullValue()));
    }

    @Test
    public void testGetTeamByIdReturnsValid() {
        when(mockedService.getTeamContent(eq(mockedContext))).thenReturn(SAMPLE_TEAMS);

        assertThat(provider.getTeamById(mockedContext, SAMPLE_TEAM_ID), is(expectedTeam(SAMPLE_TEAM_ID)));
    }

    @Test
    public void testGetTeamMembersReturnsEmptyList() {
        final Team team = new Team(SAMPLE_TEAM_ID, SAMPLE_TEAM_ID); // Team name does not matter here
        when(mockedService.getTeamMemberContent(eq(mockedContext))).thenReturn(EMPTY);

        assertThat(provider.getTeamMembers(mockedContext, team), is(emptyCollectionOf(TeamMember.class)));
    }

    @Test
    public void testGetTeamMembersReturnsExpectedValue() {
        final Team team = new Team(SAMPLE_TEAM_ID, SAMPLE_TEAM_ID);
        team.addMembersById(SAMPLE_MEMBER_ID);
        when(mockedService.getTeamMemberContent(eq(mockedContext))).thenReturn(SAMPLE_MEMBERS);

        assertThat(provider.getTeamMembers(mockedContext, team), contains(expectedMember(SAMPLE_MEMBER_ID)));
    }

    @Test
    public void testGetTeamMemberReturnsNull() {
        when(mockedService.getTeamMemberContent(eq(mockedContext))).thenReturn(EMPTY);

        assertThat(provider.getTeamMember(mockedContext, SAMPLE_MEMBER_ID), is(nullValue()));
    }

    @Test
    public void testGetTeamMemberReturnsExpectedValue() {
        when(mockedService.getTeamMemberContent(eq(mockedContext))).thenReturn(SAMPLE_MEMBERS);

        assertThat(provider.getTeamMember(mockedContext, SAMPLE_MEMBER_ID), is(expectedMember(SAMPLE_MEMBER_ID)));
    }


    private TypeSafeMatcher<Team> expectedTeam(final String id) {
        return new TeamMatcher(id);
    }

    private TypeSafeMatcher<TeamMember> expectedMember(final String id) {
        return new MemberMatcher(id);
    }

    private static final String EMPTY = "[]";
    private static final String SAMPLE_TEAMS = "[{\"id\":\"123456\",\"name\":\"Alpha Cow\",\"members\":[\"mem_123\"]}]";
    private static final String SAMPLE_TEAM_ID = "123456";
    private static final String SAMPLE_MEMBERS = "[{\"id\":\"mem_123\",\"name\":\"Beta\",\"surname\":\"Tester\",\"teamId\":\"123456\",\"skills\":\"Android, not iOS\",\"picture\":123}]";
    private static final String SAMPLE_MEMBER_ID = "mem_123";

    /**
     * Matcher for Team objects.
     */
    private static class TeamMatcher extends TypeSafeMatcher<Team> {
        private final String id;

        public TeamMatcher(final String id) {
            this.id = id;
        }

        @Override
        protected boolean matchesSafely(final Team team) {
            return id.equals(team.getId());
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(" team has expected ID = ").appendValue(id);
        }
    }

    /**
     * Matcher for Team objects.
     */
    private static class MemberMatcher extends TypeSafeMatcher<TeamMember> {
        private final String id;

        public MemberMatcher(final String id) {
            this.id = id;
        }

        @Override
        protected boolean matchesSafely(final TeamMember team) {
            return id.equals(team.getId());
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(" member has expected ID = ").appendValue(id);
        }
    }

    private class TestModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(DataService.class).toInstance(mockedService);
        }
    }
}