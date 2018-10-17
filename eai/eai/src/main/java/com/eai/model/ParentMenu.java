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
    
    @Column(name = "MENU_ICON", nullable = false, length = 45)
    private String menuIcon;
    
    @Column(name = "ORDER", nullable = false)
    private int order;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentMenu", fetch = FetchType.LAZY)
    private List<Menu> menuList;

    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;
    
    public Integer getIdParentMenu() {
        return idParentMenu;
    }

    public void setIdParentMenu(Integer idParentMenu) {
        this.idParentMenu = idParentMenu;
    }
    
    public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Menu> getMenuList() {
       return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}