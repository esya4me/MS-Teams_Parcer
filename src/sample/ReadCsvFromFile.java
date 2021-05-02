package sample;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import javax.mail.*;

public class ReadCsvFromFile
{
    private String line;
    private final ArrayList<String> lastName = new ArrayList<>();
    private final ArrayList<String> action = new ArrayList<>();
    private final ArrayList<String> timeOfAction = new ArrayList<>();

    private final ArrayList<String> listOfEndTime = new ArrayList<>();
    private final StringBuilder sb = new StringBuilder();
    private final StringBuilder strings = new StringBuilder();

    /**
     * Функция, в которой времена окончания занятий записываются в список
     * @return - список времен окончания занятий
     * с 1 по 8 занятия включительно
     */
    private ArrayList<String> toSetArrayOfEndTime(){
        listOfEndTime.addAll(Arrays.asList("09:30:00", "11:10:00", "12:50:00", "14:50:00",
                                            "16:30:00", "18:10:00", "19:50:00", "21:30:00"));
        return listOfEndTime;
    }

    /**
     * Функция получения ближайшего времени окончания
     * исходя из первого времени входа
     * @param firstEnter - ArrayList со временами входа
     * @return - возвращаем строку с ближайшим временем окончания
     *          по типу "18:10:00"
     */
    private String toGetNearTime(ArrayList<String> firstEnter){
        int firstTimeOnArray = toGetSecondsFromString(firstEnter.get(0));
        int endTime = 0;
        ArrayList<String> arrayOfEndTime = toSetArrayOfEndTime();

        //Получаем строку из ArrayList'a
        for (String s : arrayOfEndTime) {
            endTime = toGetSecondsFromString(s);
            int diff = endTime - firstTimeOnArray;
            if (diff >= 0) {
                break;
            }
        }
        return toGetStringFromSeconds(endTime);
    }

    /**
     * Функция получения секунд из строки по типу "18:10:00"
     * @param string - Не пустая строка
     * @return - количество секунд
     */
    private int toGetSecondsFromString(@NotNull String string) {
        int seconds;
        String[] stringsOfTime = string.split(":");
        seconds = (Integer.parseInt(stringsOfTime[0]) * 3600) +
                    (Integer.parseInt(stringsOfTime[1]) * 60) +
                        Integer.parseInt(stringsOfTime[2]);
        return seconds;
    }

    /**
     * Функция переводит секунды в строку по типо "00:20:32"
     * @param seconds - количество секунд
     * @return - строка по типу "00:20:32"
     */
    private String toGetStringFromSeconds(int seconds) {
        int hours = (int) Math.floor(seconds/3600);
        int minutes = (int)Math.floor((seconds-(hours * 3600))/60);
        int sec = seconds - (hours * 3600) - (minutes * 60);
        String min,strSec,hr, time;
        if (hours < 10)
            hr = "0" + hours;
        else
            hr = "" + hours;

        if (minutes<10)
            min = "0"+minutes;
        else
            min = ""+minutes;

        if (sec<10)
            strSec = "0"+sec;
        else
            strSec = ""+sec;

        time = "" + hr + ":"+ min + ":" + strSec;
        return time;
    }

    public static void main(String[] args) {
        ReadCsvFromFile readCsvFromFile = new ReadCsvFromFile();
        readCsvFromFile.toReadFullFile("D:/UTC-151 06.04.21.csv");
        System.out.println(readCsvFromFile.toGetSecondsFromString("12:01:03"));
        System.out.println(readCsvFromFile.toGetStringFromSeconds(1232));
    }

    /**
     * Функция считает количество строк в .csv
     * написана для ограничения в методе @toReadFullFile
     * @param filename - путь к файлу (Не пустой)
     * @return - количество строк в файле .csv
     * @throws IOException - отлавливаем ошибки во время операции ввода-вывода
     */
    public int countLines(@NotNull String filename) throws IOException {
        LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
        int cnt = 0;
        while ((line = reader.readLine()) != null) {
            cnt = reader.getLineNumber();
        }
        reader.close();
        return cnt;
    }

