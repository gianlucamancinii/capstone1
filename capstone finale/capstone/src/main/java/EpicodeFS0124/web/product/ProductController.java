package EpicodeFS0124.web.product;

import EpicodeFS0124.core.product.ProductFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductFacade productFacade;
    public ProductController(ProductFacade productFacade){
        this.productFacade = productFacade;
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts(){
        return productFacade.getAllProducts();
    }

    @GetMapping("/byType/{productType}")
    public List<ProductResponseDTO> getProductsByType(@PathVariable String productType){
        return productFacade.getProductsByType(productType);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProduct(@PathVariable Integer id){
        return productFacade.getProduct(id);
    }

    @PostMapping
    public void addProduct(@RequestBody AddProductDTO addProductDTO){
        productFacade.addProduct(addProductDTO);
    }

    @PostMapping("/{id}")
    public void editProduct(@PathVariable Integer id, @RequestBody EditProductDTO editProductDTO){
        productFacade.editProduct(id, editProductDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id){
        productFacade.deleteProduct(id);
    }

}
