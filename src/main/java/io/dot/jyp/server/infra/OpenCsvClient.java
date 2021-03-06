package io.dot.jyp.server.infra;

import com.opencsv.bean.CsvToBeanBuilder;
import io.dot.jyp.server.domain.FileIoClient;
import io.dot.jyp.server.domain.NicknameGenerator;
import io.dot.jyp.server.infra.dto.NicknameCsvObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@Qualifier("OpenCsvClient")
public class OpenCsvClient implements FileIoClient {

    @Override
    public NicknameGenerator readCsvFile(String path) {
        NicknameGenerator nicknameLists = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(path));) {
            List<NicknameCsvObject> nicknameObjects = new CsvToBeanBuilder(reader)
                    .withType(NicknameCsvObject.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build().parse();
            reader.close();
            nicknameLists = NicknameGenerator.of(
                    nicknameObjects.stream().map(NicknameCsvObject::getFirst).collect(Collectors.toList()),
                    nicknameObjects.stream().map(NicknameCsvObject::getSecond).collect(Collectors.toList())
            );
            return nicknameLists;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nicknameLists;
    }

    @Override
    public Map<String, String> write(String path) throws IOException {
        return null;
    }

}
