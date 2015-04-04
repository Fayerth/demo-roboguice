package com.seemingamusing.demo.roboguice.model;

import android.support.annotation.DrawableRes;

/**
 * @author Eric Lee
 */
public class TeamMember {

    private final String id;
    private CharSequence name;
    private CharSequence surname;
    private CharSequence teamId;
    private CharSequence skills;
    private int picture;

    /**
     * Creates a new TeamMember object.
     *
     * @param id The member ID
     */
    public TeamMember(final String id) {
        this.id = id;
    }

    /**
     * Return the member ID.
     *
     * @return The member ID
     */
    public String getId() {
        return id;
    }

    /**
     * Set a new name value.
     *
     * @param name The new member name
     */
    public void setName(final CharSequence name) {
        this.name = name;
    }

    /**
     * Return the member name.
     *
     * @return The member name
     */
    public String getName() {
        return name.toString();
    }

    /**
     * Set a new surname value.
     *
     * @param surname The new member surname
     */
    public void setSurname(final CharSequence surname) {
        this.surname = surname;
    }

    /**
     * Return the member surname.
     *
     * @return The member surname
     */
    public String getSurname() {
        return surname.toString();
    }

    /**
     * Set the new team ID.
     *
     * @param teamId The new team ID
     */
    public void setTeamId(final CharSequence teamId) {
        this.teamId = teamId;
    }

    /**
     * Return the member's team's ID.
     *
     * @return The member's team ID
     */
    public String getTeamId() {
        return teamId.toString();
    }

    /**
     * Set a new skills.
     *
     * @param skills The new skills
     */
    public void setSkills(final CharSequence skills) {
        this.skills = skills;
    }

    /**
     * Return the member's skills.
     *
     * @return The member's skills
     */
    public String getSkills() {
        return skills.toString();
    }

    /**
     * Set a new picture resource.
     *
     * @param picture The new picture resource ID
     */
    public void setPicture(@DrawableRes final int picture) {
        this.picture = picture;
    }

    /**
     * Return the member's picture.
     *
     * @return The picture drawable resource ID
     */
    @DrawableRes
    public int getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return name.toString() + ' ' + surname.toString();
    }
}