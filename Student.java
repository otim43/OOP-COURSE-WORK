package utms.models;

public class Student extends User {
    private String course;

    public Student(String name, String userId, String course) {
        super(name, userId);
        this.course = course;
    }

    @Override
    public void requestTransport() {
        System.out.println("Student " + name + " requests a bus for a school trip.");
    }
}
