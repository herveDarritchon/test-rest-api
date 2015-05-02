/**
 * 
 */
package com.orange.devheure.springboot.world.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.orange.devheure.springboot.world.model.World;

/**
 * @author Hervé Darritchon
 *
 */
public interface WorldRepository extends CrudRepository<World, Long> {
	
	List<World> findByName (String lastname);
	
	World findById (Long lastname);
	

}
