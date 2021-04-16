package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class ReadCsvFromFile
{
    public static void main(String[] args) {
        ReadCsvFromFile obj = new ReadCsvFromFile();
        obj.toReadFullFile("D:\\Test_Final_Grade.csv");
        System.out.println("________________");
        obj.toReadEmails("D:\\Test_Final_Grade.csv");
        System.out.println("________________");
        obj.countOfElements("D:\\Test_Final_Grade.csv");
        System.out.println("________________");
        obj.finalGrade("D:\\Test_Final_Grade.csv");
        System.out.println("________________");
        obj.finalGradeToArrayList("D:\\Test_Final_Grade.csv");
    }

    //СЧИТАЕМ КОЛИЧЕСТВО СТРОК В CSV С ОЦЕНКАМИ
    public int countLines(String filename) throws IOException
    {
        LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
        int cnt;
        String lineRead = "";
        while ((lineRead = reader.readLine()) != null) {}

        cnt = reader.getLineNumber();
        reader.close();
        return cnt;
    }

    //СЧИТАЕМ КОЛИЧЕСТВО ЭЛЕМЕНТОВ В CSV С ОЦЕНКАМИ
    public int countOfElements(String filepath) {
        int elements = 0;
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {

            if ((line = br.readLine()) != null) {
                String[] var = line.split(",");
                elements = var.length;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return elements;
    }

    public int digitsToArray(String stringWithDigits)
    {
        // для группировки найденных цифр и парсинга каждой группы в число
        StringBuilder sb = new StringBuilder();

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
                //sbout.append(k + ", "); // формирование строки-списка найденных чисел
                sb.delete(0, sb.length()); // очистка sb для повторного использования
            } else
                // если текущий символ - это не_цифра,просто переходим к следующему
                i++;
        }
        return sum;
    }
    public ArrayList finalGrade(String filepath)
    {
        String line = "";
        ArrayList<String> arrayList = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            //Используем для пропуска первой строки
            line = br.readLine();
            int i = 4;
            while ((line = br.readLine()) != null)
            {
                String[] var = line.split(",");

                String strings = "";
                for(i=4; i<countOfElements(filepath);i++)
                {
                    strings = strings + var[i];
                }
                float finalGrade = (float) digitsToArray(strings)/(countOfElements(filepath) - 3)/3;
                //System.out.println( var[0] + " " +
                //                    var[1] + " " +
                //                    finalGrade);
                arrayList.add(var[0]+" "+var[1]+" "+finalGrade);
            }

        } catch (IOException e) { e.printStackTrace(); }
        System.out.println(arrayList.toString());
        return arrayList;
    }

    public ArrayList finalGradeToArrayList(String filepath)
    {
        String line = "";
        ArrayList<String> arrayList = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            //Используем для пропуска первой строки
            line = br.readLine();
            int i = 4;
            while ((line = br.readLine()) != null)
            {
                String[] var = line.split(",");

                String strings = "";
                for(i=4; i<countOfElements(filepath);i++)
                {
                    strings = strings + var[i];
                }
                float finalGrade = (float) digitsToArray(strings)/(countOfElements(filepath) - 3)/3;
                //Записываем как отдельные элементы массива
                //Имя   --  Фамилия --  Итоговая оценка
                arrayList.add(var[0]);
                arrayList.add(var[1]);
                arrayList.add(finalGrade + "");
            }

        } catch (IOException e) { e.printStackTrace(); }
        System.out.println(arrayList.toString());
        return arrayList;
    }







    //ЧИТАЕМ CSV ФАЙЛ
    public void toReadFullFile(String filepath)
    {
        String line = "";

        try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            //Используем для пропуска первой строки
            line = br.readLine();
            while ((line = br.readLine()) != null)
            {
                String[] var = line.split(",");

                System.out.println("Имя: " + var[0] +
                                   " Фамилия: " + var[1] +
                                   " E-mail: " + var[2] +
                                   var[3] +
                        var[4] +
                        var[5] +
                        var[6]);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    //ВЫДЕЛЯЕМ С CSV СПИСОК E-MAIL'ОВ ДЛЯ ОТПРАВКИ ПИСЕМ
    public void toReadEmails(String filepath) {
        String line = "";

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
