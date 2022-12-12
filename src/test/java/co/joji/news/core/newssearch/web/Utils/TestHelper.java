package co.joji.news.core.newssearch.web.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;


@Slf4j
public class TestHelper {


    public static String getResourceFileAsString(String fileName) throws Exception {


        File file = ResourceUtils.getFile("classpath:data/" + fileName);
        var content = new String(Files.readAllBytes(file.toPath()));


        return content;


    }

    public static <T> T getObjectFromjson(String fileName, Class<T> type)  throws Exception {

        String json = getResourceFileAsString(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        T result = null;

        result = objectMapper.readValue(json, type);

        return result;

    }
}
