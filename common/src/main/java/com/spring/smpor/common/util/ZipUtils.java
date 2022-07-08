package com.spring.smpor.common.util;

import jcifs.CIFSContext;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import lombok.extern.slf4j.Slf4j;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Slf4j
public class ZipUtils {
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * zip解压
     *
     * @param srcFile     zip源文件
     * @param destDirPath 解压后的目标文件夹
     * @throws RuntimeException 解压失败会抛出运行时异常
     */
    public static void unZipSmbFile(SmbFile srcFile, String destDirPath, CIFSContext context) throws RuntimeException, SmbException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");
        }
        // 开始解压
        ZipInputStream zip = null;
        try {
            SmbFileInputStream in = new SmbFileInputStream(srcFile);
            zip = new ZipInputStream(in, Charset.forName("gbk"));//支持中文文件名
            ZipEntry entry = null;
            while ((entry = zip.getNextEntry()) != null) {
                System.out.println("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    SmbFile dir = new SmbFile(dirPath, context);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    SmbFile targetFile = new SmbFile(destDirPath + "/" + entry.getName(), context);
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    OutputStream os = new BufferedOutputStream(new SmbFileOutputStream(targetFile));

                    int read = 0;
                    byte[] buffer = new byte[1024];
                    while ((read = zip.read(buffer)) != -1) {
                        os.write(buffer, 0, read);
                    }
                    os.flush();
                    os.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zip != null) {
                try {
                    zip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解压文件到指定目录
     */
    @SuppressWarnings({"rawtypes", "resource"})
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
        log.info("文件:{}. 解压路径:{}. 解压开始.", zipPath, descDir);
        long start = System.currentTimeMillis();
        try {
            File zipFile = new File(zipPath);
            System.err.println(zipFile.getName());
            if (!zipFile.exists()) {
                throw new IOException("需解压文件不存在.");
            }
            File pathFile = new File(descDir);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                System.err.println(zipEntryName);
                InputStream in = zip.getInputStream(entry);
                String outPath = (descDir + File.separator + zipEntryName).replaceAll("\\*", "/");
                System.err.println(outPath);
                // 判断路径是否存在,不存在则创建文件路径
                int separatorIndex = outPath.lastIndexOf('/');
                if (separatorIndex < 0)
                    separatorIndex = outPath.lastIndexOf('\\');
                File file = new File(outPath.substring(0, separatorIndex));
                if (!file.exists()) {
                    file.mkdirs();
                }
                // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                // 输出文件路径信息
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
            log.info("文件:{}. 解压路径:{}. 解压完成. 耗时:{}ms. ", zipPath, descDir, System.currentTimeMillis() - start);
        } catch (Exception e) {
            log.info("文件:{}. 解压路径:{}. 解压异常:{}. 耗时:{}ms. ", zipPath, descDir, e, System.currentTimeMillis() - start);
            throw new IOException(e);
        }
    }

    /**
     * 压缩成ZIP 方法
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out      压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles, OutputStream out) throws Exception {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解压rar
     * @param rarPath 需要解压缩的文件
     * @param descDir
     * @return
     */
    /**
     * @param file 文件全路径
     * @param extractPath 解压后文件存放路径 - 支持压缩格式：7z, zip, tar, rar, lzma, iso, gzip, bzip2,
     *                    cpio, z, arj, lzh, cab, chm, nsis, deb, rpm, udf, wim
     */
    public static void extract(String file, String extractPath) throws SevenZipException, IOException {
        IInArchive inArchive = null;
        RandomAccessFile randomAccessFile = null;

        try{
            randomAccessFile = new RandomAccessFile(new File(file), "r");
            inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
            inArchive.extract(null, false, new ExtractCallback(inArchive, extractPath));
        } finally {
            if(inArchive != null){
                inArchive.close();
            }
            if(randomAccessFile != null){
                randomAccessFile.close();
            }
        }
    }

    private static class ExtractCallback implements IArchiveExtractCallback {
        private final IInArchive inArchive;

        private final String extractPath;

        public ExtractCallback(IInArchive inArchive, String extractPath) {
            this.inArchive = inArchive;
            if (!extractPath.endsWith("/") && !extractPath.endsWith("\\")) {
                extractPath += File.separator;
            }
            this.extractPath = extractPath;
        }

        @Override
        public void setTotal(long total) {

        }

        @Override
        public void setCompleted(long complete) {

        }

        @Override
        public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) throws SevenZipException {
            return data -> {
                String filePath = inArchive.getStringProperty(index, PropID.PATH);
                FileOutputStream fos = null;
                try {
                    File path = new File(extractPath + filePath);

                    if(!path.getParentFile().exists()){
                        path.getParentFile().mkdirs();
                    }

                    if(!path.exists()){
                        path.createNewFile();
                    }
                    fos = new FileOutputStream(path, true);
                    fos.write(data);
                } catch (IOException e) {
                    log.error( "IOException while extracting " + filePath);
                } finally{
                    try {
                        if(fos != null){
                            fos.flush();
                            fos.close();
                        }
                    } catch (IOException e) {
                        log.error("Could not close FileOutputStream", e);
                    }
                }
                return data.length;
            };
        }

        @Override
        public void prepareOperation(ExtractAskMode extractAskMode) {

        }

        @Override
        public void setOperationResult(ExtractOperationResult extractOperationResult) {
        }

    }
}
