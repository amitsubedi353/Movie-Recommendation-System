package com.movie.recommendation.repo;

import com.movie.recommendation.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WatchlistRepo extends JpaRepository<WatchList,Long> {
    @Query(value = "select w.watchlist_id,w.movie_id_fk,w.user_id_fk from watchlist_table w inner join user_table u on w.user_id_fk=u.user_id inner join movie_table m on m.movie_id=w.movie_id_fk where w.user_id_fk=?1 and w.movie_id_fk=?2",nativeQuery = true)
    WatchList findWatchlistByUserAndMovie(Long userId,Long movieId);
    @Query(value = "select w.watchlist_id,w.movie_id_fk,w.user_id_fk from watchlist_table w inner join user_table u on w.user_id_fk=u.user_id inner join movie_table m on m.movie_id=w.movie_id_fk where w.user_id_fk=?1",nativeQuery = true)
    List<WatchList> findWatchListForUser(Long userId);
}
