package net.osomahe.pulsarsourceapp.string.boundary;

import net.osomahe.pulsarsourceapp.message.boundary.MessageService;
import net.osomahe.pulsarsourceapp.message.entity.MessageInfo;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(("/string-messages"))
public class StringResource {

    @Inject
    MessageService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response writeData(@Valid MessageInfo info) {
        service.writeStringMessage(info.topic, info.data, info.producer);
        return Response.ok().build();
    }
}
