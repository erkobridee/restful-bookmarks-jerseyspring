package com.erkobridee.restful.bookmarks.jerseyspring.junit;

import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.erkobridee.restful.bookmarks.jerseyspring.persistence.entity.Bookmark;
import com.erkobridee.restful.bookmarks.jerseyspring.persistence.entity.ResultData;
import com.erkobridee.restful.bookmarks.jerseyspring.rest.BookmarkRest;
import com.erkobridee.restful.bookmarks.jerseyspring.rest.resource.ResultMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/applicationContext.xml")
public class BookmarkRestTest {

	@Autowired
	private BookmarkRest rest;

	private static Bookmark vo;

	@Test
	// RESTful POST
	public void testInsert() {
		vo = new Bookmark();
		vo.setName("BookmarkServiceTest Name");
		vo.setDescription("BookmarkServiceTest Description");
		vo.setUrl("http://service.bookmarkdomain.test/"
				+ System.currentTimeMillis() + "/");
		
		vo = (Bookmark) rest.create(vo).getEntity();

		Assert.assertNotNull(vo.getId());
	}

	@Test
	// RESTful GET .../{id}
	public void testGetById() {
		Assert.assertNotNull(rest.get(vo.getId().toString()));
	}

	@Test
	// RESTful GET .../search/{name}
	public void testGetByName() {
		@SuppressWarnings("unchecked")
		ResultData<List<Bookmark>> r = (ResultData<List<Bookmark>>) rest.search(vo.getName(), 1, 10).getEntity();

		Assert.assertTrue(r.getData().size() > 0);
	}

	@Test
	// RESTful PUT .../{id}
	public void testUpdate() {
		String nameUpdated = vo.getName() + "++";

		vo.setName(nameUpdated);
		vo.setDescription(vo.getDescription() + "++");
		vo.setUrl(vo.getUrl() + System.currentTimeMillis());

		vo = (Bookmark) rest.update(vo).getEntity();

		Assert.assertEquals(vo.getName(), nameUpdated);
	}

	@Test
	// RESTful GET
	public void testGetAll() {
		@SuppressWarnings("unchecked")
		ResultData<List<Bookmark>> r = (ResultData<List<Bookmark>>) rest.getList(1, 10).getEntity();

		Assert.assertTrue(r.getData().size() > 0);
	}

	@Test
	// RESTful DELETE
	public void testDelete() {
		String id = vo.getId().toString();

		rest.remove(id);

		Response response = rest.get(id);

		boolean flag = response.getEntity() instanceof ResultMessage;
		
		Assert.assertTrue(flag);
		
		if(flag) {
			ResultMessage rm = (ResultMessage) response.getEntity();
			Assert.assertEquals(404, rm.getCode());
		}
	}

}
