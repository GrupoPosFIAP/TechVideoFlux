package br.com.fiap.flux.video.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VideoCriteria {

    String titulo;

    LocalDate dataPublicacao;


    public Video toVideo() {
        return new Video()
                .withDataPublicacao(this.dataPublicacao != null ? this.dataPublicacao : null)
                .withTitulo(this.titulo != null ? this.titulo : null);
    }
}
