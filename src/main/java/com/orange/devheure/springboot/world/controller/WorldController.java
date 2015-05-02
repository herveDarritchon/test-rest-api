/**
 * 
 */
package com.orange.devheure.springboot.world.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.orange.devheure.springboot.world.dao.WorldRepository;
import com.orange.devheure.springboot.world.model.World;

/**
 * @author Hervé Darritchon
 *
 */
@RestController
@RequestMapping(value="/worlds")
public class WorldController {

	@Autowired
	WorldRepository repository;
	
	@RequestMapping (value="/{worldId}",method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public World retreiveWorld (@PathVariable Long worldId) {
		World world = repository.findById(worldId);
		return world;
	}
	
	@RequestMapping (method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public World createWorld (@RequestBody World newWorld) {
		return repository.save(newWorld);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	@ResponseStatus (value= HttpStatus.NO_CONTENT)
    public World destroyWorld(@PathVariable Long id) {
		repository.delete(id);
		return null;
    }
}
