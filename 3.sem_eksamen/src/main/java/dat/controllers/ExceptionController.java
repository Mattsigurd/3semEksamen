package dat.controllers;


import dat.exceptions.ApiException;
import dat.entities.Message;
import io.javalin.http.Context;

public class ExceptionController {


    public void apiExceptionHandler(ApiException e, Context ctx) {
        ctx.status(e.getStatusCode());
        ctx.json(new Message(e.getStatusCode(), e.getMessage()));

    }

    public void exceptionHandler(Exception e, Context ctx) {
        ctx.status(500);
        ctx.json(new Message(500, e.getMessage()));

    }


}
