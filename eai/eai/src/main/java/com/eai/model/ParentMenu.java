package com.eai.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "parent_menu")
public class ParentMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PARENT_MENU", nullable = false)
    private Integer idParentMenu;
    
    @Column(name = "MENU_NAME", nullable = false, length = 45)
    private String menuName;
    
    @Column(name = "ORDER", nullable = false)
    private int order;
    
    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentMenu", fetch = FetchType.LAZY)
    private List<Menu> menuList;

    public ParentMenu() {
    }

    public ParentMenu(Integer idParentMenu) {
        this.idParentMenu = idParentMenu;
    }

    public ParentMenu(Integer idParentMenu, String menuName, int order) {
        this.idParentMenu = idParentMenu;
        this.menuName = menuName;
        this.order = order;
    }

    public Integer getIdParentMenu() {
        return idParentMenu;
    }

    public void setIdParentMenu(Integer idParentMenu) {
        this.idParentMenu = idParentMenu;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }   
}