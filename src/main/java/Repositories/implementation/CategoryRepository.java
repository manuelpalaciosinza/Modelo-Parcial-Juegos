package Repositories.implementation;

import Connections.DatabaseConnection;
import Entities.CategoryEntity;
import Repositories.interfaces.IRepository;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class CategoryRepository implements IRepository<CategoryEntity> {
    @Getter
    private static final CategoryRepository instance = new CategoryRepository();

    private CategoryRepository() {
    }

    ;

    private Optional<CategoryEntity> resultToCategory(ResultSet rs) throws SQLException {
        return Optional.of(CategoryEntity.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build());
    }

    @Override
    public ArrayList<CategoryEntity> findAll() throws SQLException {
        ArrayList<CategoryEntity> categoryEntities = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next())
                {
                    Optional<CategoryEntity> categoryEntityOptional = resultToCategory(rs);
                    categoryEntityOptional.ifPresent(categoryEntities::add);
                }

            }
        }
        return categoryEntities;
    }

    @Override
    public Optional<CategoryEntity> findById(int id) throws SQLException {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return resultToCategory(rs);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

    @Override
    public void save(CategoryEntity categoryEntity) throws SQLException {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,categoryEntity.getName());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(CategoryEntity categoryEntity) throws SQLException {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,categoryEntity.getName());
            ps.setInt(2,categoryEntity.getId());
            ps.executeUpdate();
        }
    }
}
