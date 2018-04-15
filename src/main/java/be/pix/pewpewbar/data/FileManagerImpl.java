package be.pix.pewpewbar.data;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.IllegalPathStateException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Component
public class FileManagerImpl implements FileManager {


    private static Path basePath;

    public FileManagerImpl(){
        basePath = Paths.get("./cocktails");
        if(!Files.exists(basePath)) {
            try {
                Files.createDirectory(Paths.get("./cocktails"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            basePath = Paths.get("./cocktails");
        }
    }

    @Override
    public void store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Failed to addCocktails empty file " + file.getOriginalFilename());
        }else {
            storeFile(file);
        }

    }

    private void storeFile(MultipartFile file){
        Path pathToCopy = basePath.resolve(indexFilename(file,basePath));
        try {
            Files.copy(file.getInputStream(),pathToCopy);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to copy file");
        }
    }


    private String indexFilename(MultipartFile file, Path path) {
        String newFilename = file.getOriginalFilename();
        int index = 1;
        String tempName = removeExtension(newFilename);
        while (Files.exists(path.resolve(newFilename))) {
            newFilename = tempName + "(" + 1 + ")" + getExtension(file.getOriginalFilename());
            if (Files.exists(path.resolve(newFilename))) {
                newFilename = tempName + "(" + ++index + ")" + getExtension(file.getOriginalFilename());
            }
        }
        return newFilename;
    }


    private String getExtension(String file) {
        for (int i = file.length() - 1; i > 0; i--) {
            if (file.charAt(i) == '.') {
                return file.substring(i, file.length());
            }
        }
        return file;
    }


    private String removeExtension(String file) {
        for (int i = file.length() - 1; i > 0; i--) {
            if (file.charAt(i) == '.') {
                return file.substring(0, i);
            }
        }
        return file;
    }



    @Override
    public void store(MultipartFile[] files) {
        for(MultipartFile f: files){
            store(f);
        }
    }

    @Override
    public String[] getFilelist() {
        File parent = basePath.toFile();
        if(parent==null) {
            throw new IllegalStateException("Basepath shouldn't be null.");
        }
        if(parent.listFiles()==null){
            return new String[0];
        }

        String[] fileNames = new String[parent.listFiles().length];
        File[] files = parent.listFiles();
        for (int i = 0; i < fileNames.length ; i++) {
            fileNames[i] = files[i].getName();
        }
        return fileNames;
    }

    @Override
    public String getFileContent(String file) {
        return null;
    }

    @Override
    public List<String> getStringsFromFile(String fileName) {
        List<String> stringList = new ArrayList<>();
        Path filePath = basePath.resolve(fileName);
        if(Files.exists(filePath)){
            File file = filePath.toFile();
            try (Scanner input = new Scanner(file)){
                while(input.hasNext()) {
                    stringList.add(input.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else throw new IllegalPathStateException("File path not found.");
        return stringList;
    }

    @Override
    public void deleteFileBecauseNoOneLikesIt(String cocktailName) {
        try {
            Files.delete(basePath.resolve(cocktailName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
