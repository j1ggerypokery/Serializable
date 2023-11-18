import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static StringBuilder builder = new StringBuilder();

    public static void main(String[] args) {
        List<String> directories = Arrays.asList(
                "Users/Oleg/Desktop/Games/src",
                "Users/Oleg/Desktop/Games/res",
                "Users/Oleg/Desktop/Games/savegames",
                "Users/Oleg/Desktop/Games/temp",
                "Users/Oleg/Desktop/Games/src/main",
                "Users/Oleg/Desktop/Games/src/test",
                "Users/Oleg/Desktop/Games/res/drawables",
                "Users/Oleg/Desktop/Games/res/vectors",
                "Users/Oleg/Desktop/Games/res/icons"
        );
        for (String directory : directories) {
            createDirectory(directory);
        }

        List<String> files = Arrays.asList(
                "Main.java",
                "Utils.java"
                //        "temp.txt" // - убрала, чтобы не создавать файл temptemp.txt
        );
        List<String> path = Arrays.asList(
                "Users/Oleg/Desktop/Games/src/main",
                "Users/Oleg/Desktop/Games/src/main"
                //        "Users/Oleg/Desktop/Games/temp" // - убрала, чтобы не создавать файл temptemp.txt
        );
        for (int i = 0; i < files.size(); i++) {
            createFile(path.get(i), files.get(i));
        }

        GameProgress gameProgress = new GameProgress(94, 10, 2, 230);
        saveGame("Users/Oleg/Desktop/Games/savegames/save.dat", gameProgress);
        zipFiles("Users/Oleg/Desktop/Games/savegames/zip-output.zip", "Users/Oleg/Desktop/Games/savegames/save.dat");

        GameProgress gameProgress1 = new GameProgress(90, 20, 4, 500);
        saveGame("Users/Oleg/Desktop/Games/savegames/save.dat", gameProgress);
        zipFiles("Users/Oleg/Desktop/Games/savegames/zip-output.zip", "Users/Oleg/Desktop/Games/savegames/save.dat");

        GameProgress gameProgress2 = new GameProgress(50, 15, 5, 600);
        saveGame("Users/Oleg/Desktop/Games/savegames/save.dat", gameProgress);
        zipFiles("Users/Oleg/Desktop/Games/savegames/zip-output.zip", "Users/Oleg/Desktop/Games/savegames/save.dat");

    }


    public static void createDirectory(String directories) {
        File dir = new File(directories);
        if (dir.mkdirs())
            System.out.println("Каталог создан " + directories);
            //     builder.append("Каталог" + directories + "создан"); // - лучше System.out.println, тк выводит запись создан/не создан
        else {
            System.out.println("Каталог" + directories + " не создан");
            //    builder.append("Каталог" + directories + "НЕ создан");
        }
        builder.append("\n");
    }

    public static void createFile(String path, String fileName) {
        File myFile = new File(path + fileName);
        try {
            if (myFile.createNewFile())
                System.out.println(fileName + " Файл был создан");
            else {
                System.out.println(fileName + " Файл НЕ был создан");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        try (FileWriter writer = new FileWriter("Users/Oleg/Desktop/Games/temp/temp.txt")) {
            writer.write(builder.toString());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, String object) {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream("zip_output.zip"));
             FileInputStream fis = new FileInputStream("save.dat")) {
            ZipEntry entry = new ZipEntry("packed_save.dat");
            zout.putNextEntry(entry);
// считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
// добавляем содержимое к архиву
            zout.write(buffer);
// закрываем текущую запись для новой записи
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


        public static void saveGame (String path, Object gameProgress) {
        createFile(path,"Game Progress");
            try (FileOutputStream fos = new FileOutputStream("save.dat");
// перевод строки в массив байтов
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
// запишем экземпляр класса в файл
                oos.writeObject(gameProgress);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
}


