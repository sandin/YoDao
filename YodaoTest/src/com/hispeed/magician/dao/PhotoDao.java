package com.hispeed.magician.dao;

import java.util.List;

import com.hispeed.magician.model.Photo;
import com.yoda.yodao.YoDao;
import com.yoda.yodao.annotation.Repository;

@Repository
public interface PhotoDao extends YoDao<Photo> {

	List<Photo> findListByViewType(int type);

	List<Photo> findListByModelingUUId(String mid);

	/**
	 * 根据版本值不为0判断是否已同步（云存储 num使用）
	 * 
	 * @param ver
	 *            版本值
	 * @return
	 */
	long countByOpStatus(int opStatus);

}
