package com.seemingamusing.demo.roboguice.ui.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.inject.Inject;
import com.seemingamusing.demo.roboguice.R;
import com.seemingamusing.demo.roboguice.ui.event.CheckLocationEvent;
import roboguice.event.EventManager;
import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;

/**
 * @author Eric Lee
 */
public class FooterFragment extends RoboFragment {

    @Inject
    private EventManager mEventManger;

    @InjectView(R.id.btn_about)
    private Button mAboutButton;
    @InjectView(R.id.btn_location)
    private Button mLocationButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_footer, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AboutDialog().show(getFragmentManager(), null);
            }
        });
        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mEventManger.fire(new CheckLocationEvent());
            }
        });
    }
}