package com.seemingamusing.demo.roboguice.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.seemingamusing.demo.roboguice.R;
import com.seemingamusing.demo.roboguice.model.Team;
import com.seemingamusing.demo.roboguice.model.TeamMember;
import com.seemingamusing.demo.roboguice.provider.TeamContentProvider;
import com.seemingamusing.demo.roboguice.ui.event.CheckLocationEvent;
import com.seemingamusing.demo.roboguice.ui.event.TeamMemberUpdatedEvent;
import roboguice.activity.RoboActionBarActivity;
import roboguice.config.DefaultRoboModule;
import roboguice.event.EventListener;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * @author Eric Lee
 */
@ContentView(R.layout.activity_details)
public class DetailsActivity extends RoboActionBarActivity {

    private static final String KEY_ID = "id";

    /**
     * Convenience for starting a new DetailsActivity with the provided team member's ID.
     *
     * @param context  The context triggering the navigation
     * @param memberId The team member's ID
     */
    public static void startDetailsActivity(final Context context, final String memberId) {
        final Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(KEY_ID, memberId);
        context.startActivity(intent);
    }


    @Named(DefaultRoboModule.GLOBAL_EVENT_MANAGER_NAME)
    @Inject
    private EventManager mGlobalEventManager;
    @Inject
    private TeamContentProvider mProvider;

    @InjectView(R.id.profile)
    private ImageView mPicture;
    @InjectView(R.id.name)
    private TextView mName;
    @InjectView(R.id.surname)
    private TextView mSurname;
    @InjectView(R.id.team_name)
    private TextView mTeamName;
    @InjectView(R.id.skills)
    private TextView mSkills;
    @InjectView(R.id.btn_update)
    private Button mUpdateBtn;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlobalEventManager.registerObserver(TeamMemberUpdatedEvent.class, new EventListener<TeamMemberUpdatedEvent>() {
            @Override
            public void onEvent(final TeamMemberUpdatedEvent event) {
                setMemberValues();
            }
        });
        setMemberValues();
    }

    private void setMemberValues() {
        final TeamMember member = mProvider.getTeamMember(this, getMemberId());
        if (member == null) {
            return;
        }

        mName.setText(member.getName());
        mSurname.setText(member.getSurname());
        mSkills.setText('"' + member.getSkills() + '"');

        if (member.getPicture() == 0) {
            mPicture.setImageResource(R.drawable.default_picture);
        } else {
            mPicture.setImageResource(member.getPicture());
        }

        final Team team = mProvider.getTeamById(this, member.getTeamId());
        if (team != null) {
            mTeamName.setText(team.getName());
        } else {
            mTeamName.setText("No affiliated team");
        }

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                UpdateActivity.startUpdateActivity(DetailsActivity.this, getMemberId());
            }
        });
    }

    private String getMemberId() {
        return getIntent().getStringExtra(KEY_ID);
    }

    // See related comment for MainActivity#handleCheckLocationEvent
    public void handleCheckLocationEvent(@Observes final CheckLocationEvent event) {
        final String msg = "You are looking at " + getClass().getSimpleName() + " for " + getMemberId();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}