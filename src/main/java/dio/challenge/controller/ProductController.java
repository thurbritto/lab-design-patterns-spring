package dio.challenge.controller;

import dio.challenge.model.Product;
import dio.challenge.service.ProductService;
import dio.challenge.strategy.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private DiscountStrategy blackFridayDiscountStrategy; // Injeção da estratégia para Black Friday
    @Autowired
    private DiscountStrategy marketDiscountStrategy; // Injeção da estratégia para a Feira

    @GetMapping
    public List<Product> getAllProducts() {
        System.out.println("GET - Retornando todos elementos");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable Long id) {
        System.out.println("GET by ID - Retornando elemento por id");
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        System.out.println("POST - Inserindo o produto");
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        System.out.println("PUT - Atualizando o produto");
        Optional<Product> existingProduct = productService.getProductById(id);

        // Verifica se o produto existe, se não existir é retornado uma resposta HTTP com o status "404 Not Found"
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());

            // Variáveis para identificar a data
            LocalDate currentDate = LocalDate.now();
            LocalDate blackFridayDate = LocalDate.of(currentDate.getYear(), 11, 24);


            // Verifica se é black friday, se for, o desconto de 50% é aplicado
            // Se não for, é aplicado o desconto de 10%
            if (currentDate.equals(blackFridayDate)) {
                double originalPrice = product.getPrice();
                double discountedPrice = blackFridayDiscountStrategy.applyDiscount(originalPrice);
                product.setPrice(originalPrice - discountedPrice);
            } else {
                double originalPrice = product.getPrice();
                double discountedPrice = marketDiscountStrategy.applyDiscount(originalPrice);
                product.setPrice(originalPrice - discountedPrice);
            }

            productService.updateProduct(product);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        System.out.println("DELETE - Deletando o produto");
        productService.deleteProduct(id);
    }
}