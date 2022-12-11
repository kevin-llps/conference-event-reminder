package fr.kevin.llps.conf.event.reminder.service;

import fr.kevin.llps.conf.event.reminder.domain.Speaker;
import fr.kevin.llps.conf.event.reminder.domain.Talk;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TalkFileParser {

    private static final String DELIMITER = ";";

    public List<Talk> parse(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        List<Talk> talks = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] talkInfos = line.split(DELIMITER);
                talks.add(mapToTalk(talkInfos));
            }
        }

        return talks;
    }

    private Talk mapToTalk(String[] talkInfos) {
        return new Talk(talkInfos[0], talkInfos[1], mapToLocalDateTime(talkInfos[2], talkInfos[3]), mapToSpeaker(talkInfos[4]));
    }

    private LocalDateTime mapToLocalDateTime(String date, String time) {
        return LocalDateTime.parse(mapToLocalDateTimeString(date, time));
    }

    private String mapToLocalDateTimeString(String date, String time) {
        String[] dateParts = date.split("/");

        String localDate = String.join("-", dateParts[2], dateParts[1], dateParts[0]);

        return localDate + "T" + time;
    }

    private Speaker mapToSpeaker(String speakerInfos) {
        String[] speakerParts = speakerInfos.split(" ");

        return new Speaker(speakerParts[0], speakerParts[1]);
    }

}
