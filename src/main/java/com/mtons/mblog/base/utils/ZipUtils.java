package com.mtons.mblog.base.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * Zip utility
 * @author : landy
 */
@Slf4j
public class ZipUtils {

    /**
     * Unzips the specified zip file to the specified destination directory.
     * Replaces any files in the destination, if they already exist.
     *
     * @param zipFile the name of the zip file to extract
     * @param dest the directory to unzip to
     */
    public static void unzip(Path zipFile, Path dest) throws IOException {
        //if the destination doesn't exist, create it
        if (Files.notExists(dest)) {
            log.info("{} does not exist. Creating...", dest);
            Files.createDirectories(dest);
        }

        try (FileSystem zipFileSystem = createZipFS(zipFile, false)) {
            final Path root = zipFileSystem.getPath("/");

            //walk the zip file tree and copy files to the destination
            Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    final Path destFile = Paths.get(dest.toString(), file.toString());
                    log.info("Extracting file {} to {}", file, destFile);
                    Files.copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    final Path dirToCreate = Paths.get(dest.toString(), dir.toString());
                    if (Files.notExists(dirToCreate)) {
                        log.info("Creating directory {}", dirToCreate);
                        Files.createDirectory(dirToCreate);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    public static void unzip(String zipFilename, String targetDirName) throws IOException {
        unzip(Paths.get(zipFilename), Paths.get(targetDirName));
    }

    /**
     * Creates/updates a zip file.
     *
     * @param zipFile the name of the zip to create
     * @param files   list of filename to add to the zip
     */
    public static void zip(Path zipFile, Path... files) throws IOException {
        final Path root = zipFile.getParent();
        try (FileSystem zipFileSystem = createZipFS(zipFile, true)) {
            //iterate over the files we need to add
            for (Path src : files) {
                //add a file to the zip file system
                if (!Files.isDirectory(src)) {
                    final Path dest = zipFileSystem.getPath(removeRootPath(root.toString(), src.toString()));
                    Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    //for directories, walk the file tree
                    Files.walkFileTree(src, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            final Path dest = zipFileSystem.getPath(removeRootPath(root.toString(), file.toString()));
                            Files.copy(file, dest, StandardCopyOption.REPLACE_EXISTING);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            final Path dirToCreate = zipFileSystem.getPath(removeRootPath(root.toString(), dir.toString()));
                            if (Files.notExists(dirToCreate)) {
                                System.out.printf("Creating directory %s\n", dirToCreate);
                                Files.createDirectories(dirToCreate);
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    });
                }
            }
        }
    }

    public static void zip(String zipFilename, String... filenames) throws IOException {
        Path[] paths = new Path[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            paths[i] = Paths.get(filenames[i]);
        }
        zip(Paths.get(zipFilename), paths);
    }

    /**
     * Returns a zip file system
     *
     * @param path   to construct the file system from
     * @param create true if the zip file should be created
     * @return a zip file system
     */
    private static FileSystem createZipFS(Path path, Boolean create) throws IOException {
        final Map<String, String> env = new HashMap<>();
        env.put("create", create.toString());
        env.put("encoding", "UTF-8");

        StringBuilder sb = new StringBuilder();
        sb.append("jar:file:/");
        if (File.separator.equals("/")) {
            sb.append("/");
        }
        sb.append(path.toString().replaceAll("\\\\", "/"));
        return FileSystems.newFileSystem(URI.create(sb.toString()), env);
    }

    private static String removeRootPath(String root, String path) {
        boolean rootIsFile = !Files.isDirectory(Paths.get(root));
        if (rootIsFile && path.equals(root)) {
            return path.replace(Paths.get(path).getParent().toString(), "");
        } else {
            return path.replace(root, "");
        }
    }

    public static void main(String[] args) throws IOException {
        zip("F:/dockers/admin.zip", "F:/dockers/admin");
//        unzip("F:/dockers/simple.zip", "F:/dockers/simple");
    }
}
