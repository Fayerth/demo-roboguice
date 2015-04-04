package com.seemingamusing.demo.roboguice.ui.common;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seemingamusing.demo.roboguice.R;

/**
 * @author Eric Lee
 */
public class AboutDialog extends DialogFragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        getDialog().setTitle(R.string.title_about);
        final TextView about = (TextView) inflater.inflate(R.layout.dialog_about, container, false);
        about.setMovementMethod(new ScrollingMovementMethod());
        return about;
    }
}