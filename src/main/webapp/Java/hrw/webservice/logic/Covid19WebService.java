package hrw.webservice.logic;

import hrw.apicovidbot.ApiCovidBot;
import hrw.config.SwaggerConfig;
import hrw.webservice.mapper.JsonObjectMapper;
import hrw.webservice.model.JohnHDailyInfos;
import hrw.webservice.model.RKIDailyInfosTotal;
import hrw.webservice.model.endpoint.RestEndpointModel;
import hrw.webservice.rest.consumption.RestConsumption;
import hrw.webservice.rest.distribution.RestDataController;
import hrw.webservice.rest.distribution.RestErrorController;
import hrw.webservice.soap.SoapServiceImpl;
import hrw.webservice.threads.RestConsumptionThreads;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.ApplicationContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import org.telegram.telegrambots.starter.TelegramBotInitializer;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

/**
 * Covid19WebService is the main class of the web service.
 * The class holds all the objects important for the flow and handles
 * the communication from the side thread and the main thread.
 *
 * @author Lars Karbach, Philip Klein, Furkan Kilic
 * @version 1.0
 * @since 2021-02-16
 */

@ComponentScan(basePackageClasses = RestErrorController.class)
@ComponentScan(basePackageClasses = RestEndpointModel.class)
@ComponentScan(basePackageClasses = WebServiceEndpointHandler.class)
@ComponentScan(basePackageClasses = RestDataController.class)
@SpringBootApplication
@ComponentScan(basePackageClasses = SwaggerConfig.class)

public class Covid19WebService extends SpringBootServletInitializer {

    /**
     * RESTConsumption is used to send requests to
     * the Robert Koch Institute and John Hopkins University within the beside thread.
     */
    private RestConsumption restConsumption;

    /**
     * The timer object is used to control the execution of the next thread to ensure
     * regular retrieval of Robert Koch Institute and John Hopkins University data.
     */
    private Timer consumptionTimer;

    /**
     * The Jackson Mapper object is used within the web service to serialize and deserialize
     * objects /lists to json and vice versa.
     */
    private JsonObjectMapper jacksonObjMapper;

    /**
     * The list includes all days since John Hopkins University records began.
     */
    private List<JohnHDailyInfos> jHDailyInfosList;

    /**
     * Since the start of the service, the list includes the daily data of the Robert Koch Institute
     */
    private List<RKIDailyInfosTotal> rkiTotalData;

    /**
     * The RestController handles all the functionality of the REST API endpoints.
     * Within the controller, the WebService REST URLs are mapped to the respective functionalities.
     */
    private RestDataController restController;

    /**
     * One of the calculation objects to process the daily data of the JHI and provide it through the endpoints.
     */
    private CalculateRKIKeyFigures calcRKIKeyFigures;

    /**
     * The other of the calculation objects to process the daily data of the RKI and provide it through the endpoints.
     */
    private CalculateJHKeyFigures calcJHKeyFigures;

    /**
     * The Endpoint Handler works as a middleman between the endpoints and the computations.
     */
    private WebServiceEndpointHandler handler;

    /**
     * The SoapServiceImpl provides the functionality of the SOAP endpoints.
     */
    private static SoapServiceImpl soapService;

    /**
     * The static instance of the web service is needed to pass the data available in the web service to the SOAP
     * implementation. This must be implemented due to the technical constraints of Javax.
     */
    private static Covid19WebService instance = new Covid19WebService();

    /**
     * Basic getter returning the SOAP service
     *
     * @return SOAP service
     */
    public SoapServiceImpl getSoapService() {
        return soapService;
    }

    /**
     * Constructor of the WebService in which all required objects are created.
     */
    public Covid19WebService() {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new ApiCovidBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("Unexpected error occured! Service couldn't be started.");
        }

        restConsumption = new RestConsumption();
        handler = new WebServiceEndpointHandler(this);
        restController = new RestDataController(this);
        soapService = new SoapServiceImpl(this);
        rkiTotalData = Collections.synchronizedList(new ArrayList<>());
        calcRKIKeyFigures = new CalculateRKIKeyFigures();
        calcJHKeyFigures = new CalculateJHKeyFigures();
        jacksonObjMapper = new JsonObjectMapper();
        consumptionTimer = new Timer();

        // Timer runs sub thread every 24 hours to update RKI and JHI data.
        consumptionTimer.schedule(new RestConsumptionThreads(restConsumption, this), 1000, 86400000);

    }

    /**
     * Basic getter to returns the static CovidWebService Object.
     * Maintains encapsulation
     *
     * @return static CovidWebService Object (Covid19WebService)
     */
    public static Covid19WebService getInstance() {
        return instance;
    }

    /**
     * Sets an instance  of static Covid19WebService
     *
     * @param instance instance of Covid19WebService
     */
    public static void setInstance(Covid19WebService instance) {
        Covid19WebService.instance = instance;
    }

    /**
     * Basic getter to returns JsonObjectMapper Object.
     * Maintains encapsulation
     *
     * @return JsonObjectMapper
     */
    public JsonObjectMapper getJacksonObjMapper() {
        return jacksonObjMapper;
    }

    /**
     * Basic getter to returns the RKI calculation Object.
     * Maintains encapsulation
     *
     * @return CalculateRKIKeyFigures
     */
    public CalculateRKIKeyFigures getCalcRKIKeyFigures() {
        return calcRKIKeyFigures;
    }

    /**
     * Basic getter to returns the JHK calculation Object.
     * Maintains encapsulation
     *
     * @return CalculateJHKeyFigures
     */
    public CalculateJHKeyFigures getCalcJHKeyFigures() {
        return calcJHKeyFigures;
    }

    /**
     * Basic getter to returns the RKI TotalData-List Object.
     * Maintains encapsulation
     *
     * @return List of total daily RKI information
     */
    public List<RKIDailyInfosTotal> getRkiTotalData() {
        return rkiTotalData;
    }

    /**
     * Basic setter to set the JohnHDailyInfo List as a class variable.
     * Maintains encapsulation
     *
     * @param jHDailyInfosList The current list from the John Hopkins Institute is passed as input.
     */
    public void setJHDailyInfosList(List<JohnHDailyInfos> jHDailyInfosList) {
        this.jHDailyInfosList = Collections.synchronizedList(jHDailyInfosList);
    }

    /**
     * Basic getter to returns the EndpointHandler Object.
     * Maintains encapsulation
     *
     * @return WebServiceEndpointHandler
     */
    public WebServiceEndpointHandler getHandler() {
        return handler;
    }

    /**
     * Sets WebServiceEndpointHandler
     *
     * @param handler passed WebServiceEndpointHandler
     */
    public void setHandler(WebServiceEndpointHandler handler) {
        this.handler = handler;
    }

    /**
     * Basic getter to returns the JHDailyInfoList.
     * Maintains encapsulation
     *
     * @return List of total daily Johns Hopkins information
     */
    public List<JohnHDailyInfos> getJHDailyInfosList() {
        return jHDailyInfosList;
    }

    /**
     * Main method of the web service, launches the Telegram bot, as well as the soap- and rest-endpoint.
     *
     * @param args Contains the supplied command-line arguments as an array of String objects
     */
    public static void main(String[] args) {
        //Endpoint.publish("http://localhost:8080/c19service", soapService);
        SpringApplication app = new SpringApplication(Covid19WebService.class);
        app.run(args);

    }

    /**
     * This method uses SpringApplicationBuilder to register our class as the application's configuration class
     *
     * @param builder Is needed to register our class as configuration class
     * @return The Method returns a SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Covid19WebService.class);
    }
}