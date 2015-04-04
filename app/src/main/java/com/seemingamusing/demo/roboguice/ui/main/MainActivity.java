package com.seemingamusing.demo.roboguice.ui.main;

import android.os.Bundle;
import android.widget.Toast;
import com.seemingamusing.demo.roboguice.R;
import com.seemingamusing.demo.roboguice.ui.event.CheckLocationEvent;
import roboguice.activity.RoboActionBarActivity;
import roboguice.event.Observes;
import roboguice.inject.ContentView;

/**
 * @author Eric Lee
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActionBarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.teams_listing_container, new TeamsFragment())
                    .commit();
        }
    }

    // Android Studio complains that this method is not used, which we all know is not true. As the CI tools shouldn't complain about that issue,
    // nothing is done to remove the IDE warning here. If it is absolutely paramount to remove all warnings, a possible work around is to create an
    // @ObservesEvent annotation to "suppress" that warning. That or just un-check the IDE's "Unused declaration" inspection. :)
    public void handleCheckLocationEvent(@Observes final CheckLocationEvent event) {
        Toast.makeText(this, "You are looking at " + getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }
}