package com.filmFlix.project_filmFlix.services;

import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewMaxDto;
import com.filmFlix.project_filmFlix.projections.ReviewProjection;
import com.filmFlix.project_filmFlix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Transactional
    public Page<ReviewMaxDto> findAllByMovie(Pageable pageable, Long id){
        Page<ReviewProjection> pages = repository.findAllByMovie(pageable, id);
        return pages.map(ReviewMaxDto::new);
    }

//    public ReviewDto insert(ReviewDto dto) { preciso pegar o id do usuario logado apos fazer a segurança
//        repository.save(new Review(dto.getText(), dto.))
//    }

//    @Transactional
//    public GenreDto findById(Long id){
//        return repository.findById(id).map(GenreDto::new).orElseThrow(()-> new RuntimeException("id nao encontrado"));
//    }
//
//
//    @Transactional
//    public GenreDto update(Long id, GenreDto dto){
//        var entitie = repository.findById(id).orElseThrow(() -> new RuntimeException("id nao encontrado"));
//
//        entitie.setName(dto.getName());
//        repository.save(entitie);
//
//        return Stream.of(entitie).map(GenreDto::new).toList().getFirst();
//    }
}
