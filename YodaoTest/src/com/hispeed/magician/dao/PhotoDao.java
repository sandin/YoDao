package com.hispeed.magician.dao;

import java.util.List;

import com.hispeed.magician.model.Photo;
import com.yoda.yodao.YoDao;
import com.yoda.yodao.annotation.Query;
import com.yoda.yodao.annotation.Repository;

@Repository
public interface PhotoDao extends YoDao<Photo, String> {

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
	
	int updateByViewType(Photo entity, int type);
	
	@Query("select * from photo where type = ?")
	Photo findBySQL(int type);

	@Query("select * from photo where type = ?")
	List<Photo> findListBySQL(int type);

	@Query("select count(*) from photo where type = ? and name = ?")
	long countBySQL(int type, String name);

}
