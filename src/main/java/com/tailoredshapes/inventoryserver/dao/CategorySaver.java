package com.tailoredshapes.inventoryserver.dao;

import com.tailoredshapes.inventoryserver.model.Category;
import com.tailoredshapes.inventoryserver.repositories.CategoryRepository;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import java.util.Arrays;

public class CategorySaver extends Saver<Category> {

    private final DAO<Category> categoryDAO;
    private final CategoryRepository categoryRepository;

    @Inject
    public CategorySaver(DAO<Category> categoryDAO, CategoryRepository categoryRepository) {
        this.categoryDAO = categoryDAO;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveChildren(Category object) {
        if (null == object.getName()) {
            String[] split = object.getFullname().split("\\.");

            object.setName(split[split.length - 1]);
            if (split.length > 1) {
                String[] strings = Arrays.copyOfRange(split, 0, split.length - 1);
                String parentCategory = StringUtils.join(strings, ".");
                object.setParent(categoryRepository.findByFullname(parentCategory));
            }

        }

        object.setParent(upsert(object.getParent(), categoryDAO));
        return object;
    }
}
