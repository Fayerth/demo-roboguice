package com.seemingamusing.demo.roboguice.provider;

import android.content.Context;

/**
 * @author Eric Lee
 */
public interface DataService {

    String getTeamContent(Context context);

    String getTeamMemberContent(Context context);
}