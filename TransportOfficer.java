package utms.models;

public class TransportOfficer extends User {

    public TransportOfficer(String name, String userId) {
        super(name, userId);
    }

    public void assignDriver(String vehicleType) {
        System.out.println("Assigning driver for vehicle type: " + vehicleType);
    }

    public void assignDriver(String vehicleType, String shiftTime) {
        System.out.println("Assigning driver for " + vehicleType + " during " + shiftTime + " shift.");
    }

    @Override
    public void requestTransport() {
        System.out.println("Transport Officer " + name + " schedules transport.");
    }
}