    /**
     * Функция считает количество элементов в .csv файле
     * написана для ограничения в методе @finalGradeToArrayList
     * @param filepath - путь к файлу (Не пустой)
     * @return - количество элементов в .csv строке
     * @throws IOException - отлавливаем ошибки во время операции ввода-вывода
     */
    private int countOfElements(@NotNull String filepath) throws IOException {
        int elements = 0;
        BufferedReader br = new BufferedReader(new FileReader(filepath));
            if ((line = br.readLine()) != null) {
                String[] var = line.split(",");
                elements = var.length;
            }
        return elements;
    }

    //ПЕРЕВОДИМ СТРОКУ СИМВОЛОВ В МАССИВ ЧИСЕЛ
    public int digitsToArray(String stringWithDigits) {
        // для группировки найденных цифр и парсинга каждой группы в число
        int i = 0;
        int sum = 0;
        // посимвольный перебор текста
        while (i < stringWithDigits.length()) {
            // проверка на принадлежность к цифрам
            if (Character.isDigit(stringWithDigits.charAt(i))) {
                // если текущий символ это цифра, то добавляем его в строку;
                // увеличиваем индекс и смотрим сразу следующий символ;
                // если будет не_цифра, то выйдем из текущего while
                while (Character.isDigit(stringWithDigits.charAt(i))) {
                    sb.append(stringWithDigits.charAt(i));
                    i++;
                    // защита от выхода счётчика i за пределы длины строки,
                    // если последний символ в тексте - это цифра
                    if (i == stringWithDigits.length())
                        break;
                } // выходим из while, имея группу цифр в объекте sb
                int k = Integer.parseInt(sb.toString()); // парсинг sb в число
                sum += k; // подсчёт суммы
                sb.delete(0, sb.length()); // очистка sb для повторного использования
            } else
                // если текущий символ - это не_цифра,просто переходим к следующему
                i++;
        }
        return sum;
    }

