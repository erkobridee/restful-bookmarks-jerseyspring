package com.erkobridee.restful.bookmarks.junit;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.erkobridee.restful.bookmarks.entity.Bookmark;
import com.erkobridee.restful.bookmarks.service.BookmarkService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/resources/applicationContext.xml")
public class BookmarkServiceTest {

	@Autowired
	private BookmarkService service;
	
	private static Bookmark vo;
	
	@Test // RESTful POST
	public void testInsert() {
		vo = new Bookmark();
		vo.setName("BookmarkServiceTest Name");
		vo.setDescription("BookmarkServiceTest Description");
		vo.setUrl("http://service.bookmarkdomain.test/" + System.currentTimeMillis() + "/");
		vo = service.insert(vo);
		
		Assert.assertNotNull(vo.getId());
	}
	
	@Test // RESTful GET 	.../{id}
	public void testGetById() {		
		Assert.assertNotNull(service.getById( vo.getId().toString() ));
	}
	
	@Test // RESTful GET 	.../search/{name}
	public void testGetByName() {
		List<Bookmark> list = service.getByName(vo.getName());
		
		Assert.assertTrue( list.size() > 0 );
	}
	
	@Test // RESTful PUT	.../{id}
	public void testUpdate() {
		String nameUpdated = vo.getName() + "++";
		
		vo.setName( nameUpdated );
		vo.setDescription( vo.getDescription() + "++" );
		vo.setUrl( vo.getUrl() + System.currentTimeMillis() );
		
		vo = service.update(vo);
		
		Assert.assertEquals( vo.getName(), nameUpdated);
	}
	
	@Test // RESTful GET
	public void testGetAll() {
		List<Bookmark> list = service.getAll();
		
		Assert.assertTrue( list.size() > 0 );
	}
	
	@Test // RESTful DELETE
	public void testDelete() {
		String id = vo.getId().toString(); 
		
		service.remove( id );
		
		vo = service.getById( id );
		
		Assert.assertNull( vo );
	}
	
}
