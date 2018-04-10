package com.jahnelgroup.auctionapp.exception.message;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Locale;

@AllArgsConstructor
@Component
public class SpringMvcErrorCodeInterpolator implements ApiErrorInterpolator {

    private MessageSource messageSource;

    @Override
    public String interpolate(FieldError e)  {
        String message = null;
        for(String code : e.getCodes()){
            message = getMessage(code);
            if( message != null ){
                break;
            }
        }

        return message;
    }

    private String getMessage(String code){
        try{
            return messageSource.getMessage(code, null, Locale.getDefault());
        }catch(NoSuchMessageException e){
            return null;
        }
    }
}
