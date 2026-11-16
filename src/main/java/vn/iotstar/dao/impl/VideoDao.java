package vn.iotstar.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JpaConfig;
import vn.iotstar.dao.IVideoDao;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
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
			if (video.getUser() != null && video.getUser().getId() > 0) {
				video.setUser(em.getReference(User.class, video.getUser().getId()));
			}
			if (video.getCreatedDate() == null) {
				video.setCreatedDate(new Timestamp(System.currentTimeMillis()));
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
			if (video.getUser() != null && video.getUser().getId() > 0) {
				video.setUser(em.getReference(User.class, video.getUser().getId()));
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

	@Override
	public List<Video> findLatest(int limit) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT v FROM Video v WHERE v.active = 1 ORDER BY v.createdDate DESC, v.videoId DESC";
			TypedQuery<Video> q = em.createQuery(jpql, Video.class);
			q.setMaxResults(limit);
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Video> findByUserId(int userId, int page, int pageSize) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT v FROM Video v WHERE v.user.id = :uid ORDER BY v.createdDate DESC, v.videoId DESC";
			TypedQuery<Video> q = em.createQuery(jpql, Video.class);
			q.setParameter("uid", userId);
			q.setFirstResult((page - 1) * pageSize);
			q.setMaxResults(pageSize);
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public long countByUserId(int userId) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT count(v) FROM Video v WHERE v.user.id = :uid";
			return em.createQuery(jpql, Long.class)
					.setParameter("uid", userId)
					.getSingleResult();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Video> findAllActive(int page, int pageSize) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT v FROM Video v WHERE v.active = 1 ORDER BY v.createdDate DESC, v.videoId DESC";
			TypedQuery<Video> q = em.createQuery(jpql, Video.class);
			q.setFirstResult((page - 1) * pageSize);
			q.setMaxResults(pageSize);
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public long countAllActive() {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT count(v) FROM Video v WHERE v.active = 1";
			return em.createQuery(jpql, Long.class).getSingleResult();
		} finally {
			em.close();
		}
	}

	@Override
	public void increaseViews(String videoId) {
		EntityManager em = JpaConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			Video v = em.find(Video.class, videoId);
			if (v != null) {
				v.setViews(v.getViews() + 1);
				em.merge(v);
			}
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive()) {
				trans.rollback();
			}
		} finally {
			em.close();
		}
	}
}
