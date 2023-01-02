package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static fr.kevin.llps.conf.event.reminder.csv.CsvProperties.DELIMITER;
import static fr.kevin.llps.conf.event.reminder.csv.CsvProperties.HEADERS;

@Service
public class EventFileParser {

    public List<CsvEvent> parse(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        CSVFormat csvFormat = CSVFormat.newFormat(DELIMITER).builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build();

        try (CSVParser parser = new CSVParser(inputStreamReader, csvFormat)) {
            return parser.stream()
                    .filter(csvRecord -> csvRecord.size() == HEADERS.length)
                    .map(this::mapToCsvEvent)
                    .toList();
        }
    }

    private CsvEvent mapToCsvEvent(CSVRecord csvRecord) {
        return CsvEvent.builder()
                .title(csvRecord.get(HEADERS[0]))
                .type(csvRecord.get(HEADERS[1]))
                .description(csvRecord.get(HEADERS[2]))
                .date(csvRecord.get(HEADERS[3]))
                .time(csvRecord.get(HEADERS[4]))
                .speaker(csvRecord.get(HEADERS[5]))
                .attendees(csvRecord.get(HEADERS[6]))
                .company(csvRecord.get(HEADERS[7]))
                .build();
    }

}
