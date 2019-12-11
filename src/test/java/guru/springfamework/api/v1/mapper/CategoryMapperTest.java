package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    public static final Long ID = 1L;
    public static final String NAME = "Testing";
    public static final CategoryMapper CATEGORY_MAPPER = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTO() {
        Category category = new Category(NAME);
        category.setId(ID);

        CategoryDTO dto = CATEGORY_MAPPER.categoryToCategoryDTO(category);

        assertEquals(category.getId(), dto.getId());
        assertEquals(category.getName(), dto.getName());
    }
}