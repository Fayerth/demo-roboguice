package com.seemingamusing.demo.roboguice.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.inject.Inject;
import com.seemingamusing.demo.roboguice.R;
import com.seemingamusing.demo.roboguice.ui.event.TeamSelectedEvent;
import roboguice.event.Observes;
import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;

/**
 * @author Eric Lee
 */
public class MembersFragment extends RoboFragment {

    @Inject
    private TeamMembersAdapter mAdapter;

    @InjectView(R.id.list_members)
    private ListView mMembersList;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_members, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMembersList.setAdapter(mAdapter);
    }

    // See related comment for MainActivity#handleCheckLocationEvent
    public void handleTeamSelected(@Observes final TeamSelectedEvent event) {
        mAdapter.setTeam(event.teamId);
    }
}