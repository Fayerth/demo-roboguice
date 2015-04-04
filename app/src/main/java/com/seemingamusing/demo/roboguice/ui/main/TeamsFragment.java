package com.seemingamusing.demo.roboguice.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.google.inject.Inject;
import com.seemingamusing.demo.roboguice.R;
import com.seemingamusing.demo.roboguice.model.Team;
import com.seemingamusing.demo.roboguice.provider.TeamContentProvider;
import com.seemingamusing.demo.roboguice.ui.event.TeamSelectedEvent;
import roboguice.event.EventManager;
import roboguice.fragment.provided.RoboFragment;

/**
 * @author Eric Lee
 */
public class TeamsFragment extends RoboFragment {

    private static final String KEY_POSITION = "position";


    @Inject
    private TeamContentProvider mProvider;
    @Inject
    private EventManager mEventManager;

    private ArrayAdapter<Team> mAdapter;
    private int mPosition;

    @Nullable
    private Spinner mTeamsSpinner;
    @Nullable
    private ListView mTeamsList;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_teams, container, false);
        // While preferable to use the @InjectView annotations from RoboGuice, there seems to be an issue with RoboGuice reading
        // the @Nullable nnotations. A potential solution / work-around is suggested in the reported issue below; however that
        // solution was causing a bit more issues than it was resolving. For the time being, the current implementation is a
        // different work-around, with additional research required to really get to the root of the issue.
        //
        // https://github.com/roboguice/roboguice/issues/226
        //
        mTeamsSpinner = (Spinner) view.findViewById(R.id.spinner_teams);
        mTeamsList = (ListView) view.findViewById(R.id.list_teams);
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();
        mAdapter = new ArrayAdapter<>(activity, R.layout.item_team, mProvider.getTeams(activity));

        final int position;
        if (savedInstanceState == null) {
            position = 0;
        } else {
            position = savedInstanceState.getInt(KEY_POSITION, 0);
        }
        setViewAdapter(position);
    }

    private void setViewAdapter(final int position) {
        updateSpinner(position);
        updateList(position);
    }

    private void updateSpinner(final int position) {
        if (mTeamsSpinner != null) {
            mTeamsSpinner.setAdapter(mAdapter);
            mTeamsSpinner.setSelection(position);
            mTeamsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
                    onTeamSelected(position);
                }

                @Override
                public void onNothingSelected(final AdapterView<?> parent) {
                    // Do nothing
                }
            });
        }
    }

    private void onTeamSelected(final int position) {
        mPosition = position;
        final Team team = mAdapter.getItem(position);
        mEventManager.fire(new TeamSelectedEvent(team.getId()));
    }

    private void updateList(final int position) {
        if (mTeamsList != null) {
            mTeamsList.setAdapter(mAdapter);
            mTeamsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                    onTeamSelected(position);
                }
            });
            onTeamSelected(position);
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        outState.putInt(KEY_POSITION, mPosition);
        super.onSaveInstanceState(outState);
    }
}