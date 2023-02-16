package com.bernalvarela.customerservice.application.service;

import com.bernalvarela.customerservice.application.vo.ImageVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    private static ImageService service;

    private static ImageVO IMAGE_VO;

    static {
        try {
            IMAGE_VO = ImageVO.builder()
                    .name("A_file")
                    .content(Objects.requireNonNull(
                            ImageServiceTest.class.getClassLoader().getResourceAsStream("photo.jpg")).readAllBytes())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void init() {
        service = new ImageService();
    }

    @Test
    void shouldCreateAnImage() {
        try {
            service.uploadImage(IMAGE_VO);
            byte[] array = Files.readAllBytes(Paths.get("/tmp/" + IMAGE_VO.getName()));

            assertArrayEquals(array, IMAGE_VO.getContent());

            Files.delete(Paths.get("/tmp/" + IMAGE_VO.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldDeleteAnImage() {
        try {
            service.uploadImage(IMAGE_VO);
            service.deleteImage(IMAGE_VO.getName());

            assertThrows(NoSuchFileException.class, () -> {
                Files.readAllBytes(Paths.get("/tmp/" + IMAGE_VO.getName()));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldDeleteAnInexistentImage() {
        assertThrows(NoSuchFileException.class, () -> {
            service.deleteImage(IMAGE_VO.getName());
        });
    }
}
