import java.util.ArrayList;
import java.util.List;

public class StudyRoomReservationSystem {
    private List<StudyRoom> studyRooms;

    public StudyRoomReservationSystem() {
        studyRooms = new ArrayList<>();
    }

    public synchronized void addStudyRoom(StudyRoom studyRoom) {
        studyRooms.add(studyRoom);
    }

    public synchronized void reserveStudyRoom(int roomNumber) throws StudyRoomUnavailableException {
        for (StudyRoom room : studyRooms) {
            if (room.getRoom_number() == roomNumber) {
                if (room.isAvailability_status()) {
                    room.setAvailability_status(false);
                    System.out.println("Study room " + roomNumber + " reserved successfully.");
                } else {
                    throw new StudyRoomUnavailableException("Study room " + roomNumber + " is already reserved.");
                }
                return;
            }
        }
        System.out.println("Study room " + roomNumber + " does not exist.");
    }

    public synchronized void releaseStudyRoom(int roomNumber) {
        for (StudyRoom room : studyRooms) {
            if (room.getRoom_number() == roomNumber) {
                if (!room.isAvailability_status()) {
                    room.setAvailability_status(true);
                    System.out.println("Study room " + roomNumber + " released successfully.");
                } else {
                    System.out.println("Study room " + roomNumber + " is already available.");
                }
                return;
            }
        }
        System.out.println("Study room " + roomNumber + " does not exist.");
    }

    public synchronized void displayStudyRoomStatus() {
        System.out.println("Study Room Status:");
        for (StudyRoom room : studyRooms) {
            String status = room.isAvailability_status() ? "Available" : "Reserved";
            System.out.println("Room Number: " + room.getRoom_number() + ", Status: " + status);
        }
    }

    public static  void main(String[] args) throws StudyRoomUnavailableException, InterruptedException {
        // Create StudyRoom objects
        StudyRoom room1 = new StudyRoom(1, 4, true);
        StudyRoom room2 = new StudyRoom(2, 6, false);
        StudyRoom room3 = new StudyRoom(3, 3, true);
        StudyRoom room4 = new StudyRoom(4, 2, false);

        StudyRoomReservationSystem reservationSystem = new StudyRoomReservationSystem();

        // Add study rooms to the reservation system
        reservationSystem.addStudyRoom(room1);
        reservationSystem.addStudyRoom(room2);
        reservationSystem.addStudyRoom(room3);
        reservationSystem.addStudyRoom(room4);

        // Display initial status of all study rooms
        reservationSystem.displayStudyRoomStatus();

        // Simulate concurrent study room reservation and release operations using threads
        Thread reservationThread1 = new Thread(() -> {
            try {
                reservationSystem.reserveStudyRoom(1);
                Thread.sleep(2000); // Simulating some delay between operations
                reservationSystem.releaseStudyRoom(2);
                reservationSystem.reserveStudyRoom(1);
            } catch (StudyRoomUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread reservationThread2 = new Thread(() -> {
            try {
                reservationSystem.releaseStudyRoom(1);
                Thread.sleep(2000); // Simulating some delay between operations
                reservationSystem.releaseStudyRoom(2);
                reservationSystem.reserveStudyRoom(2);
            } catch (StudyRoomUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        // when the two threads call the releaseStudyRoom method after 2000 mili seconds only one method will get the key for the execution
        //(because the methods are synchronized methods)

        // so after one method call finishes reserveStudyRoom method for the room two which runs on the second thread executes

        //finally release study room method will be called again for the room 2 and it will be available after all the executions are completed

        reservationThread1.start();
        reservationThread2.start();

        reservationThread1.join();
        reservationThread2.join();

        // Display final status of all study rooms
        reservationSystem.displayStudyRoomStatus();
    }
}

// creating the exception class

class StudyRoomUnavailableException extends Exception {
    public StudyRoomUnavailableException(String message) {
        super(message);
    }
}

// designing the study room class

class StudyRoom{

    int Room_number;
    int Capacity;
    boolean Availability_status;

    // creating the constructor
    public StudyRoom(int room_number, int capacity, boolean availability_status) {
        Room_number = room_number;
        Capacity = capacity;
        Availability_status = availability_status;
    }

    // generating getters and setters
    public int getRoom_number() {
        return Room_number;
    }

    public int getCapacity() {
        return Capacity;
    }

    public boolean isAvailability_status() {
        return Availability_status;
    }

    public void setRoom_number(int room_number) {
        Room_number = room_number;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public void setAvailability_status(boolean availability_status) {
        Availability_status = availability_status;
    }

}