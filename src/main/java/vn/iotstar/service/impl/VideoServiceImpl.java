package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.IVideoDao;
import vn.iotstar.dao.impl.VideoDao;
import vn.iotstar.entity.Video;
import vn.iotstar.service.IVideoService;

public class VideoServiceImpl implements IVideoService {

	public IVideoDao videoDao = new VideoDao();

	@Override
	public boolean insert(Video video) {
		if (videoDao.findById(video.getVideoId()) != null) {
			return false;
		}
		videoDao.insert(video);
		return true;
	}

	@Override
	public void update(Video video) {
		if (videoDao.findById(video.getVideoId()) != null) {
			videoDao.update(video);
		}
	}

	@Override
	public void delete(String videoId) {
		try {
			videoDao.delete(videoId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Video findById(String videoId) {
		return videoDao.findById(videoId);
	}

	@Override
	public List<Video> findAll() {
		return videoDao.findAll();
	}

	@Override
	public List<Video> findLatest(int limit) {
		return videoDao.findLatest(limit);
	}

	@Override
	public List<Video> findByUserId(int userId, int page, int pageSize) {
		return videoDao.findByUserId(userId, page, pageSize);
	}

	@Override
	public long countByUserId(int userId) {
		return videoDao.countByUserId(userId);
	}

	@Override
	public List<Video> findAllActive(int page, int pageSize) {
		return videoDao.findAllActive(page, pageSize);
	}

	@Override
	public long countAllActive() {
		return videoDao.countAllActive();
	}

	@Override
	public void increaseViews(String videoId) {
		videoDao.increaseViews(videoId);
	}

	@Override
	public String generateNextVideoId() {
		List<Video> list = videoDao.findAll();
		int max = 0;
		for (Video v : list) {
			String id = v.getVideoId();
			if (id != null && id.matches("(?i)^v\\d+$")) {
				try {
					int num = Integer.parseInt(id.substring(1));
					if (num > max) {
						max = num;
					}
				} catch (NumberFormatException ignored) {
				}
			}
		}
		String nextId = String.format("v%02d", max + 1);
		while (videoDao.findById(nextId) != null) {
			max++;
			nextId = String.format("v%02d", max + 1);
		}
		return nextId;
	}
}
