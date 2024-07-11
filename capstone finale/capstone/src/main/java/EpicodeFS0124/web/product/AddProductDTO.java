package EpicodeFS0124.web.product;

import EpicodeFS0124.core.product.Product;
import EpicodeFS0124.core.security.CustomUser;

public class AddProductDTO {
    public String type;
    public String title;
    public String shop;
    public double price;
    public boolean hasDiscount;
    public double discountedPrice;
    public double discountPercent;
    public boolean isFreeShipping;
    public String image;
    public String thumbnail;
    public long seller_id;



    public Product toProduct(CustomUser seller){
        return Product.of(type, title, shop, seller, price, 0, 0, false,
                hasDiscount, discountedPrice, discountPercent, isFreeShipping, image, thumbnail);
    }
}
