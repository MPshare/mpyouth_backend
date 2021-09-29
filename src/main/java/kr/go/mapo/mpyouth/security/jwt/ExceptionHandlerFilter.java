package kr.go.mapo.mpyouth.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.go.mapo.mpyouth.common.ExceptionEnum;
import kr.go.mapo.mpyouth.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (CustomJwtException ex){
            logger.error("exception exception handler filter");
            setErrorResponse(response,ex.getExceptionEnum());
        } catch (RuntimeException ex){
            logger.error("runtime exception exception handler filter");
            setErrorResponse(response,ExceptionEnum.RUNTIME_EXCEPTION);
        }
    }

//    public void setErrorResponse(HttpStatus status, HttpServletResponse response,Throwable ex){
      public void setErrorResponse(HttpServletResponse response,ExceptionEnum exceptionEnum){
        response.setStatus(exceptionEnum.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(exceptionEnum);


        try{
            String json = convertObjectToJson(errorResponse);
            System.out.println(json);
            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
