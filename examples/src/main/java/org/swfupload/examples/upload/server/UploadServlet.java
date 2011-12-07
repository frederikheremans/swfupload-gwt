package org.swfupload.examples.upload.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {

  private long FILE_SIZE_LIMIT = 20 * 1024 * 1024; // 20 MiB
  private final Logger logger = Logger.getLogger("UploadServlet");

  @SuppressWarnings("unchecked")
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {

    try {
      ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
      fileUpload.setSizeMax(FILE_SIZE_LIMIT);

      List<FileItem> items = fileUpload.parseRequest(req);

      for (FileItem item : items) {
        if (item.isFormField()) {
          logger.log(Level.INFO, "Received form field:");
          logger.log(Level.INFO, "Name: " + item.getFieldName());
          logger.log(Level.INFO, "Value: " + item.getString());
        } else {
          logger.log(Level.INFO, "Received file:");
          logger.log(Level.INFO, "Name: " + item.getName());
          logger.log(Level.INFO, "Size: " + item.getSize());
        }

        if (!item.isFormField()) {
          if (item.getSize() > FILE_SIZE_LIMIT) {
            resp.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE,
                    "File size excedes limit");

            return;
          }

          // Typically here you would process the file in some way:
          // InputStream in = item.getInputStream();
          // ...

          if (!item.isInMemory())
            item.delete();
        }
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE,
              "Throwing servlet exception for unhandled exception", e);
      throw new ServletException(e);
    }
  }

}
