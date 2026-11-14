package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.iotstar.config.JpaConfig;
import vn.iotstar.dao.IVideoDao;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Video;

public class VideoDao implements IVideoDao {

	@Override
	public void insert(Video video) {
		EntityManager em = JpaConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			if (video.getCategory() != null) {
				video.setCategory(em.getReference(Category.class, video.getCategory().getCategoryid()));
			}
			em.persist(video);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (trans.isActive()) {
				trans.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void update(Video video) {
		EntityManager em = JpaConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			if (video.getCategory() != null) {
				video.setCategory(em.getReference(Category.class, video.getCategory().getCategoryid()));
			}
			em.merge(video);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (trans.isActive()) {
				trans.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(String videoId) throws Exception {
		EntityManager em = JpaConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			Video video = em.find(Video.class, videoId);
			if (video == null) {
				throw new Exception("Không tìm thấy video");
			}
			em.remove(video);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (trans.isActive()) {
				trans.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public Video findById(String videoId) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			return em.find(Video.class, videoId);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Video> findAll() {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			return em.createNamedQuery("Video.findAll", Video.class).getResultList();
		} finally {
			em.close();
		}
	}
}
