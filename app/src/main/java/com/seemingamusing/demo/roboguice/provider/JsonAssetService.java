package com.seemingamusing.demo.roboguice.provider;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Eric Lee
 */
public class JsonAssetService implements DataService {

    @Override
    public String getTeamContent(final Context context) {
        return readFromAssets("teams.json", context.getAssets());
    }

    @Override
    public String getTeamMemberContent(final Context context) {
        return readFromAssets("members.json", context.getAssets());
    }

    private String readFromAssets(final String filename, final AssetManager assets) {
        if (assets == null) {
            return EMPTY;
        }

        InputStream in = null;
        String response;
        try {
            in = assets.open(filename);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            final StringBuilder buffer = new StringBuilder();

            String s = reader.readLine();
            while (s != null) {
                buffer.append(s);
                s = reader.readLine();
            }

            response = buffer.toString();

        } catch (final IOException e) {
            response = EMPTY;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (final IOException e) {
                    // Do nothing
                }
            }
        }
        return response;
    }

    private static final String EMPTY = "[]";
}