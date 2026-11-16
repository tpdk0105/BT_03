package vn.iotstar.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Video;
import vn.iotstar.model.User;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IVideoService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.VideoServiceImpl;
import vn.iotstar.util.UploadUtil;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 50, // 50MB
		maxRequestSize = 1024 * 1024 * 100) // 100MB
@WebServlet(urlPatterns = { "/video", "/video/detail", "/video/add", "/video/insert", "/video/edit", "/video/update",
		"/video/delete" })
public class UserVideoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 6;

	private final IVideoService videoService = new VideoServiceImpl();
	private final ICategoryService cateService = new CategoryServiceImpl();

	private boolean isManagerOrAdmin(User user) {
		return user != null && (user.getRoleid() == 1 || user.getRoleid() == 2);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("account") == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		User currentUser = (User) session.getAttribute("account");
		boolean canCrud = isManagerOrAdmin(currentUser);
		String url = req.getRequestURI();

		if (url.contains("/video/detail")) {
			String id = req.getParameter("id");
			if (id != null && !id.trim().isEmpty()) {
				videoService.increaseViews(id);
				Video video = videoService.findById(id);
				if (video != null) {
					req.setAttribute("video", video);
					req.setAttribute("youtubeEmbedUrl", toYoutubeEmbedUrl(video.getVideoUrl()));
					req.getRequestDispatcher("/views/video-detail.jsp").forward(req, resp);
					return;
				}
			}
			resp.sendRedirect(req.getContextPath() + "/video");
		} else if (url.contains("/video/add")) {
			if (!canCrud) {
				resp.sendRedirect(req.getContextPath() + "/video");
				return;
			}
			req.setAttribute("nextVideoId", videoService.generateNextVideoId());
			req.setAttribute("listcate", cateService.findAll());
			req.setAttribute("formMode", "add");
			req.getRequestDispatcher("/views/video-form.jsp").forward(req, resp);
		} else if (url.contains("/video/edit")) {
			if (!canCrud) {
				resp.sendRedirect(req.getContextPath() + "/video");
				return;
			}
			Video video = videoService.findById(req.getParameter("id"));
			if (video != null && canModify(currentUser, video)) {
				req.setAttribute("video", video);
				req.setAttribute("listcate", cateService.findAll());
				req.setAttribute("formMode", "edit");
				req.getRequestDispatcher("/views/video-form.jsp").forward(req, resp);
				return;
			}
			resp.sendRedirect(req.getContextPath() + "/video");
		} else if (url.contains("/video/delete")) {
			if (!canCrud) {
				resp.sendRedirect(req.getContextPath() + "/video");
				return;
			}
			Video video = videoService.findById(req.getParameter("id"));
			if (video != null && canModify(currentUser, video)) {
				videoService.delete(video.getVideoId());
			}
			resp.sendRedirect(req.getContextPath() + "/video");
		} else {
			int page = 1;
			String p = req.getParameter("page");
			if (p != null) {
				try {
					page = Math.max(1, Integer.parseInt(p));
				} catch (NumberFormatException ignored) {
				}
			}
			long totalItems = canCrud
					? videoService.countByUserId(currentUser.getId())
					: videoService.countAllActive();
			int totalPages = (int) Math.max(1, Math.ceil((double) totalItems / PAGE_SIZE));
			if (page > totalPages) {
				page = totalPages;
			}
			List<Video> list = canCrud
					? videoService.findByUserId(currentUser.getId(), page, PAGE_SIZE)
					: videoService.findAllActive(page, PAGE_SIZE);

			req.setAttribute("listvideo", list);
			req.setAttribute("currentPage", page);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("totalItems", totalItems);
			req.setAttribute("canCrud", canCrud);
			req.getRequestDispatcher("/views/video-list.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("account") == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		User currentUser = (User) session.getAttribute("account");
		if (!isManagerOrAdmin(currentUser)) {
			resp.sendRedirect(req.getContextPath() + "/video");
			return;
		}

		String url = req.getRequestURI();

		if (url.contains("/video/insert")) {
			Video video = new Video();
			String nextId = videoService.generateNextVideoId();
			video.setVideoId(nextId);
			video.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			video.setViews(0);
			fillFields(req, video);

			vn.iotstar.entity.User owner = new vn.iotstar.entity.User();
			owner.setId(currentUser.getId());
			video.setUser(owner);

			String posterSaved = UploadUtil.savePart(req.getPart("poster1"), req.getParameter("poster"));
			String videoSaved = UploadUtil.savePart(req.getPart("videoFile"), req.getParameter("videoUrl"));
			video.setPoster(posterSaved);
			video.setVideoUrl(videoSaved);

			if (videoSaved == null || videoSaved.trim().isEmpty()) {
				req.setAttribute("error", "Bắt buộc phải nhập Link Video hoặc tải lên File Video!");
				req.setAttribute("nextVideoId", nextId);
				req.setAttribute("video", video);
				req.setAttribute("listcate", cateService.findAll());
				req.setAttribute("formMode", "add");
				req.getRequestDispatcher("/views/video-form.jsp").forward(req, resp);
				return;
			}
			if (posterSaved == null || posterSaved.trim().isEmpty()) {
				req.setAttribute("error", "Bắt buộc phải nhập Link Poster hoặc tải lên File Poster!");
				req.setAttribute("nextVideoId", nextId);
				req.setAttribute("video", video);
				req.setAttribute("listcate", cateService.findAll());
				req.setAttribute("formMode", "add");
				req.getRequestDispatcher("/views/video-form.jsp").forward(req, resp);
				return;
			}

			videoService.insert(video);
			resp.sendRedirect(req.getContextPath() + "/video");
		} else if (url.contains("/video/update")) {
			Video video = videoService.findById(req.getParameter("videoId"));
			if (video != null && canModify(currentUser, video)) {
				String oldPoster = video.getPoster();
				String oldVideoUrl = video.getVideoUrl();
				fillFields(req, video);

				String posterSaved = UploadUtil.savePart(req.getPart("poster1"), req.getParameter("poster"));
				video.setPoster(posterSaved != null ? posterSaved : oldPoster);
				String videoSaved = UploadUtil.savePart(req.getPart("videoFile"), req.getParameter("videoUrl"));
				video.setVideoUrl(videoSaved != null ? videoSaved : oldVideoUrl);

				if (video.getVideoUrl() == null || video.getVideoUrl().trim().isEmpty()
						|| video.getPoster() == null || video.getPoster().trim().isEmpty()) {
					req.setAttribute("error", "Video và Poster không được để trống!");
					req.setAttribute("video", video);
					req.setAttribute("listcate", cateService.findAll());
					req.setAttribute("formMode", "edit");
					req.getRequestDispatcher("/views/video-form.jsp").forward(req, resp);
					return;
				}

				videoService.update(video);
			}
			resp.sendRedirect(req.getContextPath() + "/video");
		}
	}

	private void fillFields(HttpServletRequest req, Video video) {
		video.setTitle(req.getParameter("title"));
		video.setDescription(req.getParameter("description"));
		video.setActive("1".equals(req.getParameter("active")) ? 1 : 0);
		String cid = req.getParameter("categoryId");
		if (cid != null && !cid.isEmpty()) {
			Category c = new Category();
			c.setCategoryid(Integer.parseInt(cid));
			video.setCategory(c);
		}
	}

	private boolean canModify(User user, Video video) {
		if (user.getRoleid() == 1) {
			return true;
		}
		return video.getUser() != null && video.getUser().getId() == user.getId();
	}

	private String toYoutubeEmbedUrl(String url) {
		if (url == null) {
			return null;
		}
		String trimmed = url.trim();
		if (trimmed.contains("youtube.com/watch?v=")) {
			String vid = trimmed.substring(trimmed.indexOf("v=") + 2);
			int amp = vid.indexOf('&');
			if (amp > 0) {
				vid = vid.substring(0, amp);
			}
			return "https://www.youtube.com/embed/" + vid;
		}
		if (trimmed.contains("youtu.be/")) {
			String vid = trimmed.substring(trimmed.indexOf("youtu.be/") + 9);
			int q = vid.indexOf('?');
			if (q > 0) {
				vid = vid.substring(0, q);
			}
			return "https://www.youtube.com/embed/" + vid;
		}
		if (trimmed.contains("youtube.com/embed/")) {
			return trimmed;
		}
		return null;
	}
}
