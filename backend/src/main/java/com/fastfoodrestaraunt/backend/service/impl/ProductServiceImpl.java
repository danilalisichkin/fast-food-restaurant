package com.fastfoodrestaraunt.backend.service.impl;

import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductUpdatingDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.ProductSortField;
import com.fastfoodrestaraunt.backend.core.mappers.PageMapper;
import com.fastfoodrestaraunt.backend.core.mappers.ProductMapper;
import com.fastfoodrestaraunt.backend.entity.Category;
import com.fastfoodrestaraunt.backend.entity.Product;
import com.fastfoodrestaraunt.backend.exception.ResourceNotFoundException;
import com.fastfoodrestaraunt.backend.repository.ProductRepository;
import com.fastfoodrestaraunt.backend.service.CategoryService;
import com.fastfoodrestaraunt.backend.service.ProductService;
import com.fastfoodrestaraunt.backend.util.PageRequestBuilder;
import com.fastfoodrestaraunt.backend.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductValidator productValidator;

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final ProductMapper productMapper;

    private final PageMapper pageMapper;

    @Override
    public PageDto<ProductDto> getPageOfProducts(
            Integer offset, Integer limit, ProductSortField sortBy, Sort.Direction sortOrder) {

        PageRequest pageRequest =
                PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);

        return pageMapper.pageToPageDto(
                productMapper.entityPageToDtoPage(
                        productRepository.findAll(pageRequest)));
    }

    @Override
    public ProductDto getProduct(Long id) {
        return productMapper.entityToDto(
                getProductEntity(id));
    }

    @Override
    public ProductDto createProduct(ProductAddingDto addingDto) {
        productValidator.validateProductNameUnique(addingDto.name());

        Product newProduct = productMapper.dtoToEntity(addingDto);
        Category category = categoryService.getCategoryEntity(addingDto.categoryId());
        newProduct.setCategory(category);

        return productMapper.entityToDto(
                productRepository.save(newProduct));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductUpdatingDto updatingDto) {
        Product productToUpdate = getProductEntity(id);

        if (!productToUpdate.getName().equals(updatingDto.name())) {
            productValidator.validateProductNameUnique(updatingDto.name());
        }
        if (!productToUpdate.getCategory().getId().equals(updatingDto.categoryId())) {
            Category category = categoryService.getCategoryEntity(updatingDto.categoryId());
            productToUpdate.setCategory(category);
        }

        productMapper.updateEntityFromDto(updatingDto, productToUpdate);

        return productMapper.entityToDto(
                productRepository.save(productToUpdate));
    }

    @Override
    public void deleteProduct(Long id) {
        Product productToDelete = getProductEntity(id);
        productRepository.delete(productToDelete);
    }

    @Override
    public Product getProductEntity(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("product with id=%s not found", id)));
    }
}
