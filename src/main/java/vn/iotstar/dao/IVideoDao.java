package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.entity.Video;

public interface IVideoDao {

	void insert(Video video);

	void update(Video video);

	void delete(String videoId) throws Exception;

	Video findById(String videoId);

	List<Video> findAll();

	List<Video> findLatest(int limit);

	List<Video> findByUserId(int userId, int page, int pageSize);

	long countByUserId(int userId);

	List<Video> findAllActive(int page, int pageSize);

	long countAllActive();

	void increaseViews(String videoId);
}
