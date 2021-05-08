package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.domain.Player;

//@Mapper
//public interface PlayerMapper {
//
//    List<Player> findAll();
//
//    Player findOne(Long id);
//
//    void save(Player player);
//
//    void update(Player player);
//
//    void delete(Long id);
//
//    List<Player> ranking();
//}
@Mapper
public interface PlayerMapper {
    @Select("select * from player")
    List<Player> findAll();

    @Select("select * from player where id = #{id}")
    Player findOne(Long id);

    @Insert("insert into player (name, team, point) values (#{name}, #{team}, #{point})")
    @Options(useGeneratedKeys = true)
    void save(Player player);

    @Update("update player set name = #{name}, team = #{team}, point = #{point} where id = #{id}")
    void update(Player player);

    @Delete("delete from player where id = #{id}")
    void delete(Long id);

    @Select("select team, sum(point) point from player group by team order by point desc")
    List<Player> ranking();

}
