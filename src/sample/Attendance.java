package sample;

import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;

public class Attendance {
    private SimpleStringProperty fullName;
    private SimpleStringProperty timeOfAction;
    private SimpleStringProperty attendance;

    Attendance(String fullName, String timeOfAction, String attendance) {
        this.fullName = new SimpleStringProperty(fullName);
        this.timeOfAction = new SimpleStringProperty(timeOfAction);
        this.attendance = new SimpleStringProperty(attendance);
    }

    Attendance() {}


    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public SimpleStringProperty timeOfActionProperty() {
        return timeOfAction;
    }

    public void setTimeOfAction(String timeOfAction) {
        this.timeOfAction.set(timeOfAction);
    }

    public String getAttendance() {
        return attendance.get();
    }

    public SimpleStringProperty attendanceProperty() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance.set(attendance);
    }

    public ArrayList<Attendance> getArrayOfAttendance(ArrayList<String> listOfAttendance) {
        ArrayList<Attendance> attendanceList = new ArrayList<>();

        for (int i = 0; i < listOfAttendance.size(); i = i + 3) {
            Attendance list = new Attendance(listOfAttendance.get(i), listOfAttendance.get(i+1), listOfAttendance.get(i+2));
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