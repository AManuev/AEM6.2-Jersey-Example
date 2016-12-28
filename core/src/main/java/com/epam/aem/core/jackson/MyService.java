package com.epam.aem.core.jackson;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Service(MyService.class)
@Path("/osgi-jax-rs")
@Produces(MediaType.APPLICATION_JSON)
public class MyService {

    @GET
    public Response getEntity() {
        return Response.ok("{'lala' : 'lala'}", MediaType.APPLICATION_JSON).build();
    }
}
