package Services;

import Entities.CategoryEntity;
import Entities.GameEntity;
import Repositories.implementation.GameRepository;
import lombok.Getter;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GameService {

    private final GameRepository gameRepository;
    @Getter
    private static final GameService instance = new GameService();
    private GameService(){
        gameRepository = GameRepository.getInstance();
    }

    public void chargeGame (GameEntity game){
        try {
            gameRepository.save(game);
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
    }
    public void deleteGame(GameEntity game){
        try {
            gameRepository.deleteById(game.getId());
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
    }
    public String listAll(){
        String message = "List of games: ";
        try {
            message += gameRepository.findAll().toString();
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
        return message;
    }
    public GameEntity findById (int id){
        Optional<GameEntity> gameEntityOptional;
        try {
            gameEntityOptional = gameRepository.findById(id);
            if (gameEntityOptional.isEmpty()){
                throw new NoSuchElementException("Game not found");
            }
            return gameEntityOptional.get();
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
        return GameEntity.builder().build();
    }
    public void updateGame (GameEntity gameEntity){
        try {
            Optional<GameEntity> gameEntityOptional = gameRepository.findById(gameEntity.getId());
            if (gameEntityOptional.isEmpty()){
                throw new NoSuchElementException("Game not found");
            }
            gameRepository.update(gameEntity);
        } catch (SQLException e) {
            System.out.println("Error in the connection with the database");
        }catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }
}
