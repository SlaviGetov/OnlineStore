package com.failedsaptrainees.onlinestore.enums;

public enum Roles
{
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE"),
    CLIENT("CLIENT");

    private final String text;

    Roles(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getRoleName()
    {
        return "ROLE_" + text;
    }

}
