package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.User;
import edu.epam.swp.model.service.UserService;
import edu.epam.swp.model.service.impl.UserServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ChangeAvatarCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeAvatarCommand.class);
    private static final String EXTENSION_SEPARATOR = ".";
    private static final String SLASH = "/";
    private static final int FILE_SIZE_THRESHOLD = 1024 * 1024;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 5 * 5;
    private static final String UPLOAD_PATH = "C:/storage";
    private static final String TEMP_DIR = "javax.servlet.context.tempdir";
    private static final UserService service = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        boolean flag;
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long id = user.getId();
        try {
            List<FileItem> fileItems = parseRequest(request);
            String avatar = handleFileItems(fileItems);
            flag = service.changeAvatar(avatar,id);
            user.setAvatar(avatar);
        } catch (Exception e) {
            logger.error("Error occurred while updating the avatar",e);
            request.getSession().setAttribute(AttributeName.GENERAL_ERROR_MESSAGE,true);
            return PagePath.SERVLET_HOME;
        }
        return PagePath.SERVLET_PROFILE + "&id=" + user.getId();
    }

    private String saveImage(FileItem fileItem) throws Exception {
        String itemName = fileItem.getName();
        String uploadName = generateName(itemName);
        String appName = UPLOAD_PATH + SLASH + uploadName;
        File uploadedFile = new File(appName);
        fileItem.write(uploadedFile);
        return appName;
    }

    private String generateName(String uploadName) {
        UUID uuid = UUID.randomUUID();
        String name = uuid.toString();
        int index = uploadName.lastIndexOf(EXTENSION_SEPARATOR);
        String extension = uploadName.substring(index);
        return name + extension;
    }

    private List<FileItem> parseRequest(HttpServletRequest request) throws FileUploadException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(FILE_SIZE_THRESHOLD);
        ServletContext servletContext = request.getServletContext();
        File repository = (File) servletContext.getAttribute(TEMP_DIR);
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        List<FileItem> fileItems = upload.parseRequest(request);
        return fileItems;
    }

    private String handleFileItems(List<FileItem> fileItems) throws Exception {
        Iterator<FileItem> iterator = fileItems.iterator();
        String picture = null;
        if (iterator.hasNext()) {
            FileItem item = iterator.next();
            picture = saveImage(item);
        }
        return picture;
    }
}