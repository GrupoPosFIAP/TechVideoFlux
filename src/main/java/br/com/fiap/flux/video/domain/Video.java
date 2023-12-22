package br.com.fiap.flux.video.domain;

import br.com.fiap.flux.enums.Category;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Document(collection = "videos")
public class Video {

    @Id
    @MongoId(FieldType.STRING)
    private UUID id = UUID.randomUUID();

    private String titulo;
    private String descricao;
    private String url;
    private LocalDate dataPublicacao;
    private List<Category> categorias;
    private Long contadorFavoritos = 0L;
    private Long contadorVisualizacoes = 0L;

    public void incrementarFavorito() {
        this.contadorFavoritos++;
    }

    public void decrementarFavorito() {
        this.contadorFavoritos--;
    }
}
