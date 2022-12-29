package fr.kevin.llps.conf.event.reminder.samples;

import fr.kevin.llps.conf.event.reminder.csv.CsvEvent;

import java.util.List;

public class CsvEventSample {

    public static List<CsvEvent> csvEventList() {
        CsvEvent cognito = CsvEvent.builder()
                .title("AWS Cognito")
                .type("Talk")
                .description("Après 2 ans à travailler sur la mise en place de cette solution au PMU, kevin llps nous présentera son retour d'expérience en détaillant les points forts et les points faibles de Cognito.")
                .date("13/10/2022")
                .time("19:45:00")
                .speaker("kevin llps")
                .build();

        CsvEvent chatBots = CsvEvent.builder()
                .title("Les chatbots")
                .type("Talk")
                .description("Les chatbots sont un peu partout aujourd'hui. Voyons le pourquoi du comment et réalisons-en un avec Google DialogFlow, pour aider nos chers formateurs à se libérer des questions les plus fréquentes.")
                .date("13/10/2022")
                .time("19:00:00")
                .speaker("charlie lcs")
                .build();

        CsvEvent videoGames = CsvEvent.builder()
                .title("Mécanismes d'un bon jeu vidéo")
                .type("Talk")
                .description("Passionné de jeux vidéo ou juste curieux de comprendre pourquoi ton copain ou ta copine passe toute ses soirées devant son écran au lieu de venir regarder Top Chef avec toi ? Je te conseille de venir écouter cette conférence pour comprendre une partie des mystères qui entourent l'univers très vaste du jeu vidéo.")
                .date("13/10/2022")
                .time("21:00:00")
                .speaker("iza elk")
                .build();

        CsvEvent firstPartAwsLambda = CsvEvent.builder()
                .title("AWS Lambda")
                .type("Talk")
                .description("A travers ce retour d'expérience, kevin llps nous présentera le service AWS Lambda et dans quel contexte l'utiliser.")
                .date("24/10/2022")
                .time("19:00:00")
                .speaker("kevin llps")
                .build();

        CsvEvent secondPartAwsLambda = CsvEvent.builder()
                .title("AWS Lambda - Partie 2")
                .type("Talk")
                .description("Deuxième partie de la présentation sur les lambdas AWS")
                .date("09/02/2023")
                .time("19:00:00")
                .speaker("kevin llps")
                .build();

        return List.of(cognito, chatBots, videoGames, firstPartAwsLambda, secondPartAwsLambda);
    }

}
