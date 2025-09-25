package com.gec.service;

/*
 * 主要用途: (通用节点)
 * 1. 处理树形节点的数据(解析, 封装)。
 * 2. 组织相关数据, 生成树形结构。
 * 3. 甚至可以实现查找功能。
 * 4. 对一个树形结构进行 增, 删, 改.
 *
 * [+]一级部门: 总经办
 *    [+]二级部门: 行政部
 *                人事部
 *       [+]三级部门
 *
 * [+]一级部门: 公司工会(委员会)
 *     [+]二级部门: 行政部
 * [+]一级部门: 董事会
 *
 */

import com.gec.domain.entity.Node;

import java.util.ArrayList;
import java.util.List;

public interface IBaseService {

    //1. 原本的 list 没有分层结构处理的。
    //2. 这里给它做一下分层处理, 转换为带分层结构的列表。
    default List<Node> convertNodeBO(
            List<? extends Node> list){
        //1. 指定一个顶层节点 ID
        Integer parentId = 0;

        //2. 创建一级列表
        ArrayList<Node> topList = new ArrayList<>();
        for( Node node : list){
            //3. 获取 node 的父节点 ID
            Integer parID = node.getParentId();
            //4. 如果 parID==0, node 就是一级节点。
            if(parentId.equals(parID) ){
                //5.做节点拷贝（适配所有不同类型：部门，类别，文件）
                Node nodeBO = copyObj(node);
                // 设置一级节点的level为1
                nodeBO.setLevel(1);
                //6.查找子节点，把子节点加入到nodeBO下面
                findChildren(nodeBO, list);
                //7. 添加到 topList
                topList.add( nodeBO );
            }
        }
        return topList; //返回创建的树形结构列表。
    }

    default void findChildren(
            Node D, List<? extends Node> list){
        //1.获取当前节点的 ID
        Integer parentId = D.getId();
        for(Node node : list){
            Integer parID = node.getParentId();
            //当前node父ID==parentId,认定它是D的子级节点
            if( parentId.equals(parID) ){
                Node nodeBO = copyObj(node);
                // 设置子节点的level为父节点level+1，添加空值检查
                Integer parentLevel = D.getLevel();
                if (parentLevel == null) {
                    parentLevel = 0;
                }
                nodeBO.setLevel(parentLevel + 1);
                //6. 查找子节点，把子节点加入到nodeBO下面
                findChildren(nodeBO, list);
                //7.添加到D 的子级列表。
                D.addChildNode( nodeBO );
            }
        }
    }

    //这个方法是在实现类中实施。
    Node copyObj(Node node);
}
