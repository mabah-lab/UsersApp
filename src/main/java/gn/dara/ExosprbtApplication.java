package gn.dara;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gn.dara.dao.ClientRepository;
import gn.dara.entites.Client;

@SpringBootApplication
public class ExosprbtApplication implements CommandLineRunner {
	@Autowired
	private ClientRepository cltrep;

	public static void main(String[] args) {
		SpringApplication.run(ExosprbtApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		cltrep.save(new Client("BAH", "Prenom", "Bruxelles"));
		cltrep.save(new Client("BARRY", "Fatoumata", "Banjul"));
		cltrep.save(new Client("SOW", "Abdoul", "Dakar"));
		cltrep.save(new Client("SY", "Alioune", "Paris"));

		cltrep.findAll().forEach(p -> System.out.println(p.toString()));
		// cltrep.chercher("BAH").forEach(p -> System.out.println(p.toString()));
	}

}
