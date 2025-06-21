package utms.models;

public abstract class User {
    protected String name;
    protected String userId;

    public User(String name, String userId) {
        this.name = name;
        this.userId = userId;
    }

    public abstract void requestTransport();

    public String getName() { return name; }
    public String getUserId() { return userId; }
}
