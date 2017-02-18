package com.jiedaibao.demo.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过Java的Zip输入输出流实现压缩和解压文件
 * Created by jinzg on 2016年12月9日.
 */
public final class ZipUtils {

    private static final Logger log        = LoggerFactory.getLogger(ZipUtils.class);

    private static final String ZIP_SUFFIX = ".zip";

    /**
     * 压缩文件
     * @param filePath 待压缩的文件路径
     * @return 压缩后的文件
     */
    public static File zip(String filePath) {
        File target = null;
        File source = new File(filePath);
        if (!source.exists()) {
            log.error("{} is invalid", filePath);
            return null;
        }
        // 压缩文件名=源文件名.zip
        String zipName = source.getName() + ZIP_SUFFIX;
        target = new File(source.getParent(), zipName);
        if (target.exists()) {
            target.delete(); // 删除旧的文件
        }
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(target);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            // 添加对应的文件Entry
            addEntry(StringUtils.EMPTY, source, zos);
        } catch (IOException e) {
            log.error("压缩文件异常,异常信息:{}", e);
            return null;
        } finally {
            closeQuietly(zos, fos);
        }
        return target;
    }

    /**
     * 扫描添加文件Entry
     * @param base  基路径
     * @param source  源文件
     * @param zos Zip文件输出流
     * @throws IOException
     */
    private static void addEntry(String base, File source, ZipOutputStream zos) throws IOException {
        // 按目录分级，形如：/aaa/bbb.txt
        String entry = base + source.getName();
        if (source.isDirectory()) {
            for (File file : source.listFiles()) {
                // 递归列出目录下的所有文件，添加文件Entry
                addEntry(entry + File.separator, file, zos);
            }
        } else {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                byte[] buffer = new byte[1024 * 10];
                fis = new FileInputStream(source);
                bis = new BufferedInputStream(fis, buffer.length);
                int read = 0;
                zos.putNextEntry(new ZipEntry(entry));
                while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
            } finally {
                closeQuietly(bis, fis);
            }
        }
    }

    /**
     * 关闭一个或多个流对象
     * @param closeables 可关闭的流对象列表
     */
    public static void closeQuietly(Closeable... closeables) {
        try {
            if (closeables != null) {
                for (Closeable closeable : closeables) {
                    if (closeable != null) {
                        closeable.close();
                    }
                }
            }
        } catch (IOException e) {
            log.error("关闭流异常,异常信息:{}", e);
        }
    }

    /**
     * 解压文件
     * @param filePath 压缩文件路径
     */
    public static void unzip(String filePath) {
        File source = new File(filePath);
        if (!source.exists()) {
            log.error("{} is invalid", filePath);
            return;
        }
        ZipInputStream zis = null;
        BufferedOutputStream bos = null;
        try {
            zis = new ZipInputStream(new FileInputStream(source));
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
                File target = new File(source.getParent(), entry.getName());
                if (!target.getParentFile().exists()) {
                    // 创建文件父目录
                    target.getParentFile().mkdirs();
                }
                // 写入文件
                bos = new BufferedOutputStream(new FileOutputStream(target));
                int read = 0;
                byte[] buffer = new byte[1024 * 10];
                while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, read);
                }
                bos.flush();
            }
            zis.closeEntry();
        } catch (IOException e) {
            log.error("解压文件异常,异常信息:{}", e);
            return;
        } finally {
            closeQuietly(zis, bos);
        }
    }

    /**
     * 解压文件
     * @param filePath 压缩文件路径
     * @param targetPath  目标目录
     */
    public static void unzip(String filePath, String targetPath) {
        File source = new File(filePath);
        if (!source.exists()) {
            log.error("{} is invalid", filePath);
            return;
        }
        ZipInputStream zis = null;
        BufferedOutputStream bos = null;
        try {
            zis = new ZipInputStream(new FileInputStream(source));
            ZipEntry entry = null;
            File f = new File(targetPath);
            if (!f.exists()) {
                f.mkdirs();
            } else {
                clearFiles(targetPath);
                f.mkdirs();
            }
            while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
                File target = new File(f, entry.getName());
                if (!target.getParentFile().exists()) {
                    // 创建文件父目录
                    target.getParentFile().mkdirs();
                }
                // 写入文件
                bos = new BufferedOutputStream(new FileOutputStream(target));
                int read = 0;
                byte[] buffer = new byte[1024 * 10];
                while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, read);
                }
                bos.flush();
            }
            zis.closeEntry();
        } catch (IOException e) {
            log.error("解压文件异常,异常信息:{}", e);
            return;
        } finally {
            closeQuietly(zis, bos);
        }
    }

    /**
     * 清空文件
     * @param path 文件路径
     */
    private static void clearFiles(String path) {
        File file = new File(path);
        if (file.exists()) {
            deleteFile(file);
        }
    }

    /**
     * 删除文件
     * @param file 文件
     */
    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }

}