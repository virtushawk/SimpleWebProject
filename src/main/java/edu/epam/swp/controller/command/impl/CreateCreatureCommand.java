package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.AttributeName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.AccountRole;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.entity.CreatureStatus;
import edu.epam.swp.model.entity.User;
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
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Command to create creature.
 * @author romab
 */
public class CreateCreatureCommand implements Command {

    private static final Logger logger = LogManager.getLogger(CreateCreatureCommand.class);
    private static final String EXTENSION_SEPARATOR = ".";
    private static final String SLASH = "/";
    private static final int FILE_SIZE_THRESHOLD = 1024 * 1024;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 5 * 5;
    private static final String UPLOAD_PATH = "C:/storage";
    private static final String TEMP_DIR = "javax.servlet.context.tempdir";
    private static final CreatureService service = CreatureServiceImpl.getInstance();

    /**
     * Executes the command.
     * @param request HttpServletRequest object.
     * @return String containing the path to the page.
     */
    @Override
    public String execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(AttributeName.USER);
        long id = user.getAccountId();
        boolean flag;
        try {
            List<FileItem> fileItems = parseRequest(request);
            Creature creature = handleFileItems(fileItems);
            creature.setAccountId(id);
            if (user.getRole().equals(AccountRole.ADMIN)) {
                creature.setCreatureStatus(CreatureStatus.APPROVED);
            } else {
                creature.setCreatureStatus(CreatureStatus.UNCHECKED);
            }
            flag = service.createCreature(creature);
            if (flag) {
                request.getSession().setAttribute(AttributeName.CREATURE_CREATE_VALID,true);
            } else {
                request.getSession().setAttribute(AttributeName.CREATURE_CREATE_ERROR,true);
            }
        }
        catch (Exception e) {
            logger.error("Error occurred while creating the creature",e);
            request.getSession().setAttribute(AttributeName.DATABASE_ERROR_MESSAGE,true);
        }
        return PagePath.SERVLET_CATALOG;
    }

    /**
     * Saves the image in the folder specified in UPLOAD_PATH variable.
     * @param fileItem object containing the image.
     * @return String containing the name of the image.
     * @throws Exception
     */
    private String saveImage(FileItem fileItem) throws Exception {
        String itemName = fileItem.getName();
        String uploadName = generateName(itemName);
        String appName = UPLOAD_PATH + SLASH + uploadName;
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
     * parse request into list of fileItems.
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
     * creates and returns Creature object from fileItems
     * @param fileItems List of FileItem.
     * @return Creature object
     * @throws Exception
     */
    private Creature handleFileItems(List<FileItem> fileItems) throws Exception {
        Iterator<FileItem> iterator = fileItems.iterator();
        String picture = null;
        String name = null;
        String description = null;
        while (iterator.hasNext()) {
            FileItem item = iterator.next();
            if (item.isFormField()) {
                if (item.getFieldName().equals(ParameterName.NAME)) {
                    name = item.getString();
                } else {
                    description = item.getString();
                }
            } else {
                picture = saveImage(item);
            }
        }
        long currentTime = System.currentTimeMillis();
        Date lastUpdated = new Date(currentTime);
        Creature creature = new Creature.CreatureBuilder().withName(name).withPicture(picture)
                .withDescription(description).withLastUpdated(lastUpdated).build();
        return creature;
    }
}
