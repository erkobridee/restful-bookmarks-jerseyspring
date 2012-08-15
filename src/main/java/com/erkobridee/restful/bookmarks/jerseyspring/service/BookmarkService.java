package com.erkobridee.restful.bookmarks.jerseyspring.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.erkobridee.restful.bookmarks.jerseyspring.dao.IBookmarkDAO;
import com.erkobridee.restful.bookmarks.jerseyspring.entity.Bookmark;

@Component
@Scope("prototype")
@Path("/bookmarks")
public class BookmarkService {

	// --------------------------------------------------------------------------

	@Autowired
	private IBookmarkDAO dao;

	// --------------------------------------------------------------------------

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Bookmark> getAll() {
		return dao.listAll();
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Bookmark getById(@PathParam("id") String id) {
		return dao.findById(Long.valueOf(id));
	}

	@GET
	@Path("search/{name}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Bookmark> getByName(@PathParam("name") String name) {
		return dao.findByName(name);
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Bookmark insert(Bookmark value) {
		return dao.save(value);
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Bookmark update(Bookmark value) {
		return dao.save(value);
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") String id) {
		dao.remove(Long.valueOf(id));
	}

}
