import java.io.*;
import java.nio.charset.StandardCharsets;

public class SplitLogs {
    public static void main(String[] args) throws IOException {

        String fileLogPath = args[0]; // получение аргуемена "путь до целевого файла"
        String filesLogPath = null;

        if (args[0].equals("--help")) { //передача параметра --help
            System.out.println("Программа запускаетсяа из командной строки при помощи команды: \n" +
                    "\"java -jar <название jar-файла> <путь/имя_лог_файла> <путь/постоянная_часть_имени_полученных_файлов> \n" +
                    "Переданный файл с логами будет разбит на 5 файлов. Новыйе файлы будут созданы в директории, которая уазываетсвя вторым параметром\n" +
                    "Если создаваемый файлы уже сующествуюет в данной директории, то произдойдет перезапись файла.\n");
        }else {
            filesLogPath =  args[1]; //получение аргуемента "путь сохранения новых файлов"
            File file = new File(fileLogPath);
            long fileSize = file.length(); // получаем размер файла

            int count_file = 5; // количество файлов логов

            if (fileSize > 0) { // проверка пустой ли файл
                long fileSizeTemp = fileSize;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "windows-1251"))) { // считываем файл с учетом кодировки
                    for (int i = 1; i < (count_file + 1); i++) // создаем указанное количество фалов для записи
                    {
                        if (fileSizeTemp == 0) break; // если файл весь вычитали, то новых файлов не создается
                        String filesLogPath_n = filesLogPath + "_" + i + ".txt"; // задаем название и расширение файлов
                        File file_n = new File(filesLogPath_n);
                        file_n.createNewFile(); // создаем новый файл, если он не создан
                        try (FileOutputStream fos = new FileOutputStream(file_n, false);
                             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8))) { // поток записи строк в файл с учетом кодировки
                            long fileSize_n = file_n.length(); // Проверяем размер файла в, который пишем строки

                            String line;
                            while (fileSize_n < fileSize / (count_file) && (line = reader.readLine()) != null) { //проверяем размер файла в котоый пишем и естли не прочитанные строки в исходном файле
                                writer.write(line + "\n"); // пишем строки в файл
                                fileSize_n = file_n.length(); // обновляем вес нового файла
                            }
                            fileSizeTemp -= fileSize_n; // вычетаем записанный объем из общего объема
                            writer.flush();
                        }
                    }
                }
            } else System.out.println("ВНИМАНИЕ! Указанный файл пустой."); // если файл пустой выдаст предупреждение
        }
    }
}
