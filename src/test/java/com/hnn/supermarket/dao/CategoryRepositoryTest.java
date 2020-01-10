package com.hnn.supermarket.dao;

import com.hnn.supermarket.SupermarketApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupermarketApplication.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:create_tables.sql")
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insert_tables.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:cleanup.sql")
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testGetCategoryByCode() {
        Category category = categoryRepository.save(new Category("CAT_TEST", "test"));
        Category foundCategory = categoryRepository.getCategoryByCode(category.getCode());

        assertNotNull(foundCategory);
        assertEquals(category.getCode(), foundCategory.getCode());
    }

    @Test
    public void testGetCategoryByCode_CodeNotExist() {
        Category foundCategory = categoryRepository.getCategoryByCode("codeNotExist");

        assertNull("Should return null if code not exist", foundCategory);
    }

    @Test
    public void testGetCategoryByCode_CodeNull() {
        Category foundCategory = categoryRepository.getCategoryByCode(null);

        assertNull("Should return null if code null", foundCategory);
    }
}
