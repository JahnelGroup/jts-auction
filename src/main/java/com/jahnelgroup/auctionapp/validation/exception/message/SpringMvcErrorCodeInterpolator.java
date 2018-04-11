package com.jahnelgroup.auctionapp.validation.exception.message;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Locale;

@AllArgsConstructor
@Component
@Slf4j
public class SpringMvcErrorCodeInterpolator implements ApiErrorInterpolator {

    private MessageSource messageSource;

    @Override
    public String interpolate(FieldError e)  {
        String message = messageSource.getMessage(getResolvable(e), Locale.getDefault());
        if( log.isDebugEnabled() ){
            log.debug("object={} field={}, defaultMessage={}, arguments={} " +
                "codes={} resolvedMessage={}", e.getObjectName(), e.getField(), e.getDefaultMessage(), prettyOutput(e.getArguments()), e.getCodes(), message);
        }
        return message;
    }

    /**
     * Extract message and does parameter replacement {0}, {1}, etc
     * @param e
     * @return
     */
    private DefaultMessageSourceResolvable getResolvable(FieldError e) {
        return new DefaultMessageSourceResolvable(e.getCodes(), e.getArguments());
    }

    /**
     * Helper function to pretty up the output.
     * @param a
     * @return
     */
    private String[] prettyOutput(Object a[]) {
        String[] codeStr = null;
        if( a != null ){
            codeStr = new String[a.length];
            for(int i=0; i < a.length; i++){
                codeStr[i] = a[i] instanceof DefaultMessageSourceResolvable ? ((DefaultMessageSourceResolvable)a[i]).getDefaultMessage()
                        : (a[i] != null ? a[i].toString() : null);
            }
        }
        return codeStr;
    }

}
