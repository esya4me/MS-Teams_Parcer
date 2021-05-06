package sample;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Attendance {
    private SimpleStringProperty fullName;
    private SimpleStringProperty timeOfAction;

    Attendance(String fullName, String timeOfAction) {
        this.fullName = new SimpleStringProperty(fullName);
        this.timeOfAction = new SimpleStringProperty(timeOfAction);
    }

    Attendance() {}

    public String getFullName() {
        return fullName.get();
    }

    public String getTimeOfAction() {
        return timeOfAction.get();
    }

    public ArrayList<Attendance> getArrayOfAttendance(ArrayList<String> listOfAttendance) {
        ArrayList<Attendance> attendanceList = new ArrayList<>();

        for (int i = 0; i < listOfAttendance.size(); i = i + 2) {
            Attendance list = new Attendance(listOfAttendance.get(i), listOfAttendance.get(i+1));
            for (int j = 0; j <= i; j++) {
                attendanceList.add(list);
            }
        }
        for(int i = 0; i < attendanceList.size(); i++) {
            //Сравниваем i-й элемент с j-ми в цикле
            for(int j = i + 1; j < attendanceList.size(); j++) {
                //Если i-й эквивалентен j-му
                if(attendanceList.get(i).equals(attendanceList.get(j))) {
                    //Удаляем объект из списка
                    attendanceList.remove(j);
                    j--;
                }
            }
        }

        return attendanceList;
    }
}