package Entities;

import lombok.*;

@NoArgsConstructor
@Builder
@Setter
@Getter
@AllArgsConstructor

public class GameEntity {
    private Integer id;
    private String title;
    private Integer category_id;

    public GameEntity(String title, Integer category_id){
        this.title = title;
        this.category_id = category_id;
        id = 0;
    }

    @Override
    public String toString() {
        return "\nGameEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category_id=" + category_id +
                '}';
    }
}
