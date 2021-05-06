package sample;

import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;

public class FinalGrade {
    private SimpleStringProperty name;
    private SimpleStringProperty lastName;
    private SimpleStringProperty finalGrade;

    FinalGrade(String name, String lastName, String finalGrade) {
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.finalGrade = new SimpleStringProperty(finalGrade);
    }
    FinalGrade(){ }

    public String getName() {
        return name.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public String getFinalGrade() {
        return finalGrade.get();
    }

    /**
     * Функция возвращает список итоговых оценок
     * @param listOfGrades - список оценок
     * @return - возвращает список итоговых оценок в ArrayList<FinalGrade>
     *           для перевода его в дальнейшем в ObservableList<FinalGrade>
     *           и использования в объекте TableView<FinalGrade>
     */
    public ArrayList<FinalGrade> getArrayOfFinalGrades(ArrayList<String> listOfGrades) {
        //Создаем новый объект типа ArrayList<FinalGrade>
        ArrayList<FinalGrade> arrayList = new ArrayList<>();
        for (int i = 0; i < listOfGrades.size(); i = i + 3) {
            FinalGrade list = new FinalGrade(listOfGrades.get(i), listOfGrades.get(i+1),listOfGrades.get(i+2));
            for (int j = 0; j <= i; j++) {
                arrayList.add(list);
            }
        }
        for(int i=0;i<arrayList.size();i++) {
            //Сравниваем i-й элемент с j-ми в цикле
            for(int j = i + 1; j < arrayList.size(); j++) {
                //Если i-й эквивалентен j-му
                if(arrayList.get(i).equals(arrayList.get(j))) {
                    //Удаляем объект из списка
                    arrayList.remove(j);
                    j--;
                }
            }
        }
        return arrayList;
    }
}