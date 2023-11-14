package br.com.fiap.flux.utils;

import br.com.fiap.flux.video.domain.Video;
import br.com.fiap.flux.video.domain.VideoCriteria;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.query.Criteria;

public class CriteriaBuilder {

    private CriteriaBuilder() {

    }

    public static Criteria buildCriteria(VideoCriteria videoCriteria) {
        ExampleMatcher matcher = createMatcher(videoCriteria);
        Example<Video> videoExample = Example.of(videoCriteria.toVideo(), matcher);

        return new Criteria().alike(videoExample);
    }

    private static ExampleMatcher createMatcher(VideoCriteria videoCriteria) {
        // Adiciona apenas os atributos de filtro que foram inseridos pelo usuário
        return ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnorePaths(
                        videoCriteria.getDataPublicacao() == null ? "dataPublicacao" : "",
                        videoCriteria.getTitulo() == null ? "titulo" : "",
                        "id",
                        "descricao",
                        "url")
                .withIgnoreCase();
    }
}
