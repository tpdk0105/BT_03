package vn.iotstar.dao.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JpaConfig;
import vn.iotstar.dao.UserDao;
import vn.iotstar.model.User;

public class UserDaoImpl implements UserDao {

	private User toModel(vn.iotstar.entity.User eu) {
		if (eu == null) {
			return null;
		}
		User u = new User();
		u.setId(eu.getId());
		u.setEmail(eu.getEmail());
		u.setUserName(eu.getUserName());
		u.setFullName(eu.getFullName());
		u.setPassWord(eu.getPassWord());
		u.setAvatar(eu.getAvatar());
		u.setRoleid(eu.getRoleid());
		u.setPhone(eu.getPhone());
		u.setCreatedDate(eu.getCreatedDate());
		u.setStatus(eu.getStatus());
		u.setCode(eu.getCode());
		return u;
	}

	@Override
	public User get(String username) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.userName = :username";
			TypedQuery<vn.iotstar.entity.User> query = em.createQuery(jpql, vn.iotstar.entity.User.class);
			query.setParameter("username", username);
			List<vn.iotstar.entity.User> list = query.getResultList();
			return list.isEmpty() ? null : toModel(list.get(0));
		} finally {
			em.close();
		}
	}

	@Override
	public User getByEmail(String email) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.email = :email";
			TypedQuery<vn.iotstar.entity.User> query = em.createQuery(jpql, vn.iotstar.entity.User.class);
			query.setParameter("email", email);
			List<vn.iotstar.entity.User> list = query.getResultList();
			return list.isEmpty() ? null : toModel(list.get(0));
		} finally {
			em.close();
		}
	}

	@Override
	public User findById(int id) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			return toModel(em.find(vn.iotstar.entity.User.class, id));
		} finally {
			em.close();
		}
	}

	@Override
	public List<User> findAll() {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u ORDER BY u.id DESC";
			List<vn.iotstar.entity.User> entities = em.createQuery(jpql, vn.iotstar.entity.User.class).getResultList();
			List<User> result = new ArrayList<>();
			for (vn.iotstar.entity.User eu : entities) {
				result.add(toModel(eu));
			}
			return result;
		} finally {
			em.close();
		}
	}

	@Override
	public void insert(User user) {
		EntityManager em = JpaConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			vn.iotstar.entity.User eu = new vn.iotstar.entity.User();
			eu.setEmail(user.getEmail());
			eu.setUserName(user.getUserName());
			eu.setFullName(user.getFullName());
			eu.setPassWord(user.getPassWord());
			eu.setAvatar(user.getAvatar());
			eu.setRoleid(user.getRoleid());
			eu.setPhone(user.getPhone());
			eu.setCreatedDate(user.getCreatedDate());
			eu.setStatus(user.getStatus());
			eu.setCode(user.getCode());
			em.persist(eu);
			trans.commit();
			user.setId(eu.getId());
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
	public void update(User user) {
		EntityManager em = JpaConfig.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			vn.iotstar.entity.User eu = em.find(vn.iotstar.entity.User.class, user.getId());
			if (eu != null) {
				eu.setEmail(user.getEmail());
				eu.setUserName(user.getUserName());
				eu.setFullName(user.getFullName());
				eu.setPassWord(user.getPassWord());
				eu.setAvatar(user.getAvatar());
				eu.setRoleid(user.getRoleid());
				eu.setPhone(user.getPhone());
				eu.setCreatedDate(user.getCreatedDate());
				eu.setStatus(user.getStatus());
				eu.setCode(user.getCode());
				em.merge(eu);
			}
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
	public boolean checkExistEmail(String email) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT count(u) FROM User u WHERE u.email = :email";
			Long count = em.createQuery(jpql, Long.class)
					.setParameter("email", email)
					.getSingleResult();
			return count != null && count > 0;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean checkExistUsername(String username) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT count(u) FROM User u WHERE u.userName = :username";
			Long count = em.createQuery(jpql, Long.class)
					.setParameter("username", username)
					.getSingleResult();
			return count != null && count > 0;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean checkExistPhone(String phone) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT count(u) FROM User u WHERE u.phone = :phone";
			Long count = em.createQuery(jpql, Long.class)
					.setParameter("phone", phone)
					.getSingleResult();
			return count != null && count > 0;
		} finally {
			em.close();
		}
	}
}
