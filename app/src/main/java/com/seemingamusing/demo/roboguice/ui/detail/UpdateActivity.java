package com.seemingamusing.demo.roboguice.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.util.List;

/**
 * @author Eric Lee
 */
@ContentView(R.layout.activity_update)
public class UpdateActivity extends RoboActionBarActivity {

    private static final String KEY_ID = "id";
    private static final int[] IMAGES = {
            R.drawable.ic_pic_1,
            R.drawable.ic_pic_2,
            R.drawable.ic_pic_3,
            R.drawable.ic_pic_4,
    };

    /**
     * Convenience for starting a new UpdateActivity with the provided team member's ID.
     *
     * @param context  The context triggering the navigation
     * @param memberId The team member's ID
     */
    public static void startUpdateActivity(final Context context, final String memberId) {
        final Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra(KEY_ID, memberId);
        context.startActivity(intent);
    }


    @Named(DefaultRoboModule.GLOBAL_EVENT_MANAGER_NAME)
    @Inject
    private EventManager mGlobalEventManager;
    @Inject
    private TeamContentProvider mProvider;
    private TeamMember mMember;

    @InjectView(R.id.profile)
    private ImageView mPicture;
    @InjectView(R.id.name)
    private EditText mName;
    @InjectView(R.id.surname)
    private EditText mSurname;
    @InjectView(R.id.team_name)
    private Spinner mTeamName;
    @InjectView(R.id.skills)
    private EditText mSkills;
    @InjectView(R.id.btn_next_image)
    private Button mNextBtn;
    @InjectView(R.id.btn_save)
    private Button mSaveBtn;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMember = mProvider.getTeamMember(this, getMemberId());
        if (mMember != null) {
            applyValues();
        }
    }

    private void applyValues() {
        mName.setText(mMember.getName());
        mSurname.setText(mMember.getSurname());
        mSkills.setText(mMember.getSkills());

        setTeam();
        setProfilePicture(mMember.getPicture());
        setNextPictureButton();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mMember.setName(mName.getText());
                mMember.setSurname(mSurname.getText());
                mMember.setSkills(mSkills.getText());
                mMember.setPicture((Integer) mPicture.getTag());

                mGlobalEventManager.fire(new TeamMemberUpdatedEvent());
                finish();
            }
        });
    }

    private void setTeam() {
        final List<Team> teams = mProvider.getTeams(this);
        final String teamId = mMember.getTeamId();
        final int size = teams.size();
        int position = 0;
        for (int i = 0; i < size; i++) {
            final Team meat = teams.get(i);
            if (teamId.equals(meat.getId())) {
                position = i;
            }
        }
        mTeamName.setAdapter(new ArrayAdapter<>(this, R.layout.item_team, teams));
        mTeamName.setSelection(position);
    }

    private void setProfilePicture(final int resId) {
        final int imgResId;
        if (resId == 0) {
            imgResId = R.drawable.default_picture;
        } else {
            imgResId = resId;
        }
        mPicture.setImageResource(imgResId);
        mPicture.setTag(imgResId);
    }

    private void setNextPictureButton() {
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            private int i;

            @Override
            public void onClick(final View v) {
                i = (i + 1) % IMAGES.length;
                setProfilePicture(IMAGES[i]);
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