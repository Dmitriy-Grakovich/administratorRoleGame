package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;


public interface PlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player>  {

}
