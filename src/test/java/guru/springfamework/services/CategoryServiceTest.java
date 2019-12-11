package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    public static final Long ID = 2L;
    public static final String NAME = "Name";

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    void getAllCategories() {

        List<Category> categoryList = Arrays.asList(new Category(NAME), new Category());

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertEquals(2L, result.size());
    }

    @Test
    void getCategoryByName() {

        Category category = new Category(NAME);
        category.setId(ID);

        when(categoryRepository.findOneByName(eq(NAME))).thenReturn(Optional.of(category));

        CategoryDTO result = categoryService.getCategoryByName(NAME);

        assertEquals(ID, result.getId());
        assertEquals(NAME, result.getName());

    }
}