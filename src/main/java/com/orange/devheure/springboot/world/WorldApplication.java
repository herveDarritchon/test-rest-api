/**
 * 
 */
package com.orange.devheure.springboot.world;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.orange.devheure.springboot.world.dao.WorldRepository;
import com.orange.devheure.springboot.world.model.World;
import com.orange.devheure.springboot.world.model.WorldType;

/**
 * 
 * @author Hervé Darritchon
 *
 */
@SpringBootApplication
public class WorldApplication implements CommandLineRunner {

	@Autowired
	WorldRepository repository;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(WorldApplication.class, args);
	}

	public void run(String... strings) throws Exception {
		System.out.println("Fill the World table with dummy data");

		String[] fullNames = new String[] { "Euskal Herria-PVP", "Catalona-RP",
				"Britania-PVE", "Connacht-RP" };
		for (String fullname : fullNames) {
			String[] name = fullname.split("-");
			System.out.printf("Inserting world record for %s %s\n", name[0],
					name[1]);
			repository.save(new World(name[0],WorldType.valueOf(name[1])));
		}

	}

}
