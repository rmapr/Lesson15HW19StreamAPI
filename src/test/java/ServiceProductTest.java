import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServiceProductTest {
    static ServiceProduct servProd;
    static List<Product> productList;

    @BeforeAll
    public static void serviceProduct() {
        servProd = new ServiceProduct();
        productList = servProd.initProduct();
        System.out.println("---List<Product>---");
        for (Product product : productList) {
            System.out.println(product);
        }
        System.out.println("---List<Product>---");
    }

    @Test
    @DisplayName("Init Product successfully")
    void initProduct() {
        assertNotNull(servProd.initProduct());

    }

    @Test
    @DisplayName("Product \"Book\" with price more 250")
    void productsBookPriceMore250() {
        assertEquals(3,
                servProd.productsBookPriceMore250(productList).size());
    }

    @Test
    @DisplayName("Discounted first value Book product")
    void productsBookDiscount() {
        assertEquals(270,
                servProd.productsBookDiscount(productList).get(0).getPrice());
    }

    @Test
    @DisplayName("Min price product \"Book\"")
    void productMinPrice() {
        assertEquals(20,
                servProd.productMinPrice(productList, "book"));
    }

    @Test
    @DisplayName("Min price product \"exception\" exception")
    void productMinPriceException() {
        String nameType = "exception";
        assertThrowsExactly(ProductEmptyException.class, () -> servProd.productMinPrice(productList, nameType),
                "Продукт \"" + nameType + "\" відсутній в списку товарів.");
    }

    @Test
    @DisplayName("Comparison last three addition product by \"id\"")
    void getThreeLastProduct() {
        List<Integer> ints = Arrays.asList(9, 7, 6);
        List<Integer> integerList = new ArrayList<>();
        for (Product pr : servProd.getThreeLastProduct(productList)) {
            integerList.add(pr.getId());
        }
        assertEquals(ints,
                integerList);
    }

    @Test
    @DisplayName("Cost product \"Book\" addition this year and the price is below 75")
    void getCostProduct() {
        assertEquals(90,
                servProd.getCostProduct(productList));
    }

    @Test
    void mapProduct() {
        Map<String, List<Product>> map = servProd.mapProduct(productList);
        assertEquals(3,
                map.keySet().size());
    }
}