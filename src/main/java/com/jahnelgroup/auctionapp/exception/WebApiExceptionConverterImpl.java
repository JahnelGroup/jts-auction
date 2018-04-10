package com.jahnelgroup.auctionapp.exception;

import com.jahnelgroup.auctionapp.auditing.context.RequestContextService;
import com.jahnelgroup.auctionapp.exception.message.ApiErrorInterpolator;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.Field;

@Component
@AllArgsConstructor
public class WebApiExceptionConverterImpl implements WebApiExceptionConverter {

    private RequestContextService requestContextService;
    private ApiErrorInterpolator interpolator;

    @Override
    public ResponseEntity<ApiError> convert(ResponseEntity resp, Exception ex, BindingResult bindingResult) throws IllegalAccessException {
        ApiError err = new ApiError(resp.getStatusCode(), ex.getLocalizedMessage(), ex.getClass().getSimpleName());

        if ( bindingResult != null ) {
            err.setObjectErrors(bindingResult.getGlobalErrors());
            err.setFieldErrors(bindingResult.getFieldErrors());
        }

        err.setTimestamp(requestContextService.getTimestamp());
        err.setUuid(requestContextService.getUuid());

        if( err.getFieldErrors() != null ){
            for(FieldError e : err.getFieldErrors()){
                String message = interpolator.interpolate(e);
                if( message != null ){
                    setDefaultMessage(e, message);
                }
            }
        }

        return new ResponseEntity<>(err, resp.getHeaders(), resp.getStatusCode());
    }

    private void setDefaultMessage(FieldError e, String message) throws IllegalAccessException {
        Field defaultMessage = ReflectionUtils.findField(FieldError.class, "defaultMessage");
        ReflectionUtils.makeAccessible(defaultMessage);
        defaultMessage.set(e, message);
    }


}

