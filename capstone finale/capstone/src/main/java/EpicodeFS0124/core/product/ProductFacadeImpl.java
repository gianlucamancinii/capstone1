package EpicodeFS0124.core.product;


import EpicodeFS0124.core.security.CustomUser;
import EpicodeFS0124.web.product.AddProductDTO;
import EpicodeFS0124.web.product.EditProductDTO;
import EpicodeFS0124.web.product.ProductResponseDTO;
import EpicodeFS0124.core.security.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ProductFacadeImpl implements ProductFacade{

    private final ProductRepository productRepository;
    private final UserRepository customUserRepository;
    public ProductFacadeImpl(ProductRepository productRepository, UserRepository customUserRepository){
        this.productRepository = productRepository;
        this.customUserRepository = customUserRepository;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductResponseDTO::from).toList();
    }

    @Override
    public List<ProductResponseDTO> getProductsByType(String productType) {
        return productRepository.getProductByType(productType).stream().map(ProductResponseDTO::from).toList();
    }

    @Override
    public ProductResponseDTO getProduct(Integer id) {
        return ProductResponseDTO.from(productRepository.findById(id).get());
    }

    @Override
    public void addProduct(AddProductDTO addProductDTO) {
        final CustomUser customUser = customUserRepository.getReferenceById(addProductDTO.seller_id);
        productRepository.save(addProductDTO.toProduct(customUser));
    }

    @Override
    public void editProduct(Integer id, EditProductDTO editProductDTO) {
        final Product product = productRepository.findById(id).get();
        editProductDTO.updateProduct(product);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        final Product product = productRepository.findById(id).get();
        productRepository.delete(product);
    }
}
