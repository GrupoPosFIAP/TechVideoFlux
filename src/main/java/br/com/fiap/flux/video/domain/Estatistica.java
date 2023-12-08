package br.com.fiap.flux.video.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estatistica {

    private Integer quantidadeVideos;
    private Long quantidadeVideosFavoritos;
    private Long mediaVisualizacoes;

}
