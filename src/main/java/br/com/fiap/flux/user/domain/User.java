package br.com.fiap.flux.user.domain;

import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.flux.enums.Category;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.fiap.flux.video.domain.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

@Data
@Getter
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User {

    @Id
    private String id;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Version
    private Long version;

    @Builder.Default
    private Boolean delete = Boolean.FALSE;

    private String name;

    @Indexed(unique = true)
    private String email;

    private List<Video> favorites;

    public Flux<Category> getCategorias() {
        return Flux.fromStream(favorites.stream().flatMap(video -> video.getCategorias().stream()));
    }
}