    //ФУНКЦИЯ ПОЛУЧЕНИЯ ИТОГОВЫХ ОЦЕНОК
    public ArrayList<String> finalGradeToArrayList(String filepath) {
        ArrayList<String> arrayList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            //Используем для пропуска первой строки
            line = br.readLine();
            while ((line = br.readLine()) != null)
            {
                String[] var = line.split(",");
                //Используем StringBuilder
                //Выглядит как
                //for(int i=4; i<countOfElements(filepath);i++){
                //    strings = strings + var[i]
                //}
                for(int i=4; i<countOfElements(filepath);i++) {
                    strings.append(var[i]);
                }
                float finalGrade = (float) digitsToArray(strings.toString())/(float)((countOfElements(filepath) - 3)/3);
                //Записываем как отдельные элементы массива
                //Имя   --  Фамилия --  Итоговая оценка
                //И заменяем все " на пусто
                arrayList.add(var[0].replaceAll("\"",""));
                arrayList.add(var[1].replaceAll("\"",""));
                arrayList.add(finalGrade + "");
            }
        //Отлавливаем ошибки во время операции ввода-вывода
        } catch (IOException e) { e.printStackTrace(); }
        return arrayList;
    }

    //ЧИТАЕМ CSV ФАЙЛ, ЕСЛИ ЧЕЛОВЕК ЗАХОДИЛ И ВЫХОДИЛ НЕСКОЛЬКО РАЗ
    //СЧИТАТЬ (ВРЕМЯ ВЫХОДА - ВРЕМЯ ВХОДА) И НАХОДИМ ВСЮ СУММУ ПО ЧЕЛОВЕКУ
    public void toReadFullFile(String filepath) {

        ReadCsvFromFile attendance = new ReadCsvFromFile();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(
                filepath), StandardCharsets.UTF_16))) {
            //Используем для пропуска первой строки
            line = br.readLine();
            int i = 0;
            int numberOfLines = countLines(filepath);
            while ((line = br.readLine()) != null) {
                String[] var = line.split("\t");

                String[] strings = new String[numberOfLines];
                String str = var[2];
                strings[i] = str.replaceAll(".*[,]\\s","");
                //System.out.print(strings[i]);


                lastName.add(var[0]);
                action.add(var[1]);
                timeOfAction.add(strings[i]);

                System.out.println("Фамилия: " + var[0] +
                                   " Действие: " + var[1] +
                                   " Время выполнения действия: " + strings[i]
                );
            }
            String nearTimeToEnd = toGetNearTime(timeOfAction);

            for(i = 0; i < lastName.size();i++) {
                for(int j = i + 1; j < lastName.size();j++) {
                    //Сравниваем элементы по Фамилии
                    //Если Фамилия текущего совпадает с фамилией следующего, значит
                    //Студент отключался от собрания
                    if(lastName.get(i).equals(lastName.get(j))) { //Если имена эквивалентны
                        if (!action.get(i).equals(action.get(j))) { //Если действия не эквивалентны

                            int seconds1 = attendance.toGetSecondsFromString(timeOfAction.get(j));
                            int seconds2 = attendance.toGetSecondsFromString(timeOfAction.get(i));
                            int time = seconds1 - seconds2;
                            timeOfAction.set(i,attendance.toGetStringFromSeconds(time));
                            i=i+2;
                            j=j+2;
                        }
                        //Если текущее действие не эквивалентно следующему, значит
                        //Студент отключался от собрания
                        else {
                            if (timeOfAction.get(i).equals(timeOfAction.get(j))) {
                                //TODO
                            }
                            //Если текущее время не эквивалентно следующему
                            //Необходимо вычесть = Ушёл - Присоединился
                            else {
                                int seconds1 = attendance.toGetSecondsFromString(timeOfAction.get(j));
                                int seconds2 = attendance.toGetSecondsFromString(timeOfAction.get(i));
                                int time = seconds1 - seconds2;
                                timeOfAction.set(i,attendance.toGetStringFromSeconds(time));
                            }
                        }
                    }
                    //Если текущий элемент не эквивалентен следующему, значит
                    //студент не выходил до конца формирования отчета посещения
                    //Считаем, что он присутствовал всю пару
                   // if (!lastName.get(i).equals(lastName.get(j)) && !action.get(i).equals(action.get(j))){
                   //     if (!Objects.equals(lastName.get(lastName.size() - 1), lastName.get(lastName.size() - 2))){timeOfAction.set(lastName.size()-1,"1:30:00");}
                   //     timeOfAction.set(i,"1:30:00");
                   //     i++;
                   //     //break outerloop;
                   // }
                    else {
                        if (action.get(i).equals(action.get(j))) {
                            int time = toGetSecondsFromString(nearTimeToEnd) - attendance.toGetSecondsFromString(timeOfAction.get(i));
                            timeOfAction.set(i, toGetStringFromSeconds(time));
                        }
                    }
                }
            }
            for (i=0;i<lastName.size();i++){
            System.out.println(lastName.get(i) + " "+action.get(i)+" "+timeOfAction.get(i));
            }

        } catch (UnsupportedEncodingException e){
        System.out.println("Неподдерживаемая кодировка!");
        } catch (IOException e) { e.printStackTrace(); }
    }


    //ВЫДЕЛЯЕМ С CSV СПИСОК E-MAIL'ОВ ДЛЯ ОТПРАВКИ ПИСЕМ
    public void toReadEmails(String filepath) {

        try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            //Используем для пропуска первой строки
            line = br.readLine();
            int i = 0;
            int numberOfLines = countLines(filepath);
            while ((line = br.readLine()) != null) {
                //Выполняем разделение строки, используя "," как разделитель+заменяем " на пусто
                String[] var = line.replace("\"" , "").split(",");
                //Записываем каждое значение из столбца E-mail в строку emails
                String[] strings = new String[numberOfLines];
                strings[i] = var[2];
                System.out.print(strings[i]);
                i++;
            }


        } catch (IOException e) { e.printStackTrace(); }
    }
}
