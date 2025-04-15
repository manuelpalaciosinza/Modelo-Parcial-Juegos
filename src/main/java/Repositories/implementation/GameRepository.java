package Repositories.implementation;

import Connections.DatabaseConnection;
import Entities.GameEntity;
import Repositories.interfaces.IRepository;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class GameRepository implements IRepository<GameEntity> {
    @Getter
    private static final GameRepository instance = new GameRepository();

    private GameRepository() {};


    private Optional<GameEntity> resultToGame(ResultSet rs) throws SQLException {
        return Optional.of(GameEntity.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .category_id(rs.getInt("category_id"))
                .build());
    }

    @Override
    public ArrayList<GameEntity> findAll() throws SQLException {
        ArrayList<GameEntity> games = new ArrayList<>();
        String sql = "SELECT * FROM games";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Optional<GameEntity> gameEntityOptional = resultToGame(rs);
                    gameEntityOptional.ifPresent(games::add);
                }
            }
        }
        return games;
    }

    @Override
    public Optional<GameEntity> findById(int id) throws SQLException {
        String sql = "SELECT * FROM games WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return resultToGame(rs);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM games WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

    @Override
    public void save(GameEntity gameEntity) throws SQLException {
        String sql = "INSERT INTO games(title, category_id) VALUES(?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,gameEntity.getTitle());
            ps.setInt(2,gameEntity.getCategory_id());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(GameEntity gameEntity) throws SQLException {
        String sql = "UPDATE games SET title = ?, category_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,gameEntity.getTitle());
            ps.setInt(2,gameEntity.getCategory_id());
            ps.setInt(3,gameEntity.getId());
            ps.executeUpdate();
        }
    }


}
