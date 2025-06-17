package utms.models;

import utms.interfaces.Serviceable;

public class Bus implements Serviceable {
    private String busNumber;

    public Bus(String busNumber) {
        this.busNumber = busNumber;
    }

    @Override
    public void service() {
        System.out.println("Servicing Bus: " + busNumber);
    }
}
