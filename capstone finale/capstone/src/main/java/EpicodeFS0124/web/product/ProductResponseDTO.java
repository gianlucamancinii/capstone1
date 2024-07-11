package EpicodeFS0124.web.product;

import EpicodeFS0124.core.product.Product;

public class ProductResponseDTO {
    public Integer id;
    public String title;
    public String shop;
    public double price;
    public int rating;
    public int ratingCount;
    public boolean isBestSeller;
    public boolean hasDiscount;
    public double discountedPrice;
    public double discountPercent;
    public boolean isFreeShipping;
    public String image;
    public String thumbnail;

    public static ProductResponseDTO from(Product product){
        final ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.id = product.getId();
        productResponseDTO.title = product.getTitle();
        productResponseDTO.shop = product.getShop();
        productResponseDTO.price = product.getPrice();
        productResponseDTO.rating = product.getRating();
        productResponseDTO.ratingCount = product.getRatingCount();
        productResponseDTO.isBestSeller = product.isBestSeller();
        productResponseDTO.hasDiscount = product.isHasDiscount();
        productResponseDTO.discountedPrice = product.getDiscountedPrice();
        productResponseDTO.discountPercent = product.getDiscountPercent();
        productResponseDTO.isFreeShipping = product.isFreeShipping();
        productResponseDTO.image = product.getImage();
        productResponseDTO.thumbnail = product.getThumbnail();
        return productResponseDTO;
    }
}
