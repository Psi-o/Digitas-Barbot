package be.pix.pewpewbar.data;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileManager {

    void store(MultipartFile file);

    void store(MultipartFile[] files);

    String[] getFilelist();

    String getFileContent(String file);

    List<String> getStringsFromFile(String fileName);

    void deleteFileBecauseNoOneLikesIt(String cocktailName);
}
