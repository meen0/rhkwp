package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    //id로 엔티티 조회
    public MenuDTO findMenuByCode(int menuCode) {
        //Entity로 조회한 뒤 비영속 객체인 MenuDTO로 변환해서 반환한다.
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalAccessError::new);

        return modelMapper.map(menu, MenuDTO.class);
    }

    // 모든 엔티티 조회 //페이징 이전(sort)
//    public List<MenuDTO> findAllMenu(){
//        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());



    //페이징 이후 페이징된 entity조회
    public Page<MenuDTO> findAllMenu(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending());

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }


    // query method 테스트
    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {
        List<Menu> menuList = menuRepository.findMenuByMenuPriceGreaterThan(menuPrice,Sort.by("menuPrice").descending());

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    /* JPQL or Native Query Test */
    public List<CategoryDTO> findAllCategory(){
        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream().map(category->modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }
    //entity 저장
    @Transactional
    public void registNewMenu(MenuDTO menu) {
        menuRepository.save(modelMapper.map(menu,Menu.class));
    }

    // entity 수정
    @Transactional
    public void modifyMenu(MenuDTO menu) {
        Menu foundMenu = menuRepository.findById(menu.getMenuCode()).orElseThrow(IllegalAccessError::new);
        foundMenu.setMenuName(menu.getMenuName());
    }

    public void deleteMenu(Integer menuCode) {
        menuRepository.deleteById(menuCode);

    }

    public List<MenuDTO> findByCategoryCode(Integer categoryCode) {

        List<Menu> menuList = menuRepository.findByCategory(categoryCode);

        return menuList.stream().map(list -> modelMapper.map(list,MenuDTO.class)).collect(Collectors.toList());

    }

    public List<MenuDTO> findByMenuName(String menuName) {

        List<Menu> menuList = menuRepository.findByMenuName(menuName);

        return menuList.stream().map(menu->modelMapper.map(menu,MenuDTO.class)).collect(Collectors.toList());
    }

    public List<MenuDTO> findByMenuStatus(String stat) {
        List<Menu> menuList = menuRepository.findMenuByStatus(stat);

        return menuList.stream().map(menu->modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }
}
