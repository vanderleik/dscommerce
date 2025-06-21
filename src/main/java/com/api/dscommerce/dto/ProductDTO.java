package com.api.dscommerce.dto;

import com.api.dscommerce.entities.Category;
import com.api.dscommerce.entities.Product;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private Long id;

    @Size(min = 3, max = 80, message = "O nome do produto deve ter entre 3 e 80 caracteres")
    @NotBlank(message = "Campo obrigatório")
    private String name;

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 10, message = "A descrição do produto deve ter no mínimo 10 caracteres")
    private String description;

    @NotNull(message = "Campo requerido")
    @Positive(message = "O preço do produto deve ser um valor positivo")
    private Double price;

    private String imgUrl;

    @NotEmpty(message = "Deve ter pelo menos uma categoria")
    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        for (Category cat : entity.getCategories()) {
            categories.add(new CategoryDTO(cat));
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

}
