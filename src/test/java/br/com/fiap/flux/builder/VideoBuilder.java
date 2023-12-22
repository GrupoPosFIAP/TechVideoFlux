package br.com.fiap.flux.builder;

import br.com.fiap.flux.enums.Category;
import br.com.fiap.flux.video.domain.Video;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class VideoBuilder {
    public static Video publicarVideo() {

        Video video = new Video();

        video.setId(UUID.randomUUID());
        video.setTitulo("Título");
        video.setDescricao("Descrição");
        video.setUrl("https://www.techvideoflux.com.br/video1234");
        video.setDataPublicacao(LocalDate.now());
        //video.setCategorias(new List<Category>);
        video.setContadorFavoritos(2L);
        video.setContadorVisualizacoes(2L);

        return video;
    }
}
