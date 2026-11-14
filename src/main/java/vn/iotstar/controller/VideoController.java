package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Video;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IVideoService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.VideoServiceImpl;
import vn.iotstar.util.Constant;

@MultipartConfig()
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
			fillFromForm(req, video);
			video.setPoster(resolvePoster(req, null));

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
				fillFromForm(req, video);
				video.setPoster(resolvePoster(req, video.getPoster()));
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

	private String resolvePoster(HttpServletRequest req, String old) throws IOException, ServletException {
		String posterLink = req.getParameter("poster");
		Part part = req.getPart("poster1");
		if (part != null && part.getSize() > 0) {
			File dir = new File(Constant.DIR);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
			int index = filename.lastIndexOf(".");
			String ext = index >= 0 ? filename.substring(index + 1) : "png";
			String fname = System.currentTimeMillis() + "." + ext;
			part.write(Constant.DIR + File.separator + fname);
			return fname;
		}
		if (posterLink != null && !posterLink.trim().isEmpty()) {
			return posterLink.trim();
		}
		return old;
	}
}
