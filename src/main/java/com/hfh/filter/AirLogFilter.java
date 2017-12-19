package com.hfh.filter;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

@PreMatching
public class AirLogFilter implements ContainerRequestFilter, ClientRequestFilter, ContainerResponseFilter, ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {

    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {

    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MultivaluedMap<String, String> map = requestContext.getHeaders();
        System.out.println(requestContext.getMethod() + "   " + requestContext.getUriInfo().getAbsolutePath().toString());
        for (String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }
        System.out.println("");
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

    }
}
