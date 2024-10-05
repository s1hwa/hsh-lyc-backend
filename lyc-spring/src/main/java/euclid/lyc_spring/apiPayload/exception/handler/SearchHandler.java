package euclid.lyc_spring.apiPayload.exception.handler;

import euclid.lyc_spring.apiPayload.code.BaseErrorCode;
import euclid.lyc_spring.apiPayload.exception.GeneralException;

public class SearchHandler extends GeneralException {
    public SearchHandler(BaseErrorCode code) {
        super(code);
    }
}
