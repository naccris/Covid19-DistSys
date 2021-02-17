package hrw.webservice.rest.distribution;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hrw.webservice.model.endpoint.ErrorResponseModel;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * REST Error Controller for unexpected Exceptions
 * Annotation in Spring boot and Swagger documentation
 *
 * @author Lars Karbach
 * @version 1.0
 * @since 2021-02-16
 */


@Controller
public class RestErrorController implements ErrorController {

    /**
     * @param request the rest webservice endpoints
     * @return ErrorResponseModel with the given code and exception
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during serialization/deserialization.
     */
    @RequestMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request, Exception exception) throws JsonProcessingException {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(new ErrorResponseModel(statusCode, exception.toString()));
    }

    /**
     * Basic getter to return the error path
     *
     * @return error Path
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
