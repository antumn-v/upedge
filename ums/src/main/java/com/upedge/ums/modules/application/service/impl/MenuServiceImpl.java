package com.upedge.ums.modules.application.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.MenuVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.application.dao.MenuDao;
import com.upedge.ums.modules.application.entity.Menu;
import com.upedge.ums.modules.application.request.MenuTreeRequest;
import com.upedge.ums.modules.application.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Menu record = new Menu();
        record.setId(id);
        return menuDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Menu record) {
        return menuDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Menu record) {
        return menuDao.insert(record);
    }

    @Override
    public void addMenus(List<Menu> menus, Session session) {
        Date date = new Date();
        for (Menu menu : menus) {
            menu.setCreateTime(date);
            menu.setUpdateTime(date);
        }
        menuDao.insertByBatch(menus);
    }

    @Override
    public List<MenuVo> menuTree(Session session, MenuTreeRequest request) throws CustomerException {
        List<MenuVo> menus=new ArrayList<>();
        //超级管理员
        if(session.getUserType().intValue() == BaseCode.USER_ROLE_SUPERADMIN) {
            Long applicationId=session.getApplicationId();
            if(request.getApplicationId()!=null){
                applicationId=request.getApplicationId();
            }
            Page<Menu> menuPage = new Page<>();
            Menu menu=new Menu();
            //菜单分组
            if(!StringUtils.isBlank(request.getMenuGroup())){
                menu.setMenuGroup(request.getMenuGroup());
            }
            menu.setApplicationId(applicationId);
            //所有菜单
            menuPage.setPageSize(-1);
            menuPage.setT(menu);
            List<Menu> list=menuDao.select(menuPage);
            menus=treeMenu(list);
//            permission(session.getApplicationId(),menus);
        }else {
            Page<Menu> menuPage = new Page<>();
            Menu menu=new Menu();
            menu.setApplicationId(session.getApplicationId());
            menu.setMenuType(BaseCode.MENU_NORMAL);
            menuPage.setPageSize(-1);
            menuPage.setT(menu);
            List<Menu> list = select(menuPage);
            menus = treeMenu(list);
//            permission(session.getApplicationId(), menus);
            return menus;
        }
        return menus;
    }

    /**
     *
     */
    public Menu selectByPrimaryKey(Long id){
        Menu record = new Menu();
        record.setId(id);
        return menuDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Menu record) {
        return menuDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Menu record) {
        return menuDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Menu> select(Page<Menu> record){
        record.initFromNum();
        return menuDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Menu> record){
        return menuDao.count(record);
    }


    /**
     * 将菜单项处理成树结构
     * @param list 已经根据menupath seq id进行排序的菜单项列表
     * @return
     * @throws CustomerException
     */
    public List<MenuVo> treeMenu(List<Menu> list) throws CustomerException {
        // 初始化结果
        List<MenuVo> result = new ArrayList<MenuVo>();
        // 初始化索引
        Map<Long, MenuVo> map = new HashMap<Long,MenuVo>();
        // 死循环阈值
        int maxLoop = (list.size() + 1)*list.size()/2;
        while(list.size() > 0) {
            Menu menu = list.get(0);
            MenuVo menuVo = new MenuVo();
            menuVo.setChildren(new ArrayList<>());
            BeanUtils.copyProperties(menu,menuVo);
            // 一级节点
            if(menu.getParentId().longValue() == 0L) {
                result.add(menuVo);
                map.put(menu.getId(),menuVo);
                list.remove(0);
            }
            else {
                //其他节点
                if(map.containsKey(menu.getParentId())) {
                    //父节点地址在map中有记录
                    MenuVo parent = map.get(menu.getParentId());
                    parent.getChildren().add(menuVo);
                    map.put(menu.getId(), menuVo);
                    list.remove(0);
                }
                else {
                    // 如果没有找到父节点就将其移动到队列的末尾
                    list.remove(0);
                    list.add(menu);
                }
            }
            if(maxLoop-- < 0) {
                //当循环到此时，说明有孤立节点，终止循环
                throw new CustomerException(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
            }
        }
        sorMenu(result);
        return result;
    }

    /**
     * 排序菜单
     */
    private void sorMenu(List<MenuVo> menus) {
        Collections.sort(menus);
        for(MenuVo menu : menus) {
            if(menu.getChildren().size() != 0) {
                Collections.sort(menu.getChildren());
                sorMenu(menu.getChildren());
            }
        }
    }

//    public void permission(Long applicationId,List<Menu> menus){
//        List<MenuPermission> permissions = menuPermissionMapper.listMenuPermission(applicationId);
//        Map<Long,List<MenuPermission>> pMap = new HashMap<Long,List<MenuPermission>>();
//        for(MenuPermission mp : permissions) {
//            if(pMap.containsKey(mp.getMenuId())) {
//                pMap.get(mp.getMenuId()).add(mp);
//            }
//            else {
//                List<MenuPermission> ps = new ArrayList<MenuPermission>();
//                ps.add(mp);
//                pMap.put(mp.getMenuId(), ps);
//            }
//        }
//        addPermission(pMap,menus);
//    }
}