package pl.holowinska.gpcproject.adapters.dao;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pl.holowinska.gpcproject.domain.model.Product;
import pl.holowinska.gpcproject.ports.outbound.ProductRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FileRepositoryImpl implements ProductRepository {

    private final String xmlFilePath = "file.xml";
    private Document document;

    public FileRepositoryImpl() {
        this.document = loadDocument();
    }

    @Override
    public Long countAllProducts() {
        document = loadDocument();
        NodeList nodeList = document.getElementsByTagName("Product");
        return (long) nodeList.getLength();
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        document = loadDocument();
        NodeList nodeList = document.getElementsByTagName("Product");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            products.add(convertToProduct(element));
        }
        return products;
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        List<Product> products = getAllProducts();
        return products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    private Product convertToProduct(Element element) {
        Product product = new Product();
        product.setId(Long.valueOf(element.getAttribute("id")));
        product.setName(element.getElementsByTagName("Name").item(0).getTextContent());
        product.setCategory(element.getElementsByTagName("Category").item(0).getTextContent());
        product.setActive(Boolean.parseBoolean(element.getElementsByTagName("Active").item(0).getTextContent()));
        product.setCompanyName(element.getElementsByTagName("CompanyName").item(0).getTextContent());
        product.setPartNumberNR(element.getElementsByTagName("PartNumberNR").item(0).getTextContent());
        return product;
    }

    private Document loadDocument() {
        try {
            File file = new ClassPathResource(xmlFilePath).getFile();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(file);
        } catch (Exception e) {
            throw new RuntimeException("Error loading XML file", e);
        }
    }
}
