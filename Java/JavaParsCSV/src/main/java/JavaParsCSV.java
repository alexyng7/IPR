import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaParsCSV {
    public static void main(String[] args) throws IOException {
        //заменить аргументами
        /*String delimiter = ";";
        String path_log = "c:\\q";
        String name_file = "log.csv";
        String name_new_dir = "\\find";*/

        String delimiter = args[0];
        String path_log = null;
        String name_file = null;
        String name_new_dir = "\\find";

        if (args[0].equals("--help")) { //передача параметра --help
            System.out.println("\n" +
                    "Запуск программы осуществляется из командной строки при помощи команды: \n" +
                    "java -jar <название jar-файла> <тип_разделителя> <путь_до_лог_файла(ов)> <имя_нового_файла>\n" +
                    "Программа в заданной директории поочередно в каждом файле осуществляет перебор строк, \nвставку разделителя между значениями и запись преобразованной строки в файл с расширением *.csv.\n" +
                    "В директории, где располагаются исходные файлы, для нового файла будет создана папка " + name_new_dir + "\n" +
                    "Если создаваемый файл уже сующествуюет в данной директории, то произдойдет перезапись файла.\n" +
                    "Для получения справки используйте параметр <--help>.\n" +
                    " ");
        } else {

            path_log = args[1];
            name_file = args[2];

            Path path_txt = Path.of(path_log + name_new_dir); //  путь до новой папки

            if (!Files.isDirectory(path_txt)) { //Проверка на наличие папки
                Files.createDirectory(path_txt); // создание новой папки
            }
            Path path_new_file = Path.of(path_log + name_new_dir + "\\" + name_file);

            if (!Files.isRegularFile(path_new_file)) { //Проверка на наличие файла
                Files.createFile(path_new_file); // создание нового файла csv
            }

            List<Path> listFiles = new ArrayList<>(); //получение списка файлов в дирректории
            try (DirectoryStream<Path> files = Files.newDirectoryStream(Path.of(path_log))) {
                for (Path path : files) {
                    listFiles.add(path);
                }
            }

            for (int i = 2; i < listFiles.size(); i++) { //получение очередной строки, преобразование и запись в файл
                List<String> list = Files.readAllLines(listFiles.get(i));

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(path_new_file)))) {
                    for (String str : list) {
                        StringBuilder strb = new StringBuilder(str);

                        if (str.matches("(?i).*" + "TRACE" + ".*")) {
                            ArrayList<Integer> ind = new ArrayList<>(Arrays.asList(11, 24, 32, 49));
                            for (Integer in : ind) {
                                strb.replace(in, in, delimiter);
                            }
                        } else {
                            if (str.matches("(?i).*" + "INFO" + ".*")) {
                                ArrayList<Integer> ind = new ArrayList<>(Arrays.asList(11, 24, 32));
                                for (Integer in : ind) {
                                    strb.replace(in, in, delimiter);
                                }
                            } else {
                                if (str.matches("(?i).*" + "WARN" + ".*")) {
                                    ArrayList<Integer> ind = new ArrayList<>(Arrays.asList(11, 24, 32, 55));
                                    for (Integer in : ind) {
                                        strb.replace(in, in, delimiter);
                                    }
                                } else {
                                    if (str.matches("(?i).*" + "DEBUG" + ".*")) {
                                        ArrayList<Integer> ind = new ArrayList<>(Arrays.asList(11, 24, 32, 45));
                                        for (Integer in : ind) {
                                            strb.replace(in, in, delimiter);
                                        }
                                    }
                                }
                            }
                        }
                        writer.write(strb + "\n");

                    /*int index = str.indexOf(delimiter);
                    if (index != -1) {
                        writer.write(str + "\n");
                    }*/
                    }
                }
            }
        }
    }
}
