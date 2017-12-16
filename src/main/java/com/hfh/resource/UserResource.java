package com.hfh.resource;

import com.hfh.model.User;
import io.ebean.Ebean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("user")
public class UserResource {

    @GET
    @Path("add/{name}")
    public User add(@PathParam("name") String name) {
        User user = new User();
        user.name = name;
        user.insert();
        return user;
    }

    @GET
    @Path("list")
    public List<User> list() {
        return Ebean.find(User.class).findList();
    }
}
