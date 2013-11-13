package com.erkobridee.restful.bookmarks.jerseyspring.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.erkobridee.restful.bookmarks.jerseyspring.persistence.dao.IBookmarkDAO;
import com.erkobridee.restful.bookmarks.jerseyspring.persistence.entity.Bookmark;
import com.erkobridee.restful.bookmarks.jerseyspring.persistence.entity.ResultData;

@Component
@Scope("prototype")
@Path("/bookmarks")
public class BookmarkRest {

	/*
	 * TODO: update
	 * 
	 * ref's
	 * 
	 * examples
	 * 	REST with Java (JAX-RS) using Jersey - Tutorial
	 * 	http://www.vogella.com/articles/REST/article.html
	 * 
	 * 	Extracting Request Parameters
	 * 	http://docs.oracle.com/cd/E19776-01/820-4867/6nga7f5np/index.html
	 * 
	 * 	JAX-RS @QueryParam Example
	 * 	http://www.mkyong.com/webservices/jax-rs/jax-rs-queryparam-example/
	 * 
	 * 
	 * Java EE 6 Docs
	 * http://docs.oracle.com/javaee/6/api/javax/ws/rs/core/Response.html
	 * http://docs.oracle.com/javaee/6/api/javax/ws/rs/core/Response.ResponseBuilder.html
	 */

	// --------------------------------------------------------------------------
	
	private Logger log = LoggerFactory.getLogger(BookmarkRest.class);
	
	// --------------------------------------------------------------------------

	@Autowired
	private IBookmarkDAO dao;

	// --------------------------------------------------------------------------

	@GET
	@Path("search/{find}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ResultData<List<Bookmark>> search(
		@PathParam("find") String find,
		@DefaultValue("1") @QueryParam("page") int page,
		@DefaultValue("10") @QueryParam("size") int size
	) {
		log.debug("search: " + find);
		
		return dao.findByName(find, page, size);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ResultData<List<Bookmark>> getList(
		@DefaultValue("1") @QueryParam("page") int page,
		@DefaultValue("10") @QueryParam("size") int size
	) {
		log.debug("getList");
		log.debug("page: " + page + " | size: " + size);		
		return dao.list(page, size);
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Bookmark get(@PathParam("id") String id) {
		log.debug("getById: " + id);
		return dao.findById(Long.valueOf(id));
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Bookmark create(Bookmark value) {
		log.debug("insert");
		return dao.save(value);
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Bookmark update(Bookmark value) {
		log.debug("update");
		return dao.save(value);
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") String id) {
		boolean flag = dao.remove(Long.valueOf(id));
		log.debug("remove: " + id + " | status: " + flag);
	}

}
