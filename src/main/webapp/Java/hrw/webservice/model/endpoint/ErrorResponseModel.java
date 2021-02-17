package hrw.webservice.model.endpoint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Error Response Model holds the error data that is returned when a request fails.
 *
 * @author Lars Karbach
 * @version 1.0
 * @since 2021-02-16
 */

public class ErrorResponseModel {

    /**
     * Holds the timestamp when the error occurred.
     */
    private String timestampe;

    /**
     * Holds the status code, what kind of error occurred.
     */
    private int status;

    /**
     * Holds the error message, what kind of error occurred.
     */
    private String message;

    /**
     * Constructor that creates an ErrorResponseModel.
     *
     * @param status  Indicates the status code
     * @param message The specific error message
     */
    public ErrorResponseModel(int status, String message) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss:z");
        Date date = new Date(System.currentTimeMillis());

        this.timestampe = format.format(date);
        this.status = status;
        this.message = message;
    }

    /**
     * Basic getter to return timestamp
     *
     * @return timestamp
     */
    public String getTimestampe() {
        return timestampe;
    }

    /**
     * Basic setter to set timestamp
     *
     * @param timestampe sets timestamp
     */
    public void setTimestampe(String timestampe) {
        this.timestampe = timestampe;
    }

    /**
     * Basic getter to return status
     *
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Basic setter to set status
     *
     * @param status current status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Basic getter to return message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Basic setter to set message
     *
     * @param message current error message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
