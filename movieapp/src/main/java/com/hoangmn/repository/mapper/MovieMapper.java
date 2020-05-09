package com.hoangmn.repository.mapper;

import com.hoangmn.model.Movie;
import com.hoangmn.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MovieMapper {

    @Select("SELECT * FROM movie")
    List<Movie> getAll();

    @Select("SELECT * FROM movie WHERE id = #{id}")
    Movie get(@Param("id") int id);

    @Insert("INSERT INTO movie(id, title, description, trailer) values(null, #{title}, #{description}, #{trailer})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int save(Movie movie);

    @Delete("DELETE FROM movie where id = #{id}")
    int delete(@Param("id") int id);

    @Update("UPDATE movie SET title = coalesce (#{title}, title)," +
                             " description = coalesce (#{description}, description), " +
                             " trailer = coalesce (#{trailer}, trailer) " +
                             " WHERE id = #{id}")
    int update(Movie movie);

    @Select("SELECT * FROM movie where id IN (SELECT movieId FROM favorite where userId = #{userId} )")
    List<Movie> getFavs(int userId);

    @Select("SELECT * FROM movie where id NOT IN (SELECT movieId FROM favorite where userId = #{userId} )")
    List<Movie> getNonFavs(int userId);

    @Select("SELECT COUNT(*) FROM favorite where userId = #{userId} and movieId = #{movieId}")
    int MovieMapper(int userId, int movieId);

    @Insert("INSERT INTO favorite(userId, movieId) values(#{userId}, #{movieId})")
    int addToFavorites(int userId, int movieId);

    @Delete("DELETE FROM favorite where userId = #{userId} and movieId = #{movieId}")
    int removeFromFavorites(int userId, int movieId);

    @Select("SELECT * FROM users WHERE username = #{username} and password = #{password}")
    User getUser(String username, String password);

}
