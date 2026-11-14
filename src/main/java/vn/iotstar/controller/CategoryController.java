package vn.iotstar.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.util.Constant;

@MultipartConfig()
@WebServlet(urlPatterns = { "/admin/categories", "/admin/category/add", "/admin/category/insert",
		"/admin/category/edit", "/admin/category/update", "/admin/category/delete" })
public class CategoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public ICategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		if (url.contains("/admin/categories")) {
			List<Category> list = cateService.findAll();
			req.setAttribute("listcate", list);
			req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
		} else if (url.contains("/admin/category/add")) {
			req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);
		} else if (url.contains("/admin/category/edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Category category = cateService.findById(id);
			req.setAttribute("cate", category);
			req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);
		} else if (url.contains("/admin/category/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			try {
				cateService.delete(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		if (url.contains("/admin/category/insert")) {
			String categoryname = req.getParameter("categoryname");
			int status = Integer.parseInt(req.getParameter("status"));
			String images = req.getParameter("images");

			// Kiểm tra trùng tên
			if (cateService.findByCategoryname(categoryname.trim()) != null) {
				req.setAttribute("error", "Tên sản phẩm \"" + categoryname.trim() + "\" đã tồn tại!");
				req.setAttribute("categoryname", categoryname);
				req.setAttribute("images", images);
				req.setAttribute("status", status);
				req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);
				return;
			}

			Category category = new Category();
			category.setCategoryname(categoryname.trim());
			category.setStatus(status);

			String fname = "";
			String uploadPath = Constant.DIR;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			try {
				Part part = req.getPart("images1");
				if (part != null && part.getSize() > 0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					int index = filename.lastIndexOf(".");
					String ext = (index >= 0) ? filename.substring(index + 1) : "png";
					fname = System.currentTimeMillis() + "." + ext;
					part.write(uploadPath + File.separator + fname);
					category.setImages(fname);
				} else if (images != null && !images.trim().isEmpty()) {
					category.setImages(images.trim());
				} else {
					category.setImages("avatar.png");
				}
			} catch (FileNotFoundException fne) {
				fne.printStackTrace();
			}

			cateService.insert(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}

		if (url.contains("/admin/category/update")) {
			int categoryid = Integer.parseInt(req.getParameter("categoryid"));
			String categoryname = req.getParameter("categoryname");
			int status = Integer.parseInt(req.getParameter("status"));
			String images = req.getParameter("images");

			Category category = cateService.findById(categoryid);
			String fileold = category.getImages();

			// Kiểm tra trùng tên với category khác
			Category same = cateService.findByCategoryname(categoryname.trim());
			if (same != null && same.getCategoryid() != categoryid) {
				req.setAttribute("error", "Tên sản phẩm \"" + categoryname.trim() + "\" đã tồn tại!");
				req.setAttribute("cate", category);
				req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);
				return;
			}

			category.setCategoryname(categoryname.trim());
			category.setStatus(status);

			String fname = "";
			String uploadPath = Constant.DIR;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			try {
				Part part = req.getPart("images1");
				if (part != null && part.getSize() > 0) {
					if (fileold != null && !fileold.startsWith("http") && !fileold.equals("avatar.png")) {
						try {
							deleteFile(uploadPath + File.separator + fileold);
						} catch (Exception ex) {
							// file may not exist
						}
					}
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					int index = filename.lastIndexOf(".");
					String ext = (index >= 0) ? filename.substring(index + 1) : "png";
					fname = System.currentTimeMillis() + "." + ext;
					part.write(uploadPath + File.separator + fname);
					category.setImages(fname);
				} else if (images != null && !images.trim().isEmpty()) {
					category.setImages(images.trim());
				} else {
					category.setImages(fileold);
				}
			} catch (FileNotFoundException fne) {
				fne.printStackTrace();
			}

			cateService.update(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}
	}

	public static void deleteFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			Files.delete(path);
		}
	}
}
