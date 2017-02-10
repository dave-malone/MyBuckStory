package com.mybuckstory.core.service;

import java.util.List;

import com.mybuckstory.model.Album;


public class AlbumServiceTest extends AbstractSecureContextTest{

	protected AlbumService albumService;

	
	public void testFindByAuthor() {
		List<Album> albums = albumService.findByAuthor(1L);
		assertEquals(1, albums.size());
	}

	
	public void testGetGalleryDisplayModel() {
		fail("Not yet implemented");
	}

}
