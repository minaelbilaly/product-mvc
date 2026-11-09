package ma.tp.productmvc;

import ma.tp.productmvc.entities.Product;
import ma.tp.productmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                productRepository.save(new Product(null, "Ordinateur", 8000, 10));
                productRepository.save(new Product(null, "Imprimante", 1500, 25));
                productRepository.save(new Product(null, "Smartphone", 5000, 30));
                productRepository.save(new Product(null, "Souris", 100, 80));
            }

            System.out.println("===== TOUS LES PRODUITS =====");
            productRepository.findAll().forEach(System.out::println);

            System.out.println("===== PRODUIT (id = 1) =====");
            productRepository.findById(1L).ifPresent(System.out::println);
        };
    }
}
