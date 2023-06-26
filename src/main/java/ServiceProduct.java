import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServiceProduct {

    public List<Product> initProduct() {
        return List.of(
                new Product("Book", 2000, false, LocalDate.of(2023, 01, 01)),
                new Product("Book", 20, false, LocalDate.of(2023, 01, 10)),
                new Product("Book", 70, false, LocalDate.of(2023, 01, 11)),
                new Product("Magazine", 330, true, LocalDate.of(2022, 02, 01)),
                new Product("Book", 300, true, LocalDate.of(2023, 03, 01)),
                new Product("Book", 150, true, LocalDate.of(2023, 03, 02)),
                new Product("Book", 370, false, LocalDate.of(2023, 03, 03)),
                new Product("Newspaper", 120, false, LocalDate.of(2022, 04, 01)),
                new Product("Newspaper", 150, true, LocalDate.of(2023, 05, 01))
        );
    }

    //1.2 Реалізувати метод отримання всіх продуктів у вигляді списку, категорія яких еквівалентна “Book” та ціна більш ніж 250.
    public List<Product> productsBookPriceMore250(List<Product> products) {
        Predicate<Product> isTypeBook = (p) -> p.getType().equalsIgnoreCase("Book");
        Predicate<Product> isPriceMoreThan250 = (p) -> p.getPrice() > 250;
        return products.stream()
                .filter(isTypeBook.and(isPriceMoreThan250))
                .collect(Collectors.toList());

    }

    //2.2 Реалізувати метод отримання всіх продуктів як списку, категорія яких еквівалентна “Book” і з
// можливістю застосування знижки. Фінальний список повинен містити продукти з застосованою знижкою 10%.
//Так, якщо Продукт A був з ціною 1.0 USD, то його фінальна ціна залишатиметься 0.9 USD
    public List<Product> productsBookDiscount(List<Product> products) {
        int discont = 10;

        Predicate<Product> isTypeBook = (p) -> p.getType().equalsIgnoreCase("Book");
        Predicate<Product> isDisc = Product::isDiscount;
        return products.stream()
                .filter(isTypeBook.and(isDisc))
                .peek(pr -> pr.setPrice(pr.getPrice() * (100 - discont) / 100))
                .toList();
    }

    //3.2 Реалізувати метод отримання найдешевшого продукту з категорії “Book”
//3.3 У випадку, якщо жоден продукт не знайдено (ситуація, коли немає продукту
// з категорією), викинути виняток з повідомленням “Продукт [категорія: ім'я_категорії] не знайдено”.
    public double productMinPrice(List<Product> productList, String nameType) {
        Predicate<Product> isTypeBook = (p) -> nameType.equalsIgnoreCase(p.getType());
        return productList.stream()
                .filter(isTypeBook)
                .mapToDouble(Product::getPrice)
                .min().orElseThrow(() -> new ProductEmptyException("Продукт \"" + nameType + "\" відсутній в списку товарів."));
    }

    //4.1 Даний клас Product, який складається з властивостей:...
//4.2 Реалізувати метод отримання трьох останніх доданих продуктів
    public List<Product> getThreeLastProduct(List<Product> productList) {
        return productList.stream()
                .sorted(Comparator.comparing(Product::getCreateDate).reversed())
                .limit(3)
                .toList();
    }

    //5.1 Даний клас Product, який складається з властивостей:...
//5.2 Реалізувати метод калькуляції загальної вартості продуктів, які відповідають наступним критеріям:
//- продукт додано протягом поточного року
//- продукт має тип “Book”
//- ціна продукту не перевищує 75
    public double getCostProduct(List<Product> productList) {
        LocalDate dateNow = LocalDate.now();
        Predicate<Product> isBook = (p) -> p.getType().equalsIgnoreCase("Book");
        Predicate<Product> isPrice = (p) -> p.getPrice() <= 75;
        Predicate<Product> isDateCreate = (p) -> p.getCreateDate().getYear() == dateNow.getYear();
        return productList.stream()
                .filter(isBook.and(isPrice).and(isDateCreate))
                .mapToDouble(Product::getPrice)
                .sum();
    }

    //** 6.2 Реалізувати метод групування об'єктів за типом продукту. Таким чином результатом виконання методу
//буде тип даних “Словник”, що зберігає пару ключ-значення: {тип: список_продуктів}
//Наприклад:
//	“Book”,
//	[
//		{type: “Book”, price: 100, discount: false, createDate: 01-01 -2022},
//		{…},
//		{…}
//	]
//	“Toy”,
//	[
//		{type: “Toy”, price: 100, discount: true, createDate: 10–102022},
//		{…}
//	]
    public Map<String, List<Product>> mapProduct(List<Product> productList) {
        Map<String, List<Product>> listMapProduct;
        listMapProduct = productList.stream()
                .collect(Collectors.groupingBy(Product::getType));
        for (Map.Entry<String, List<Product>> prodMap : listMapProduct.entrySet()) {
            System.out.println(prodMap.getKey());
            for (Product product : prodMap.getValue()) {
                System.out.println(product);
            }
        }
        return listMapProduct;
    }
}