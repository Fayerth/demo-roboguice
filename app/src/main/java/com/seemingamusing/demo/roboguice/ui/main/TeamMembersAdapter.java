package com.seemingamusing.demo.roboguice.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.inject.Inject;
import com.seemingamusing.demo.roboguice.R;
import com.seemingamusing.demo.roboguice.model.Team;
import com.seemingamusing.demo.roboguice.model.TeamMember;
import com.seemingamusing.demo.roboguice.provider.TeamContentProvider;
import com.seemingamusing.demo.roboguice.ui.detail.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric Lee
 */
public class TeamMembersAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final List<TeamMember> mMembers = new ArrayList<>();

    @Inject
    private TeamContentProvider mProvider;

    /**
     * Creates a new adapter for viewing a Team's TeamMembers.
     *
     * @param context The context hosting the adapter
     */
    @Inject
    public TeamMembersAdapter(final Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Sets the team to display.
     *
     * @param teamId The team ID
     */
    public void setTeam(final String teamId) {
        final Team team = mProvider.getTeamById(mContext, teamId);
        mMembers.clear();
        mMembers.addAll(mProvider.getTeamMembers(mContext, team));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMembers.size();
    }

    @Override
    public TeamMember getItem(final int position) {
        return mMembers.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final View view;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_member, parent, false);
        } else {
            view = convertView;
        }

        final TeamMember member = getItem(position);
        final TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(member.toString());
        view.findViewById(R.id.btn_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DetailsActivity.startDetailsActivity(mContext, member.getId());
            }
        });
        return view;
    }
}