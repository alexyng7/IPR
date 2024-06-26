import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class JavaPars {
    public static void main(String[] args) throws IOException {
        // аргументы для отладки
        String key_word = "WARN";
        String path_log = "c:\\q";
        String name_file = "log.txt";
        String name_new_dir = "\\find";

        Path path_txt = Path.of(path_log + name_new_dir); //  путь до новой папки

        if (!Files.isDirectory(path_txt)) { //Проверка на наличие папки
            Files.createDirectory(path_txt); // создание новой папки
        }
        Path path_new_file = Path.of(path_log + name_new_dir + "\\" + name_file); // путь до нового файла

        if (!Files.isRegularFile(path_new_file)) { //Проверка на наличие файла
            Files.createFile(path_new_file); // создание нового файла csv
        }
        //Добавить проверку наличия файлов

        List<Path> listFiles = new ArrayList<>(); //получение списка файлов в дирректории
        try (DirectoryStream<Path> files = Files.newDirectoryStream(Path.of(path_log))) {
            for (Path path : files) {
                listFiles.add(path);
            }
        }
        for (int i = 2; i < listFiles.size(); i++) { //поиск нужных строк и запись в файл
            List<String> list = Files.readAllLines(listFiles.get(i)); //получаем список строк из файла
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(path_new_file)))) {
                for (String str : list) {
                    //if (str.toLowerCase().contains(key_word.toLowerCase())) {
                    if (str.matches("(?i).*" + key_word + ".*")) {
                        writer.write(str + "\n");
                    }
                }
            }
        }
        File file = new File(path_new_file.toString()); // проверка веса файла. если вес равен 0, то поиск не нашел ничего подходящего
        long fileSize = file.length();
        if (fileSize == 0){
            Files.delete(path_new_file); // удаляет пустой файл
            Files.delete(path_txt); // удаляет пустую папку
            System.out.println("Совпадений не найдено. Проверьте корректрность воода.");
        } else System.out.println("Поиск окончен. Расположение файла: " + path_new_file);
    }
}
