package com.erkobridee.restful.bookmarks.jerseyspring.junit;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.erkobridee.restful.bookmarks.jerseyspring.persistence.entity.Bookmark;
import com.erkobridee.restful.bookmarks.jerseyspring.rest.BookmarkRest;

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
		vo = rest.insert(vo);

		Assert.assertNotNull(vo.getId());
	}

	@Test
	// RESTful GET .../{id}
	public void testGetById() {
		Assert.assertNotNull(rest.getById(vo.getId().toString()));
	}

	@Test
	// RESTful GET .../search/{name}
	public void testGetByName() {
		List<Bookmark> list = rest.getByName(vo.getName());

		Assert.assertTrue(list.size() > 0);
	}

	@Test
	// RESTful PUT .../{id}
	public void testUpdate() {
		String nameUpdated = vo.getName() + "++";

		vo.setName(nameUpdated);
		vo.setDescription(vo.getDescription() + "++");
		vo.setUrl(vo.getUrl() + System.currentTimeMillis());

		vo = rest.update(vo);

		Assert.assertEquals(vo.getName(), nameUpdated);
	}

	@Test
	// RESTful GET
	public void testGetAll() {
		List<Bookmark> list = rest.getAll();

		Assert.assertTrue(list.size() > 0);
	}

	@Test
	// RESTful DELETE
	public void testDelete() {
		String id = vo.getId().toString();

		rest.remove(id);

		vo = rest.getById(id);

		Assert.assertNull(vo);
	}

}