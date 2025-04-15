package Entities;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    private Integer id;
    private String name;

    public CategoryEntity(String name){
        this.name = name;
        id = 0;
    }

    @Override
    public String toString() {
        return "\nCategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
