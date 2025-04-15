package Services;

import Entities.CategoryEntity;
import Entities.GameEntity;
import Repositories.implementation.CategoryRepository;
import Repositories.implementation.GameRepository;
import lombok.Getter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryService {
    private CategoryRepository categoryRepository;
    private GameRepository gameRepository;
    @Getter
    private static final CategoryService instance = new CategoryService();

    private CategoryService() {
        categoryRepository = CategoryRepository.getInstance();
        gameRepository = GameRepository.getInstance();
    }

    public void chargeCategory(CategoryEntity categoryEntity) {
        try {
            categoryRepository.save(categoryEntity);
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
    }

    public void deleteCategory(CategoryEntity categoryEntity) {
        try {
            categoryRepository.save(categoryEntity);
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
    }

    public String listAll() {
        String message = "List of Categories: ";
        try {
            message += categoryRepository.findAll().toString();
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
        return message;
    }

    public CategoryEntity findById(int id) {
        Optional<CategoryEntity> categoryEntityOptional;
        try {
            categoryEntityOptional = categoryRepository.findById(id);
            if (categoryEntityOptional.isEmpty()) {
                throw new NoSuchElementException("Game not found");
            }
            return categoryEntityOptional.get();
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return CategoryEntity.builder().build();
    }

    public void updateCategory(CategoryEntity categoryEntity) {
        try {
            Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryEntity.getId());
            if (optionalCategoryEntity.isEmpty()) {
                throw new NoSuchElementException("Category not found");
            }
            categoryRepository.update(categoryEntity);
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    public CategoryEntity categoryWithMostGames() {
        try {
            ArrayList<GameEntity> games = gameRepository.findAll();
            if (games.isEmpty()) {
                throw new NoSuchElementException("No games found");
            }
            int idCategory = games.stream()
                    .collect(Collectors.groupingBy(GameEntity::getCategory_id, Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .orElseThrow(() -> new NoSuchElementException("No category found"))
                    .getKey();

            Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(idCategory);
            if (optionalCategoryEntity.isPresent()) {
                return optionalCategoryEntity.get();
            }


        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return CategoryEntity.builder().build();
    }
}
