package com.voting;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "impl";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb",
            H2 = "h2",
            HEROKU = "heroku";

    //  Get DB profile depending of DB driver in classpath
    public static String getActiveDbProfile() {
        try {
            Class.forName("org.postgresql.Driver");
            return POSTGRES_DB;
        } catch (ClassNotFoundException ex) {
            try {
                Class.forName("org.h2.Driver");
                return Profiles.H2;
            } catch (ClassNotFoundException e) {
                try {
                    Class.forName("org.hsqldb.jdbcDriver");
                    return Profiles.HSQL_DB;
                } catch (ClassNotFoundException ef) {
                    throw new IllegalStateException("Could not find DB driver");
                }
            }
        }
    }
}
