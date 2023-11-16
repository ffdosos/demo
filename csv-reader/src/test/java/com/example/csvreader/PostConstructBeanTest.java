package com.example.csvreader;

import com.example.csvreader.service.CSVReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostConstructBeanTest {

    @Mock
    private CSVReaderService csvReaderService;

    @Value("${folder.path}")
    private String folderPath;

    private PostConstructBean postConstructBean;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postConstructBean = new PostConstructBean();
        postConstructBean.csvReaderService = csvReaderService;
    }

    @Test
    public void testCheckExtension() {
        // Test with a valid extension
        boolean result = postConstructBean.checkExtension("file.csv", "csv");
        assertTrue(result);

        // Test with an invalid extension
        result = postConstructBean.checkExtension("file.txt", "csv");
        assertFalse(result);
    }

    @Test
    public void testScanFile() {
        List<String> filePaths = postConstructBean.scanFile(folderPath);
        assertEquals(2, filePaths.size());
    }


}