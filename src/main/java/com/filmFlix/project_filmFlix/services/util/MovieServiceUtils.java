package com.filmFlix.project_filmFlix.services.util;
import com.filmFlix.project_filmFlix.entities.Movie;
import com.filmFlix.project_filmFlix.projections.MovieMinProjection;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MovieServiceUtils {
    public static List<Movie> orderList(List<Movie> unorderedList, List<MovieMinProjection>orderedList){
        Map<Long, Movie> map = new HashMap<>();
        for(Movie obj : unorderedList ){
            map.put(obj.getId(), obj);
        }
        return orderedList.stream().map(x -> map.get(x.getId())).toList();
    }
}
