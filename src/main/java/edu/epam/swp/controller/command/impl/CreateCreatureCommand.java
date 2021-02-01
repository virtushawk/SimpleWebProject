package edu.epam.swp.controller.command.impl;

import edu.epam.swp.controller.PagePath;
import edu.epam.swp.controller.ParameterName;
import edu.epam.swp.controller.command.Command;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.service.impl.CreatureServiceImpl;
import org.apache.commons.fileupload.FileItem;
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

public class CreateCreatureCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateCreatureCommand.class);
    private static final String EXTENSION_SEPARATOR = ".";
    private static final int FILE_SIZE_THRESHOLD = 1024 * 1024;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 5 * 5;
    private static final String UPLOAD_PATH = "C:/storage";
    private static final String TEMP_DIR = "javax.servlet.context.tempdir";
    private static final CreatureService service = CreatureServiceImpl.getInstance();

    //todo : fix and clean
    @Override
    public String execute(HttpServletRequest request) {
        boolean flag;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(FILE_SIZE_THRESHOLD);
        ServletContext servletContext = request.getServletContext();
        File repository = (File) servletContext.getAttribute(TEMP_DIR);
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        try {
            List<FileItem> fileItem = upload.parseRequest(request);
            Iterator<FileItem> iter = fileItem.iterator();
            String picture = null;
            String name = null;
            String description = null;
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equals(ParameterName.NAME)) {
                        name = item.getString();
                    } else {
                        description = item.getString();
                    }
                } else {
                    picture = saveImage(item,request);
                }
            }
            long currentTime = System.currentTimeMillis();
            Date lastUpdated = new Date(currentTime);
            logger.info("username {},descrption {},picture {}",name,description,picture);
            Creature creature = new Creature(name,picture,description,lastUpdated);
            flag = service.createCreature(creature);
        } catch (Exception e) {
            logger.error(e);
        }
        return PagePath.SERVLET_CATALOG;
    }

    private String saveImage(FileItem fileItem,HttpServletRequest request) throws Exception {
        String itemName = fileItem.getName();
        String uploadName = generateName(itemName);
        String appName = UPLOAD_PATH + '/' +uploadName;
        File uploadedFile = new File(appName);
        fileItem.write(uploadedFile);
        logger.info("{} FILE WAS UPLOADED",itemName);
        return appName;
    }

    private String generateName(String uploadName) {
        UUID uuid = UUID.randomUUID();
        String name = uuid.toString();
        int index = uploadName.lastIndexOf(EXTENSION_SEPARATOR);
        String extension = uploadName.substring(index);
        return name + extension;
    }
}
