package Services;

import Entities.CategoryEntity;
import Entities.GameEntity;
import Repositories.implementation.CategoryRepository;
import lombok.Getter;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CategoryService {
    private CategoryRepository categoryRepository;
    @Getter
    private static final CategoryService instance = new CategoryService();
    private CategoryService(){
        categoryRepository = CategoryRepository.getInstance();
    }

    public void chargeCategory (CategoryEntity categoryEntity){
        try {
            categoryRepository.save(categoryEntity);
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
    }
    public void deleteCategory(CategoryEntity categoryEntity){
        try {
            categoryRepository.save(categoryEntity);
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
    }
    public String listAll(){
        String message = "List of Categories: ";
        try {
            message += categoryRepository.findAll().toString();
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
        return message;
    }
    public CategoryEntity findById (int id){
        Optional<CategoryEntity> categoryEntityOptional;
        try {
            categoryEntityOptional = categoryRepository.findById(id);
            if (categoryEntityOptional.isEmpty()){
                throw new NoSuchElementException("Game not found");
            }
            return categoryEntityOptional.get();
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
        return CategoryEntity.builder().build();
    }
    public void updateCategory (CategoryEntity categoryEntity){
        try {
            Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryEntity.getId());
            if (optionalCategoryEntity.isEmpty()){
                throw new NoSuchElementException("Category not found");
            }
            categoryRepository.update(categoryEntity);
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }
}
