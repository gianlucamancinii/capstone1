package EpicodeFS0124.web.product;

import EpicodeFS0124.core.product.Product;

public class EditProductDTO {
    public String title;
    public String shop;
    public double price;
    public boolean hasDiscount;
    public double discountedPrice;
    public double discountPercent;
    public boolean isFreeShipping;
    public String image;
    public String thumbnail;

    public Product updateProduct(Product product){
        product.setTitle(title);
        product.setShop(shop);
        product.setPrice(price);
        product.setHasDiscount(hasDiscount);
        product.setDiscountedPrice(discountedPrice);
        product.setDiscountPercent(discountPercent);
        product.setFreeShipping(isFreeShipping);
        product.setImage(image);
        product.setThumbnail(thumbnail);
        return product;
    }
}
