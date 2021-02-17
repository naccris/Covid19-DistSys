package hrw.apicovidbot;

import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * The ApiCovidBot class contains the main Telegram Polling Bot functionalities.
 * Received and sent messages are being handled.
 *
 * @author Daniel Muschiol
 * @version 1.0
 * @since 2021-02-16
 */
public class ApiCovidBot extends TelegramLongPollingBot {

    /**
     * The JSONObject json holds responses of the REST and SOAP webservices
     */
    JSONObject json;

    /**
     * The String rootUrl holds the base of to-be-called REST-urls
     */
    String rootUrl = "http://tomcat.nxuz.de:8080/Covid19-DisSys";

    /**
     * The onUpdateReceived method handles incoming messages/ commands from Telegram users.
     * Depending on which message/  command has been sent, this methode invokes a suitable response.
     *
     * @param update The Telegram-Update-Object which holds all the information of the current conversation.
     */
    @Override
    public void onUpdateReceived(Update update) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (update.hasMessage()) {

                    String command = update.getMessage().getText(); // Bot-Kommando
                    Integer calcDays; // Anzahl der zu berechnenden Tage
                    SoapConsumer soapConsumer = new SoapConsumer();

                    switch (command) {
                        case "/help":
                            sendResponse(update, "Format und Inhalt der Statistik-Kommandos: \n \n"
                                    + "1. Daten der John Hopkins Universität \n"
                                    + "/r1a - Neuinfektionen in den letzten 24h (Rest) \n"
                                    + "/s1a - Neuinfektionen in den letzten 24h (Soap) \n" + "/r1b - Gesamtinfektionen (Rest) \n"
                                    + "/s1b - Gesamtinfektionen (Soap) \n" + "/r1c - Anstieg der letzten 24h (Rest)\n"
                                    + "/s1c - Anstieg der letzten 24h (Soap) \n"
                                    + "/r1d {n} - Durchschnittlicher Anstieg der letzten {n} Tage (Für 'n' bitte eine ganze positive Zahl wählen.) (Rest) \n"
                                    + "/s1d {n} - Durchschnittlicher Anstieg der letzten {n} Tage (Für 'n' bitte eine ganze positive Zahl wählen.) (Soap) \n \n"
                                    + "2. Daten des RKI: \n" + "/r2a - Inzidenz-Wert für Gesamtdeutschland (Rest) \n"
                                    + "/s2a - Inzidenz-Wert für Gesamtdeutschland (Soap) \n" + "/r2b - Ziel-Gesamtinfektion (Rest) \n"
                                    + "/s2b - Ziel-Gesamtinfektion (Soap) \n"
                                    + "/r2c - Vorhersage über die verbleibenden Lockdown-Tage (Rest) \n"
                                    + "/s2c - Vorhersage über die verbleibenden Lockdown-Tage (Soap) \n"
                                    + "/rallstats {n} - Alle Statistiken (Für {n} bitte Anzahl der Tage angeben) (Rest) \n"
                                    + "/sallstats {n} - Alle Statistiken (Für {n} bitte Anzahl der Tage angeben) (Soap) ");
                            break;
                        case "/start":
                            sendResponse(update, "Schreibe /help für die Kommando-Übersicht.");
                            break;
                        case "/r1a":
                            json = getCovidStatsRest(rootUrl + "/covid/data/infections?filter=latest");
                            sendResponse(update, "Neuinfektionen in den letzten 24h: " + json.get("newInfections24H").toString());
                            break;
                        case "/s1a":
                            json = soapConsumer.getNewInfectionsLast24h();
                            System.out.println(json.toString());
                            sendResponse(update,
                                    "Neuinfektionen in den letzten 24h: " + json.get("newInfections24H").toString());
                            break;
                        case "/r1b":
                            json = getCovidStatsRest(rootUrl + "/covid/data/infections?filter=total");
                            sendResponse(update, "Gesamtinfektionen: " + json.get("totalInfections").toString());
                            break;
                        case "/s1b":
                            json = soapConsumer.getTotalInfections();
                            System.out.println(json.toString());
                            sendResponse(update, "Gesamtinfektionen: " + json.get("totalInfections").toString());
                            break;
                        case "/r1c":
                            json = getCovidStatsRest(rootUrl + "/covid/data/infections/raised?days=1");
                            sendResponse(update, "Anstieg der letzten 24h: " + json.get("raisedInfections").toString());
                            break;
                        case "/s1c":
                            json = soapConsumer.getIncInfection24h();
                            sendResponse(update, "Anstieg der letzten 24h: " + json.get("raisedInfections").toString());
                            break;
                        case "/r2a":
                            json = getCovidStatsRest(rootUrl + "/covid/data/incidences");
                            sendResponse(update, "Inzidenz-Wert für Gesamtdeutschland: " + json.get("incidence").toString());
                            break;
                        case "/s2a":
                            json = soapConsumer.getIncidence();
                            System.out.println(json.toString());
                            sendResponse(update, "Inzidenz-Wert für Gesamtdeutschland: " + json.get("incidence").toString());
                            break;
                        case "/r2b":
                            json = getCovidStatsRest(rootUrl + "/covid/data/infections?filter=target");
                            sendResponse(update, "Ziel-Gesamtinfektion: " + json.get("targetInfection").toString());
                            break;
                        case "/s2b":
                            json = soapConsumer.getTargetInfections();
                            System.out.println(json.toString());
                            sendResponse(update, "Ziel-Gesamtinfektion: " + json.get("targetInfection").toString());
                            break;
                        case "/r2c":
                            json = getCovidStatsRest(rootUrl + "/covid/data/forecast");
                            sendResponse(update,
                                    "Vorhersage über die verbleibenden Lockdown-Tage: " + json.get("remainingLockdown").toString());
                            break;
                        case "/s2c":
                            json = soapConsumer.getForecastLockdown();
                            System.out.println(json.toString());
                            sendResponse(update,
                                    "Vorhersage über die verbleibenden Lockdown-Tage: " + json.get("remainingLockdown").toString());
                            break;
                    }

                    if (command.matches("/r1d.*")) {
                        try {
                            calcDays = Integer.parseInt(command.split(" ")[1]);
                            System.out.println(calcDays);
                            if (calcDays > 1 && calcDays < 366) {
                                json = getCovidStatsRest(rootUrl + "/covid/data/infections/raised?days=" + calcDays);
                                sendResponse(update, "Durchschnittlicher Anstieg der letzten " + calcDays + " Tage: "
                                        + json.get("averageRaise").toString());
                            } else {
                                sendResponse(update, "Bitte einen Wert zwischen 2 und  365 für /r1d {Anzahl Tage} eingeben.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResponse(update, "Bitte gültigen Parameter für /r1d {Anzahl Tage} eingeben (ganze Zahl).");
                        }
                    }

                    if (command.matches("/s1d.*")) {
                        try {
                            calcDays = Integer.parseInt(command.split(" ")[1]);
                            System.out.println(calcDays);
                            if (calcDays > 1 && calcDays < 366) {
                                json = soapConsumer.getAverageInc(calcDays);
                                sendResponse(update, "Durchschnittlicher Anstieg der letzten " + calcDays + " Tage: "
                                        + json.get("averageRaise").toString());
                            } else {
                                sendResponse(update, "Bitte einen Wert zwischen 2 und  365 für /s1d {Anzahl Tage} eingeben.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResponse(update, "Bitte gültigen Parameter für /s1d {Anzahl Tage} eingeben (ganze Zahl).");
                        }
                    }

                    if (command.matches("/rallstats.*")) {
                        try {
                            calcDays = Integer.parseInt(command.split(" ")[1]); // Versuch Integer aus Eingabeparameter zu parsen.
                            if (calcDays > 1 && calcDays < 365) {
                                json = getCovidStatsRest(rootUrl + "/covid/data?days=" + calcDays);
                                sendResponse(update,
                                        "Daten der John Hopkins Universität: \n" + "Neuinfektionen letzte 24h: "
                                                + json.get("newInfections24H").toString() + "\n" + "Gesamtinfektionen:  "
                                                + json.get("totalInfections").toString() + "\n" + "Anstieg letzte 24h: "
                                                + json.get("raisedInfections").toString() + "\n" + "Durchschn. Anstieg letzte "
                                                + calcDays + " Tage: " + json.get("averageRaise").toString() + "\n \n"
                                                + "Daten des RKI: \n" + "Inzidenz-Wert Deutschland: " + json.get("incidence").toString()
                                                + "\n" + "Ziel-Gesamtinfektion: " + json.get("targetInfection").toString() + "\n"
                                                + "Vorhersage über die verbleibenden Lockdown-Tage: "
                                                + json.get("remainingLockdown").toString() + "\n \n" + "Danke der Nachfrage.");
                            } else {
                                sendResponse(update, "Bitte einen Wert zwischen 2 und  365 für /rallstats {Anzahl Tage} eingeben.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResponse(update, "Bitte gültige Parameter für /rallstats {Anzahl Tage} eingeben.");
                        }
                    }

                    if (command.matches("/sallstats.*")) {
                        try {
                            calcDays = Integer.parseInt(command.split(" ")[1]); // Versuch Integer aus Eingabeparameter zu parsen.
                            if (calcDays > 1 && calcDays < 365) {
                                json = soapConsumer.getTotalData(calcDays);
                                sendResponse(update,
                                        "Daten der John Hopkins Universität: \n" + "Neuinfektionen letzte 24h: "
                                                + json.get("newInfections24H").toString() + "\n" + "Gesamtinfektionen:  "
                                                + json.get("totalInfections").toString() + "\n" + "Anstieg letzte 24h: "
                                                + json.get("raisedInfections").toString() + "\n" + "Durchschn. Anstieg letzte "
                                                + calcDays + " Tage: " + json.get("averageRaise").toString() + "\n \n"
                                                + "Daten des RKI: \n" + "Inzidenz-Wert Deutschland: " + json.get("incidence").toString()
                                                + "\n" + "Ziel-Gesamtinfektion: " + json.get("targetInfection").toString() + "\n"
                                                + "Vorhersage über die verbleibenden Lockdown-Tage: "
                                                + json.get("remainingLockdown").toString() + "\n \n" + "Danke der Nachfrage.");
                            } else {
                                sendResponse(update, "Bitte einen Wert zwischen 2 und  365 für /sallstats {Anzahl Tage} eingeben.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendResponse(update, "Bitte gültige Parameter für /sallstats {Anzahl Tage} eingeben.");
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * The getCovidStatsRest methode creates a new instance of the RestConsumer class to communicate with the REST-API
     *
     * @param url The to-be-called URL-String
     * @return covidStats A JSONObect containing The REST-API response
     */
    public JSONObject getCovidStatsRest(String url) {
        JSONObject covidStats;
        covidStats = new RestConsumer(url).getCovidStats();
        return covidStats;
    }

    /**
     * The sendResponse method creates and sends a new message as a response to the Telegram user.
     *
     * @param update          The Telegram-Update-Object which holds all the information of the current conversation.
     * @param responseMessage A String of the to-be-sent response.
     */
    public void sendResponse(Update update, String responseMessage) {
        SendMessage message = new SendMessage(); // (Leere) Antwort des Bots
        message.setChatId(update.getMessage().getChatId().toString()); // Chat-ID der aktuellen Eingabe
        message.setText(responseMessage); // Antwort füllen
        try {
            execute(message); // Antwort absenden
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * A getter method holding the bots Telegram username
     *
     * @return the bots Telegram username
     */
    @Override
    public String getBotUsername() {
        return "covid_neunzehn_bot";
    }

    /**
     * A getter method holding the bots Telegram token
     *
     * @return the bots Telegram token
     */
    @Override
    public String getBotToken() {
        return "1648836186:AAGmJOV-vZKpBSUUMZYiWMrfvZwmKSkx1aY";
    }

}