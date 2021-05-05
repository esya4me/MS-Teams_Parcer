package sample;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Attendance {
    private SimpleStringProperty fullName;
    private SimpleStringProperty actionOfPerson;
    private SimpleStringProperty timeOfAction;

    Attendance(String fullName, String actionOfPerson, String timeOfAction) {
        this.fullName = new SimpleStringProperty(fullName);
        this.actionOfPerson = new SimpleStringProperty(actionOfPerson);
        this.timeOfAction = new SimpleStringProperty(timeOfAction);
    }

    Attendance() {}

    public String getFullName() {
        return fullName.get();
    }

    public String getActionOfPerson() {
        return actionOfPerson.get();
    }

    public String getTimeOfAction() {
        return timeOfAction.get();
    }

    public ArrayList<Attendance> getArrayOfAttendance(ArrayList<String> listOfAttendance) {
        ArrayList<Attendance> attendanceList = new ArrayList<>();

        for (int i = 0; i < listOfAttendance.size(); i = i + 3) {
            Attendance list = new Attendance(listOfAttendance.get(i), listOfAttendance.get(i+1),listOfAttendance.get(i+2));
            for (int j = 0; j <= i; j++) {
                attendanceList.add(list);
            }
        }

        
        return attendanceList;
    }
}
