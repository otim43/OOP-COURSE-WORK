package utms.models;

import utms.interfaces.Trackable;

public class Van implements Trackable {
    private String vanNumber;

    public Van(String vanNumber) {
        this.vanNumber = vanNumber;
    }

    @Override
    public void trackLocation() {
        System.out.println("Tracking Van " + vanNumber + " using GPS.");
    }
}
