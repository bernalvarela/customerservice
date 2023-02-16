package com.bernalvarela.customerservice.application.service;

import com.bernalvarela.customerservice.application.vo.ImageVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
@Service
public class ImageService {

    private static final String FILES_DIRECTORY = "/tmp/";

    public String uploadImage(ImageVO image) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(FILES_DIRECTORY + image.getName())) {
            stream.write(image.getContent());
        } catch (IOException e) {
            throw e;
        }
        return image.getName();
    }

    public void deleteImage(String name) throws IOException {
        Files.delete(Paths.get("/tmp/" + name));
    }
}
