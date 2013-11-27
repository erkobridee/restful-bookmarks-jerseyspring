package com.erkobridee.restful.bookmarks.jerseyspring.junit;

import java.util.List;

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

	//--------------------------------------------------------------------------
	
	@Autowired
	private BookmarkRest rest;

	private static Bookmark vo;

	//--------------------------------------------------------------------------
	// RESTful POST
	
	@Test	
	public void test_01_Create() {
		vo = new Bookmark();
		vo.setName( "BookmarkServiceTest Name" );
		vo.setDescription( "BookmarkServiceTest Description" );
		vo.setUrl( "http://service.bookmarkdomain.test/"
				+ System.currentTimeMillis() + "/" );
		
		vo = (Bookmark) rest.create( vo ).getEntity();

		Assert.assertNotNull( vo.getId() );
	}

	//--------------------------------------------------------------------------
	// RESTful GET
	
	@Test	
	public void test_02_GetList() {
		@SuppressWarnings("unchecked")
		ResultData<List<Bookmark>> r = (ResultData<List<Bookmark>>) rest.getList( 1, 10 ).getEntity();

		Assert.assertTrue( r.getData().size() > 0 );
	}
	
	//--------------------------------------------------------------------------
	// RESTful GET .../{id}
	
	private Bookmark getById( Long id ) {
		Object entity = rest.get( id ).getEntity();
		
		if( entity instanceof Bookmark ) return (Bookmark) entity;
		
		return null;
	}
	
	@Test
	public void test_03_GetByInvalidId() {
		Assert.assertNull( this.getById( Long.valueOf( -1 ) ) );
	}
	
	@Test	
	public void test_03_GetById() {
		Assert.assertNotNull( this.getById( vo.getId() ) );
	}
	
	//--------------------------------------------------------------------------
	// RESTful GET .../search/{name}
	
	@SuppressWarnings("unchecked")
	private ResultData<List<Bookmark>> getByName( String name ) {				
		return (ResultData<List<Bookmark>>) rest.search( name, 1, 10 ).getEntity();
	}

	@Test	
	public void test_04_GetByInvalidName() {
		ResultData<List<Bookmark>> r = this.getByName( "***" + vo.getName() + "***" );

		Assert.assertFalse( r.getData().size() > 0 );
	}
	
	@Test	
	public void test_04_GetByName() {
		ResultData<List<Bookmark>> r = this.getByName( vo.getName() );

		Assert.assertTrue( r.getData().size() > 0 );
	}

	//--------------------------------------------------------------------------
	// RESTful PUT .../{id}
	
	@Test	
	public void test_05_Update() {
		String nameUpdated = vo.getName() + "++";

		vo.setName( nameUpdated );
		vo.setDescription( vo.getDescription() + "++" );
		vo.setUrl( vo.getUrl() + System.currentTimeMillis() );

		vo = (Bookmark) rest.update( vo ).getEntity();

		Assert.assertEquals( nameUpdated, vo.getName() );
	}

	//--------------------------------------------------------------------------
	// RESTful DELETE .../{id}
	
	private ResultMessage deleteById( Long id ) {
		return (ResultMessage) rest.remove( id ).getEntity();
	}
	
	@Test
	public void test_06_DeleteByInvalidId() {
		ResultMessage message = this.deleteById( Long.valueOf( -1 ) );
		
		Assert.assertEquals( 404, message.getCode() );
	}
	
	@Test
	public void test_06_DeleteById() {
		ResultMessage message = this.deleteById( vo.getId() );
		
		Assert.assertEquals( 202, message.getCode() );
	}
	
	//--------------------------------------------------------------------------

}
