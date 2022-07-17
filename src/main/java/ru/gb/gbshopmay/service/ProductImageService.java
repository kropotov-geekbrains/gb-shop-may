package ru.gb.gbshopmay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.gbapimay.product.dto.ProductDto;
import ru.gb.gbshopmay.dao.ProductDao;
import ru.gb.gbshopmay.dao.ProductImageDao;
import ru.gb.gbshopmay.entity.Product;
import ru.gb.gbshopmay.entity.ProductImage;
import ru.gb.gbshopmay.exception.StorageException;
import ru.gb.gbshopmay.exception.StorageFileNotFoundException;
import ru.gb.gbshopmay.web.dto.mapper.ProductMapper;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Artem Kropotov
 * created at 26.06.2022
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageService {


//    private List<String> paths; // пути внутри storageLocation

    private static final String path = "products";

    @Value("${storage.location}")
    private String storagePath;

    private final ProductImageDao productImageDao;
    private final ProductDao productDao;
    private final ProductMapper productMapper;


    private Path rootLocation;

    @PostConstruct
    public void init() {
        rootLocation = Paths.get(storagePath);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            log.error("Error while creating storage {}", rootLocation.toAbsolutePath());
            throw new StorageException(String.format("Error while creating storage %s", rootLocation.toAbsolutePath()));
        }
    }

    public String save(MultipartFile file) {
        String filename = UUID.randomUUID() + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        return this.save(file, filename);
    }

    public String save(MultipartFile file, String filename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException(String.format("File %s is empty", filename));
            }
            if (filename.contains("..")) {
                throw new StorageException(String.format("Symbol '..' do not permit"));
            }
            Files.createDirectories(rootLocation.resolve(path));
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(rootLocation.resolve(path))) {
                for (Path child : dirStream) {
                    if (child.getFileName().toString().equals(filename)) {
                        throw new StorageException(String.format("File with name %s/%s already exists", rootLocation.resolve(path), filename));
                    }
                }
            } catch (IOException e) {
                throw new StorageException(String.format("Error while creating file %s", filename));
            }

        } catch (IOException e) {
            throw new StorageException("Error while creating storage");
        }
        try(InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.rootLocation.resolve(path).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException(String.format("Error while saving file %s", filename));
        }

        return filename;
    }

    public ProductDto saveProductImage(Long productId, MultipartFile multipartFile) {
        Product product = productDao.getById(productId);
        String pathToSavedFile = save(multipartFile);
        ProductImage productImage = ProductImage.builder()
                .path(pathToSavedFile)
                .product(product)
                .build();
        product.addImage(productImage);
        return productMapper.toProductDto(productDao.save(product));
    }


    public BufferedImage loadFileAsImage(Long id) throws IOException {
        String imageName = uploadMultipleFilesByProductId(id);
        Resource resource = loadAsResource(imageName);
        return ImageIO.read(resource.getFile());
    }

    public BufferedImage loadFileAsImageByIdImage(Long id) throws IOException {
        String imageName = uploadMultipleFilesByImageId(id);
        Resource resource = loadAsResource(imageName);
        return ImageIO.read(resource.getFile());
    }

    public String uploadMultipleFilesByProductId(Long id) {
        return productImageDao.findImageNameByProductId(id);
    }

    public String uploadMultipleFilesByImageId(Long id) {
        return productImageDao.findImageNameByImageId(id);
    }

    public List<Long> uploadMultipleFiles(Long id) {
        return productImageDao.findAllIdImagesByProductId(id);
    }


    public Resource loadAsResource(String filename) {

        if (StringUtils.hasText(filename)) {
            try {
                Path file = rootLocation.resolve(path).resolve(filename);
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new StorageFileNotFoundException(String.format("File %s not found in directory %s", filename, path));
                }
            } catch (MalformedURLException e) {
                throw new StorageFileNotFoundException(String.format("File %s not found in directory %s", filename, path), e);
            }
        } else {
            throw new StorageFileNotFoundException(String.format("Filename cannot be empty: %s", filename));
        }
    }


}
