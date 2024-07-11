package EpicodeFS0124.core.product;

import EpicodeFS0124.web.product.AddProductDTO;
import EpicodeFS0124.web.product.EditProductDTO;
import EpicodeFS0124.web.product.ProductResponseDTO;

import java.util.List;

public interface ProductFacade {

    List<ProductResponseDTO> getAllProducts();
    List<ProductResponseDTO> getProductsByType(String productType);
    ProductResponseDTO getProduct(Integer id);
    void addProduct(AddProductDTO addProductDTO);
    void editProduct(Integer id, EditProductDTO editProductDTO);
    void deleteProduct(Integer id);

}
