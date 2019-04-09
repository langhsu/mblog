package com.mtons.mblog.modules.data;

import com.mtons.mblog.modules.entity.Permission;

import java.util.LinkedList;
import java.util.List;

/**
 * @author - langhsu on 2018/2/11
 */
public class PermissionTree extends Permission {
    private List<PermissionTree> items;

    public List<PermissionTree> getItems() {
        return items;
    }

    public void setItems(List<PermissionTree> items) {
        this.items = items;
    }

    public void addItem(PermissionTree item){
        if(this.items == null){
            this.items = new LinkedList<>();
        }
        this.items.add(item);
    }
}
