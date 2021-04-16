package sample;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class FinalGrade {
    private SimpleStringProperty name;
    private SimpleStringProperty lastName;
    private SimpleStringProperty finalGrade;

    FinalGrade(String name, String lastName, String finalGrade){
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.finalGrade = new SimpleStringProperty(finalGrade);
    }
    FinalGrade(){ }
    public String getName(){
        return name.get();
    }
    public String getLastName(){
        return lastName.get();
    }
    public String getFinalGrade(){
        return finalGrade.get();
    }

    public ArrayList getArrayOfFinalGrades(ArrayList<String> listOfGrades){
        ArrayList<FinalGrade> arrayList = new ArrayList<>();
        for (int i = 0; i<listOfGrades.size(); i=i+3){
            FinalGrade list = new FinalGrade(listOfGrades.get(i), listOfGrades.get(i+1),listOfGrades.get(i+2));
            for (int j = 0; j<=i; j++){
                arrayList.add(list);
            }
        };
        for(int i=0;i<arrayList.size();i++){
            for(int j=i+1;j<arrayList.size();j++){
                //Сравниваем текущий элемент со всеми, копии удаляем
                if(arrayList.get(i).equals(arrayList.get(j))){
                    arrayList.remove(j);
                    j--;
                }
            }
        }
        return arrayList;
    }
}
