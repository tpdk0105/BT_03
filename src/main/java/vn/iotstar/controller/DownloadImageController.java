package vn.iotstar.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.util.Constant;

@WebServlet(urlPatterns = { "/image", "/media" })
public class DownloadImageController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("fname");
		if (fileName == null || fileName.isEmpty()) {
			return;
		}

		File file = new File(Constant.DIR, fileName);
		if (!file.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String mimeType = getServletContext().getMimeType(fileName);
		if (mimeType == null) {
			String lower = fileName.toLowerCase();
			if (lower.endsWith(".mp4")) {
				mimeType = "video/mp4";
			} else if (lower.endsWith(".webm")) {
				mimeType = "video/webm";
			} else {
				mimeType = "image/jpeg";
			}
		}
		resp.setContentType(mimeType);
		resp.setContentLengthLong(file.length());

		try (FileInputStream in = new FileInputStream(file);
			 OutputStream out = resp.getOutputStream()) {
			byte[] buffer = new byte[8192];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		}
	}
}
