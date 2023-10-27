package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu,Integer> {

    //전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록 조회
   List<Menu> findMenuByMenuPriceGreaterThan(Integer menuPrice);

    //전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격 오름차순으로 조회
    List<Menu> findMenuByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);

    //전달 받은 menuPrice보다 큰 menuPrice를 가진 메뉴 목록을 메뉴 가격 오름차순으로 조회
    List<Menu> findMenuByMenuPriceGreaterThan(Integer menuPrice, Sort sort);

    @Query(value = "SELECT m FROM Menu m WHERE m.categoryCode = ?1 ")
    List<Menu> findByCategory(Integer categoryCode);

    @Query(value = "SELECT m FROM Menu m WHERE m.menuName LIKE %?1% ")
    List<Menu> findByMenuName(String menuName);

    @Query(value = "SELECT MENU_CODE,MENU_NAME,MENU_PRICE,CATEGORY_CODE,ORDERABLE_STATUS FROM TBL_MENU WHERE ORDERABLE_STATUS = :stat",
    nativeQuery = true)
    List<Menu> findMenuByStatus(String stat);
}
