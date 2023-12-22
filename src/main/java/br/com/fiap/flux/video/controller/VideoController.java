package br.com.fiap.flux.video.controller;

import br.com.fiap.flux.video.domain.Estatistica;
import br.com.fiap.flux.video.domain.Video;
import br.com.fiap.flux.video.domain.VideoCriteria;
import br.com.fiap.flux.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Video> insert(@RequestBody Video video) {
        return this.videoService.insert(video);
    }

    @GetMapping
    public Mono<PageImpl<Video>> findAll(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "DESC") String direction,
                                         VideoCriteria videoCriteria) {
        PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(direction), "dataPublicacao");
        return this.videoService.findAll(pageRequest, videoCriteria);
    }

    @GetMapping("/{id}")
    public Mono<Video> findById(@PathVariable UUID id) {
        return this.videoService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@PathVariable UUID id, @RequestBody Video video) {
        return this.videoService.update(id, video);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        return this.videoService.delete(id);
    }



    /**
     * --------------------------------------------------------------
     * Ao salvar o favorito, incrementar o contador da entidade Video
     * --------------------------------------------------------------
     * Recuperar o vídeos da base (mongo -findById)
     * Incrementar o contador de favoritos (vídeos)
     * Adicionar na lista do usuário
     * Persistir a entidade vídeo
     * Persistir a entidade usuário (salvar somente o ID do vídeo)
     * --------------------------------------------------------------
     * Observação: Implentei a opção para desfavoritar um vídeo
     * --------------------------------------------------------------
     */
    @PutMapping("/favoritar/{userId}/{videoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> favoriteVideo(
            @PathVariable(name = "UserId") String userId,
            @PathVariable(name = "videoId") UUID videoId) {

        return this.videoService.favoriteVideo(userId, videoId);
    }

    @PutMapping("/desfavoritar/{userId}/{videoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> defavoriteVideo(
            @PathVariable(name = "UserId") String userId,
            @PathVariable(name = "videoId") UUID videoId) {

        return this.videoService.defavoriteVideo(userId, videoId);
    }



    /**
     * O Endpoint Estatísticas deve retornar:
     * 1- A quantidade total de vídeos (Count - findAll)
     * 2- A quantidade de vídeos favoritados
     * 3- Média de visualizações (quantidade de visualizações total / quantidade de vídeos)
     */
    @GetMapping("/estatisticas")
    public Mono<Estatistica> estatisticas() {
        return this.videoService.estatisticas();
    }
}

