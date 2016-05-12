/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpErrors;

/**
 *
 * @author kaspe
 */
public class ErrorMessage
{

    private int httpError;
    private String message;
    private int errorCode;

    public ErrorMessage(Throwable ex, int code)
    {
        this.httpError = code;
        this.message = ex.getMessage();
    }

    public ErrorMessage(int httpError, String message, int errorCode)
    {
        this.httpError = httpError;
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getHttpError()
    {
        return httpError;
    }

    public void setHttpError(int httpError)
    {
        this.httpError = httpError;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

}
