package vn.iotstar.controller;

import java.io.IOException;
import java.sql.Timestamp;

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
@WebServlet(urlPatterns = { "/admin/videos", "/admin/video/add", "/admin/video/insert", "/admin/video/edit",
		"/admin/video/update", "/admin/video/delete" })
public class VideoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public IVideoService videoService = new VideoServiceImpl();
	public ICategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		if (url.contains("/admin/videos")) {
			req.setAttribute("listvideo", videoService.findAll());
			req.getRequestDispatcher("/views/admin/video-list.jsp").forward(req, resp);
		} else if (url.contains("/admin/video/add")) {
			req.setAttribute("nextVideoId", videoService.generateNextVideoId());
			req.setAttribute("listcate", cateService.findAll());
			req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
		} else if (url.contains("/admin/video/edit")) {
			Video video = videoService.findById(req.getParameter("id"));
			req.setAttribute("video", video);
			req.setAttribute("listcate", cateService.findAll());
			req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);
		} else if (url.contains("/admin/video/delete")) {
			videoService.delete(req.getParameter("id"));
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		if (url.contains("/admin/video/insert")) {
			String videoId = videoService.generateNextVideoId();
			Video video = new Video();
			video.setVideoId(videoId);
			video.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			fillFromForm(req, video);

			HttpSession session = req.getSession(false);
			if (session != null && session.getAttribute("account") != null) {
				User acc = (User) session.getAttribute("account");
				vn.iotstar.entity.User owner = new vn.iotstar.entity.User();
				owner.setId(acc.getId());
				video.setUser(owner);
			}

			String posterSaved = UploadUtil.savePart(req.getPart("poster1"), req.getParameter("poster"));
			video.setPoster(posterSaved);
			String videoSaved = UploadUtil.savePart(req.getPart("videoFile"), req.getParameter("videoUrl"));
			video.setVideoUrl(videoSaved);

			if (videoSaved == null || videoSaved.trim().isEmpty()) {
				req.setAttribute("error", "Bắt buộc phải nhập Link Video hoặc tải lên File Video!");
				req.setAttribute("nextVideoId", videoId);
				req.setAttribute("video", video);
				req.setAttribute("listcate", cateService.findAll());
				req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
				return;
			}
			if (posterSaved == null || posterSaved.trim().isEmpty()) {
				req.setAttribute("error", "Bắt buộc phải nhập Link Poster hoặc tải lên File Poster!");
				req.setAttribute("nextVideoId", videoId);
				req.setAttribute("video", video);
				req.setAttribute("listcate", cateService.findAll());
				req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
				return;
			}

			if (!videoService.insert(video)) {
				req.setAttribute("error", "Không thể khởi tạo mã video mới, vui lòng thử lại!");
				req.setAttribute("nextVideoId", videoService.generateNextVideoId());
				req.setAttribute("video", video);
				req.setAttribute("listcate", cateService.findAll());
				req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
				return;
			}
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		} else if (url.contains("/admin/video/update")) {
			Video video = videoService.findById(req.getParameter("videoId"));
			if (video != null) {
				String oldPoster = video.getPoster();
				String oldVideoUrl = video.getVideoUrl();
				fillFromForm(req, video);

				String posterSaved = UploadUtil.savePart(req.getPart("poster1"), req.getParameter("poster"));
				video.setPoster(posterSaved != null ? posterSaved : oldPoster);
				String videoSaved = UploadUtil.savePart(req.getPart("videoFile"), req.getParameter("videoUrl"));
				video.setVideoUrl(videoSaved != null ? videoSaved : oldVideoUrl);

				videoService.update(video);
			}
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}
	}

	private void fillFromForm(HttpServletRequest req, Video video) {
		video.setTitle(req.getParameter("title"));
		video.setDescription(req.getParameter("description"));
		String views = req.getParameter("views");
		video.setViews((views == null || views.trim().isEmpty()) ? 0 : Integer.parseInt(views.trim()));
		video.setActive("1".equals(req.getParameter("active")) ? 1 : 0);
		String cid = req.getParameter("categoryId");
		if (cid != null && !cid.isEmpty()) {
			Category c = new Category();
			c.setCategoryid(Integer.parseInt(cid));
			video.setCategory(c);
		} else {
			video.setCategory(null);
		}
	}
}
