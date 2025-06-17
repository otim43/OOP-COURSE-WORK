package utms.main;

import utms.models.*;

public class Main {
    public static void main(String[] args) {
        // Create users
        User student = new Student("Lucky", "S001", "Software Engineering");
        User lecturer = new Lecturer("Dr. Sarah", "L101", "Engineering");
        User officer = new TransportOfficer("John", "T999");

        // Method Overriding
        student.requestTransport();
        lecturer.requestTransport();
        officer.requestTransport();

        // Method Overloading
        TransportOfficer to = new TransportOfficer("Jane", "T102");
        to.assignDriver("Bus");
        to.assignDriver("Van", "Morning");

        // Interface: Serviceable
        Bus bus = new Bus("B45");
        bus.service();

        // Interface: Trackable
        Van van = new Van("V12");
        van.trackLocation();
    }
}
