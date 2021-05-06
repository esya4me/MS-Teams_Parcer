package sample;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadCsvFromFile
{
    private String line;
    private final ArrayList<String> fullName = new ArrayList<>();
    private final ArrayList<String> action = new ArrayList<>();
    private final ArrayList<String> timeOfAction = new ArrayList<>();

    private final ArrayList<String> listOfEndTime = new ArrayList<>();
    private final ArrayList<String> listOfAttendance = new ArrayList<>();
    private final StringBuilder sb = new StringBuilder();

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
    private String toGetNearTime(ArrayList<String> firstEnter) {
        //Преобразуем строку firstEnter.get(0) в секунды с помощью метода toGetSecondsFromString
        //и записываем в firstTimeOnArray
        int firstTimeOnArray = toGetSecondsFromString(firstEnter.get(0));
        int endTime = 0;
        ArrayList<String> arrayOfEndTime = toSetArrayOfEndTime();

        //Проходимся по каждой строке списка и записываем в s
        for (String s : arrayOfEndTime) {
            //Преобразуем строку s в секунды методом toGetSecondsFromString
            //и записываем в endTime
            endTime = toGetSecondsFromString(s);
            //Находим разницу между endTime и firstTimeOnArray
            int diff = endTime - firstTimeOnArray;
            //Если разница >= 0, то выходим из цикла
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
    private int toGetSecondsFromString(String string) {
        int seconds;
        String[] stringsOfTime = string.split(":");
        seconds = (Integer.parseInt(stringsOfTime[0]) * 3600) +
                (Integer.parseInt(stringsOfTime[1]) * 60) +
                Integer.parseInt(stringsOfTime[2]);
        return seconds;
    }

    /**
     * Функция переводит секунды в строку по типу "00:20:32"
     * @param seconds - количество секунд
     * @return - строка по типу "00:20:32"
     */
    private String toGetStringFromSeconds(int seconds) {
        int hours = (int) Math.floor(seconds/3600);
        int minutes = (int)Math.floor((seconds - (hours * 3600))/60);
        int sec = seconds - (hours * 3600) - (minutes * 60);
        String min, strSec, hr, time;
        if (hours < 10)
            hr = "0" + hours;
        else
            hr = "" + hours;

        if (minutes<10)
            min = "0" + minutes;
        else
            min = "" + minutes;

        if (sec<10)
            strSec = "0" + sec;
        else
            strSec = "" + sec;

        time = "" + hr + ":" + min + ":" + strSec;
        return time;
    }

    /**
     * Функция считает количество строк в .csv
     * написана для ограничения в методе @toReadFullFile
     * @param filename - путь к файлу (Не пустой)
     * @return - количество строк в файле .csv
     * @throws IOException - отлавливаем ошибки во время операции ввода-вывода
     */
    public int countLines(String filename) throws IOException {
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
    private int countOfElements(String filepath) throws IOException {
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

                //Каждый раз обнуляем строку, чтобы накапливать только сумму по строке
                String strings = "";
                for(int i=4; i < countOfElements(filepath); i++){
                    strings = strings + var[i];
                }

                float finalGrade = (float) digitsToArray(strings)/(float)((countOfElements(filepath) - 3)/3);
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

    /**
     * Функция расчета времени присутствия в собрании
     * @param filepath - путь к файлу
     * @throws IOException - отлавливаем ошибки во время операции ввода-вывода
     */
    //ПЕРЕПИСАТЬ АДЕКВАТНО МЕТОД
    public ArrayList<String> toReadFullFileTest(String filepath) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_16));
        //Используем для пропуска первой строки
        line = br.readLine();
        int i = 0;
        int numberOfLines = countLines(filepath);
        while ((line = br.readLine()) != null) {
            String[] var = line.split("\t");

            String[] strings = new String[numberOfLines];
            String str = var[2];
            strings[i] = str.replaceAll(".*[,]\\s","");

            fullName.add(var[0]);
            action.add(var[1]);
            timeOfAction.add(strings[i]);

            //System.out.println("Фамилия: " + var[0] +
            //        " Действие: " + var[1] +
            //        " Время выполнения действия: " + strings[i]
            //);
        }
        String nearTimeToEnd = toGetNearTime(timeOfAction);

        for(i = 0; i < fullName.size(); i++) {
            for(int j = i + 1; j < fullName.size(); j++) {
                if(action.get(i).equals("Присоединился") && action.get(j).equals("Ушел")) {
                    int sec1 = toGetSecondsFromString(timeOfAction.get(i));
                    int sec2 = toGetSecondsFromString(timeOfAction.get(j));
                    int time = sec2 - sec1;
                    String timeStr = toGetStringFromSeconds(time);

                    fullName.remove(j);
                    action.remove(j);
                    timeOfAction.remove(j);

                    timeOfAction.set(i , timeStr);
                    break;
                }
                if(!fullName.get(i).equals(fullName.get(j))) {
                    String diff = toGetStringFromSeconds(toGetSecondsFromString(nearTimeToEnd) - toGetSecondsFromString(timeOfAction.get(i)));
                    timeOfAction.set(i, diff);
                    break;
                }
            }
            if (fullName.get(i).equals(fullName.get(fullName.size()-1))) {
                String diff = toGetStringFromSeconds(toGetSecondsFromString(nearTimeToEnd) - toGetSecondsFromString(timeOfAction.get(i)));
                timeOfAction.set(i, diff);
                break;
            }
            //System.out.println( lastName.get(i) + "\t" + action.get(i) + "\t" + timeOfAction.get(i));
        }
        for (i = 0; i < fullName.size(); i++) {
            for(int j = i + 1; j < fullName.size(); j++) {
                if (fullName.get(i).equals(fullName.get(j))) {
                    if (action.get(i).equals("Присоединился") && action.get(j).equals("Присоединился")) {
                        int sec11 = toGetSecondsFromString(timeOfAction.get(i));
                        int sec22 = toGetSecondsFromString(timeOfAction.get(j));
                        int time = sec22 + sec11;
                        String timeStr = toGetStringFromSeconds(time);

                        fullName.remove(j);
                        action.remove(j);
                        timeOfAction.remove(j);

                        timeOfAction.set(i, timeStr);
                        break;
                    }
                }
            }
            //System.out.println( fullName.get(i) + "\t" + action.get(i) + "\t" + timeOfAction.get(i));

            ////Соединяем три ArrayList'a в один
            for (i = 0; i < fullName.size(); i++) {
                listOfAttendance.add(fullName.get(i));
                listOfAttendance.add(timeOfAction.get(i));
            }
        }
        return listOfAttendance;
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

    public static void main(String[] args) throws IOException {
        ReadCsvFromFile readCsvFromFile = new ReadCsvFromFile();
        readCsvFromFile.toReadFullFileTest("D:/UVA 15.03.csv");
    }
}
