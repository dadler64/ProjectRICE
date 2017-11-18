package com.rice.server.util;

import javafx.beans.NamedArg;

import java.io.Serializable;

/**
 * <p>A convenience class to represent username and password pairs.</p>
 *
 * @author adlerd
 */
public class Credential<U, P> implements Serializable {
    /**
     * Default init value spec for <code>Credential</code>
     */
    private byte[] iv = "wTmg4qj8dNszs2ji".getBytes();

    /**
     * Username of this <code>Credential</code>.
     */
    private U username;
    /**
     * Password of this this <code>Credential</code>.
     */
    private P password;

    /**
     * Creates a new pair
     *
     * @param username The username for this credential
     * @param password The password to use for this credential
     */
    public Credential(@NamedArg("username") U username, @NamedArg("password") P password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the key for this pair.
     *
     * @return key for this pair
     */
    public U getUsername() {
        return username;
    }

    /**
     * Gets the value for this pair.
     *
     * @return value for this pair
     */
    public P getPassword() {
        return password;
    }

    /**
     * <p><code>String</code> representation of this
     * <code>Credential</code>.</p>
     * <p>
     * <p>The default name/value delimiter '=' is always used.</p>
     *
     * @return <code>String</code> representation of this <code>Pair</code>
     */
    @Override
    public String toString() {
        return "[Username=" + username + ", Password=" + password + "]";
    }

    /**
     * <p>Generate a hash code for this <code>Pair</code>.</p>
     * <p>
     * <p>The hash code is calculated using both the name and
     * the value of the <code>Pair</code>.</p>
     *
     * @return hash code for this <code>Pair</code>
     */
    @Override
    public int hashCode() {
        // name's hashCode is multiplied by an arbitrary prime number (13)
        // in order to make sure there is a difference in the hashCode between
        // these two parameters:
        //  name: a  value: aa
        //  name: aa value: a
        return username.hashCode() * 13 + (password == null ? 0 : password.hashCode());
    }

    /**
     * <p>Test this <code>Credential</code> for equality with another
     * <code>Object</code>.</p>
     * <p>
     * <p>If the <code>Object</code> to be tested is not a
     * <code>Pair</code> or is <code>null</code>, then this method
     * returns <code>false</code>.</p>
     * <p>
     * <p>Two <code>Pair</code>s are considered equal if and only if
     * both the names and values are equal.</p>
     *
     * @param o the <code>Object</code> to test for
     *          equality with this <code>Pair</code>
     * @return <code>true</code> if the given <code>Object</code> is
     * equal to this <code>Pair</code> else <code>false</code>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Credential) {
            Credential credential = (Credential) o;
            if (username != null ? !username.equals(credential.username) : credential.username != null) return false;
            return password != null ? password.equals(credential.password) : credential.password == null;
        }
        return false;
    }
}
