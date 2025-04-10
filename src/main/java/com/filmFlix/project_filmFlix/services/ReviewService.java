package com.filmFlix.project_filmFlix.services;

import com.filmFlix.project_filmFlix.Exceptions.ResourcesNotFoundException;
import com.filmFlix.project_filmFlix.Exceptions.UnauthorizedException;
import com.filmFlix.project_filmFlix.dtos.genreDtos.GenreDto;
import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewDto;
import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewMaxDto;
import com.filmFlix.project_filmFlix.dtos.reviewsDtos.ReviewRequestDto;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.entities.Review;
import com.filmFlix.project_filmFlix.entities.User;
import com.filmFlix.project_filmFlix.projections.ReviewProjection;
import com.filmFlix.project_filmFlix.repositories.MovieRepository;
import com.filmFlix.project_filmFlix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public Page<ReviewMaxDto> findAllByMovie(Pageable pageable, Long id){
        Page<ReviewProjection> pages = repository.findAllByMovie(pageable, id);
        return pages.map(ReviewMaxDto::new);
    }

    @Transactional
    public ReviewMaxDto insert(ReviewRequestDto dto)  {
        var userAuth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userAuth.getPrincipal();
        Review review = new Review();
        review.setText(dto.text());
        review.setMovie(movieRepository.findById(dto.moivieId()).orElseThrow(() -> new ResourcesNotFoundException("filme nao encontrado")));
        review.setUser(user);
        var entity = repository.save(review);
        return new ReviewMaxDto(entity);
    }

    @Transactional
    public ReviewMaxDto findById(Long id){
        return repository.findById(id).map(ReviewMaxDto::new).orElseThrow(()-> new ResourcesNotFoundException("filme nao encontrado"));
    }

    @Transactional
    public void delete(Long id){
        var userAuth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userAuth.getPrincipal();
        Review review = repository.findById(id).orElseThrow(()-> new ResourcesNotFoundException("filme nao encontrado"));
        if(!review.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException("voce nao pode apagar este comentario");
        }
        repository.deleteById(id);
    }
    @Transactional
    public ReviewMaxDto update(Long id, ReviewRequestDto dto){
        var userAuth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userAuth.getPrincipal();
        Review review = repository.findById(id).orElseThrow(()-> new ResourcesNotFoundException("filme nao encontrado"));
        if(!review.getUser().getId().equals(user.getId())){
            throw new UnauthorizedException("voce nao pode auterar este comentario");
        }
        review.setText(dto.text());
        var entity = repository.save(review);
        return new ReviewMaxDto(entity);
    }
}
