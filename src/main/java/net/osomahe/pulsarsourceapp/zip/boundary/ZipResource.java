package net.osomahe.pulsarsourceapp.zip.boundary;

import net.osomahe.pulsarsourceapp.message.boundary.MessageService;
import net.osomahe.pulsarsourceapp.zip.control.ZipService;
import net.osomahe.pulsarsourceapp.zip.entity.ZipInfo;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(("/zip-messages"))
public class ZipResource {

    @Inject
    ZipService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response writeZipData(@Valid ZipInfo info) {
        service.writeZipData(info);
        return Response.ok().build();
    }
}
