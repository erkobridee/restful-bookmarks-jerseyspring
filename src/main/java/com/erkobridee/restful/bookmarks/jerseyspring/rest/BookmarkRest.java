package com.erkobridee.restful.bookmarks.jerseyspring.rest;

import java.net.URI;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.erkobridee.restful.bookmarks.jerseyspring.persistence.dao.IBookmarkDAO;
import com.erkobridee.restful.bookmarks.jerseyspring.persistence.entity.Bookmark;
import com.erkobridee.restful.bookmarks.jerseyspring.persistence.entity.ResultData;
import com.erkobridee.restful.bookmarks.jerseyspring.rest.resource.ResultMessage;

@Component
@Scope("prototype")
@Path("/bookmarks")
public class BookmarkRest {

	/*
	 * ref's
	 * 
	 * https://en.wikipedia.org/wiki/List_of_HTTP_header_fields
	 * 
	 * examples
	 * 	REST with Java (JAX-RS) using Jersey - Tutorial
	 * 	http://www.vogella.com/articles/REST/article.html
	 * 
	 * 	Extracting Request Parameters
	 * 	http://docs.oracle.com/cd/E19776-01/820-4867/6nga7f5np/index.html
	 * 
	 * 	Get HTTP Header In JAX-RS
	 * 	http://www.mkyong.com/webservices/jax-rs/get-http-header-in-jax-rs/
	 * 
	 * 	JAX-RS @QueryParam Example
	 * 	http://www.mkyong.com/webservices/jax-rs/jax-rs-queryparam-example/
	 * 
	 * 	Jersey Docs
	 * 	https://jersey.java.net/documentation/latest/uris-and-links.html
	 * 
	 * 	Java EE 6 Docs
	 * 	http://docs.oracle.com/javaee/6/api/javax/ws/rs/core/Response.html
	 * 	http://docs.oracle.com/javaee/6/api/javax/ws/rs/core/Response.ResponseBuilder.html
	 */

	// --------------------------------------------------------------------------
	
	private Logger log = LoggerFactory.getLogger(BookmarkRest.class);
	
	// --------------------------------------------------------------------------

	@Context UriInfo uriInfo;
	
	// --------------------------------------------------------------------------
	
	@Autowired
	private IBookmarkDAO dao;

	// --------------------------------------------------------------------------
	
	private URI getLocation() {
		return getLocation("");
	}
	
	private URI getLocation(Long id) {		
		return getLocation("" + id);
	}
	
	private URI getLocation(String add) {
		URI uri = null;
		
		if(uriInfo != null) {
			UriBuilder ub = uriInfo.getAbsolutePathBuilder();
			uri = ub.path(add).build();
		}
		
		return uri;
	}
	
	// --------------------------------------------------------------------------

	@GET
	@Path("search/{find}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response search(
		@PathParam("find") String find,
		@DefaultValue("1") @QueryParam("page") int page,
		@DefaultValue("10") @QueryParam("size") int size
	) {
		log.debug("search: " + find + " | page: " + page + " | size: " + size);

		ResultData<List<Bookmark>> r = dao.findByName(find, page, size);
		
		return Response
				.status(Status.OK)
				.entity(r)
				.header("Allow", "GET")
				.location(getLocation())
				.build();
		
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getList(
		@DefaultValue("1") @QueryParam("page") int page,
		@DefaultValue("10") @QueryParam("size") int size
	) {
		log.debug("getList | page: " + page + " | size: " + size);
		
		ResultData<List<Bookmark>> rd = dao.list(page, size);
		
		return Response
				.status(Status.OK)
				.entity(rd)
				.header("Allow", "GET, POST")
				.location(getLocation())
				.build();
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response get(@PathParam("id") String id) {
		log.debug("getById: " + id);
		
		Bookmark bookmark = dao.findById(Long.valueOf(id));
		
		if(bookmark != null) {
			
			return Response
					.status(Status.OK)
					.entity(bookmark)
					.header("Allow", "PUT, DELETE")
					.location(getLocation())
					.build();
		
		} else {
			
			ResultMessage resultMessage = 
				new ResultMessage(
	                Status.NOT_FOUND.getStatusCode(), 
	                "id: " + id + " not found."
	            );
			
			return Response
					.status(Status.NOT_FOUND)
					.entity(resultMessage)
					.build();
			
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response create(Bookmark value) {
		
		log.debug("insert");
		Bookmark bookmark = dao.save(value);
		
		return Response
				.status(Status.CREATED)
				.entity(bookmark)
				.header("Allow", "GET, PUT, DELETE")
				.location(getLocation(bookmark.getId()))
				.build();
				
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response update(Bookmark value) {
		
		log.debug("update");
		Bookmark bookmark = dao.save(value);
		
		return Response
				.status(Status.ACCEPTED)
				.entity(bookmark)
				.header("Allow", "GET, PUT, DELETE")
				.location(getLocation())
				.build();
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response remove(@PathParam("id") String id) {
		
		boolean flag = dao.remove(Long.valueOf(id));
		log.debug("remove: " + id + " | status: " + flag);
		
		ResultMessage message;
		
		if(flag) {
			
			message = new ResultMessage(Status.ACCEPTED.getStatusCode(), "id: " + id + " removed.");
			
			return Response
					.status(Status.ACCEPTED)
					.entity(message)
					.build();
			
		} else {
			
			message = new ResultMessage(Status.NOT_FOUND.getStatusCode(), "id: " + id + " not found.");
			
			return Response
					.status(Status.NOT_FOUND)
					.entity(message)
					.build();
			
		}
		
	}

}
