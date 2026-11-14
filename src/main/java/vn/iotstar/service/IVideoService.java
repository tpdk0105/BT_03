package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.Video;

public interface IVideoService {

	/** @return false nếu mã video đã tồn tại */
	boolean insert(Video video);

	void update(Video video);

	void delete(String videoId);

	Video findById(String videoId);

	List<Video> findAll();

	String generateNextVideoId();
}
