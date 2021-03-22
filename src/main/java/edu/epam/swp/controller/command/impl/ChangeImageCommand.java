package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
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

/**
 * Command to change image.
 * @author romab
 */
public class ChangeImageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ChangeImageCommand.class);
    private static final CreatureService service = CreatureServiceImpl.getInstance();
    private static final String EXTENSION_SEPARATOR = ".";
    private static final String SLASH = "/";
    private static final int FILE_SIZE_THRESHOLD = 1024 * 1024;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 5 * 5;
    private static final String CONTEXT_UPLOAD_PATH = "uploadPath";
    private static final String TEMP_DIR = "javax.servlet.context.tempdir";

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter(ParameterName.ID));
        boolean flag;
        try {
            List<FileItem> fileItems = parseRequest(request);
            String image = handleFileItems(fileItems,request);
            flag = service.changeImage(id,image);
            if (flag) {
                request.getSession().setAttribute(AttributeName.IMAGE_CHANGE_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.IMAGE_CHANGE_ERROR,true);
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating the image",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return PagePath.SERVLET_ADMIN_PAGE;
    }

    /**
     * Saves the image in the folder specified in UPLOAD_PATH variable.
     * @param fileItem object containing the image.
     * @param request HttpServletRequest object.
     * @return String containing the name of the image.
     * @throws Exception
     */
    private String saveImage(FileItem fileItem,HttpServletRequest request) throws Exception {
        String itemName = fileItem.getName();
        String uploadName = generateName(itemName);
        ServletContext context = request.getServletContext();
        String uploadPath = context.getInitParameter(CONTEXT_UPLOAD_PATH);
        String appName = uploadPath + SLASH + uploadName;
        File uploadedFile = new File(appName);
        fileItem.write(uploadedFile);
        return appName;
    }

    /**
     * Generates random UUID name for image.
     * @param uploadName String containing name of image with extension.
     * @return String containing generated name with extension.
     */
    private String generateName(String uploadName) {
        UUID uuid = UUID.randomUUID();
        String name = uuid.toString();
        int index = uploadName.lastIndexOf(EXTENSION_SEPARATOR);
        String extension = uploadName.substring(index);
        return name + extension;
    }

    /**
     * Parse request into list of fileItems.
     * @param request HttpServletRequest object.
     * @return List of FileItem.
     * @throws FileUploadException
     */
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

    /**
     * Finds image and calls saveImage method.
     * @param fileItems List of FileItem.
     * @param request HttpServletRequest object.
     * @return String containing generated name of image with extension.
     * @throws Exception
     */
    private String handleFileItems(List<FileItem> fileItems, HttpServletRequest request) throws Exception {
        Iterator<FileItem> iterator = fileItems.iterator();
        String picture = null;
        if (iterator.hasNext()) {
            FileItem item = iterator.next();
            picture = saveImage(item,request);
        }
        return picture;
    }
}
