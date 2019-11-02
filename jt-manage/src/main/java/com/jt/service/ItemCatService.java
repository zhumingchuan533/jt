package com.jt.service;

import java.util.List;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUiTree;

public interface ItemCatService {
    
	ItemCat findItemCatById(Long itemCatId);

	List<EasyUiTree> findCatParentId(Long parentId);

	

	
 
}
