package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JpaConfig;
import vn.iotstar.dao.UserDao;
import vn.iotstar.model.User;

public class UserDaoImpl implements UserDao {

	@Override
	public User get(String username) {
		EntityManager em = JpaConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.userName = :username";
			TypedQuery<vn.iotstar.entity.User> query = em.createQuery(jpql, vn.iotstar.entity.User.class);
			query.setParameter("username", username);
			List<vn.iotstar.entity.User> list = query.getResultList();
			if (list.isEmpty()) {
				return null;
			}
			vn.iotstar.entity.User eu = list.get(0);
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
			return u;
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
