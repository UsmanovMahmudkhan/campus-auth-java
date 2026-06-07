package io.github.usmanovmahmudkhan.campusauth;

import java.util.Objects;

/**
 * An immutable view of a verified campus member.
 *
 * <p>A {@code CampusMember} carries only non-sensitive identity metadata. It
 * never stores passwords, tokens, cookies, or session material.
 */
public final class CampusMember {

    private final String id;
    private final String displayName;
    private final CampusRole role;

    private CampusMember(String id, String displayName, CampusRole role) {
        this.id = Objects.requireNonNull(id, "id");
        this.displayName = Objects.requireNonNull(displayName, "displayName");
        this.role = Objects.requireNonNull(role, "role");
    }

    /**
     * Creates a campus member.
     *
     * @param id          stable member identifier (for example a directory id)
     * @param displayName human-readable name shown in an application
     * @param role        the member's {@link CampusRole}
     * @return a new {@code CampusMember}
     * @throws NullPointerException if any argument is {@code null}
     */
    public static CampusMember of(String id, String displayName, CampusRole role) {
        return new CampusMember(id, displayName, role);
    }

    /** Returns the stable member identifier. */
    public String id() {
        return id;
    }

    /** Returns the human-readable display name. */
    public String displayName() {
        return displayName;
    }

    /** Returns the member's role. */
    public CampusRole role() {
        return role;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CampusMember)) {
            return false;
        }
        CampusMember that = (CampusMember) other;
        return id.equals(that.id)
                && displayName.equals(that.displayName)
                && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, displayName, role);
    }

    @Override
    public String toString() {
        return "CampusMember{id='" + id + "', displayName='" + displayName + "', role=" + role + '}';
    }
}
