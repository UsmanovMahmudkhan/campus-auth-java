package io.github.usmanovmahmudkhan.campusauth;

/**
 * Roles that a campus member can hold.
 *
 * <p>The role is descriptive metadata returned alongside a verified
 * {@link CampusMember}. It is not used to grant any privileges by itself.
 */
public enum CampusRole {

    /** A student member. */
    STUDENT,

    /** A teaching or research faculty member. */
    FACULTY,

    /** A non-faculty staff member. */
    STAFF,

    /** An unauthenticated or limited-access guest. */
    GUEST
}
