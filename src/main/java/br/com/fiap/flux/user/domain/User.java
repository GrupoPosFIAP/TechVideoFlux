package br.com.fiap.flux.user.domain;

import br.com.fiap.flux.video.domain.Video;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

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

    public User update(User toUpdate) {

        var name = toUpdate.getName();
        if(StringUtils.hasText(name)) {
            this.name = name;
        }

        var email = toUpdate.getEmail();
        if(StringUtils.hasText(email)) {
            this.email = email;
        }

        var favorites = toUpdate.getFavorites();
        if(null != toUpdate.getFavorites() && !favorites.isEmpty()) {
            this.favorites = favorites;
        }

        return this;
    }

}
