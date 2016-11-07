package com.crab.redis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.crab.redis.domain.SysDataDic;
import com.crab.redis.mapper.SysDataDicMapper;
import com.crab.redis.service.ISysDataDicService;

@Service
public class SysDataDicServiceImpl implements ISysDataDicService {
	@Autowired
	private SysDataDicMapper mapper;

	@Override
	public Map<String, String> findAll() {
		// get key-value
		Map<String, String> kv = getKVOnly();
		// get tree-key-value
		Map<String, String> tree = getTreeKV();
		// merge
		kv.putAll(tree);
		return kv;
	}

	@Override
	public SysDataDic findOne(int id) {
		return mapper.findOne(id);
	}

	/**
	 * get key-value only, not include complex.
	 * 
	 * @return map for JSON.
	 */
	private Map<String, String> getKVOnly() {
		Map<String, String> map = new HashMap<String, String>();
		List<SysDataDic> list = mapper.findKV();
		for (SysDataDic bean : list) {
			map.put(bean.getDic_key(), bean.getDic_value());
		}
		return map;
	}

	/**
	 * get complex key-value, like tree construct.
	 * 
	 * @return map for JSON.
	 */
	private Map<String, String> getTreeKV() {
		// get all root nodes.
		List<SysDataDic> roots = mapper.findTreeRoot();
		// get child list by root node.
		for (SysDataDic root : roots) {
			setChildByRecursive(root);
		}
		// transform for JSON
		Map<String, String> map = new HashMap<String, String>();
		for (SysDataDic root : roots) {
			map.put(root.getDic_key(), JSON.toJSONString(root.getChild()));
		}
		return map;
	}

	/**
	 * set child list by recursive.
	 * 
	 * @param root
	 */
	private void setChildByRecursive(SysDataDic root) {
		List<SysDataDic> childList = mapper.findTreeChild(root.getId());
		if (null != childList && !childList.isEmpty()) {
			root.setChild(childList);
			// next
			for (SysDataDic _root : childList) {
				setChildByRecursive(_root);
			}
		}
	}
}
