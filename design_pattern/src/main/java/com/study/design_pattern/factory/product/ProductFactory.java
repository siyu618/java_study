package com.study.design_pattern.factory.product;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tianyuzhi
 * @date 18/3/6
 */
public class ProductFactory {
    private static final Map<String, Product> productMap = new HashMap<>();
    public static synchronized Product createProduct(String type) {
        Product product = null;
        if (productMap.containsKey(type)) {
            return productMap.get(type);
        }
        else {
            if ("Product1".equals(type)) {
                product = new ConcreateProduct1();
            }
            else {
                product = new ConcreateProduct2();
            }
            productMap.put(type, product);
        }
        return product;
    }
}
