package amu;

public class Config {
    public final static String JDBC_RESOURCE = "jdbc/bookstore";
    
    public final static String EMAIL_SMTP_HOST = "smtp.gmail.com";
    public final static String EMAIL_SMTP_PORT = "465";
    public final static String EMAIL_SMTP_USER = "tdt4237.progsik.grp14@gmail.com";
    public final static String EMAIL_SMTP_PASSWORD = "abcdefghijl"; // Application-specific password
    
    public final static String EMAIL_FROM_ADDR = "tdt4237.progsik.grp14@gmail.com";
    public final static String EMAIL_FROM_NAME = "Amu-Darya";
    
    public final static String SALT="NaCl"; // Changing this will invalidate all customer passwords in DB
}
