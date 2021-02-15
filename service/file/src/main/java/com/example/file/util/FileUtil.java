package com.example.file.util;

import com.example.exception.FileException;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author gong_da_kai
 * @Date 2021/2/4 13:27
 * @version 1.0.0
 * @since 1.0.0
 */

public class FileUtil {

    /**
     * 初始化 fastdfs.conf 文件
     */
    static {
        try {

            //从配置文件中初始化所有的tracker地址信息
            ClassPathResource classPathResource = new ClassPathResource("fastdfs.conf");
            ClientGlobal.init(classPathResource.getPath());
            System.err.println(classPathResource.getPath());
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }

    //分组
    private String groupName;
    //文件在storage上的路径
    private String remotePath;
    //大小
    private Long fileSize;
    //旧名称
    private String oldFileName;
    //后缀名
    private String extName;
    //文件的byte[]
    private byte[] fileBytes;
    //文件的url(完整地址)
    private String url;

    private TrackerClient tc;
    private TrackerServer ts;
    private StorageServer ss;
    private StorageClient sc;

    //构造方法

    /**
     * 无参构造, 创建链接, 获得操作客户端的对象
     * @throws FileException 无参构造获取客户端失败
     */
    public FileUtil() throws FileException {
        //可以通过 tc.getFetchStorage(group, remotePath) 获得 group 的信息
        tc = new TrackerClient();

        //可通过 ts 获得 tracker 的信息
        try {
            ts = tc.getTrackerServer();
            //可以通过 ss 获得关于 storage 的信息
            ss = tc.getStoreStorage(ts);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }

        if (ts == null || ss == null) {
            throw new FileException("操作文件失败");
        }

        //获得 StorageClient 对象,用这个对象进行对文件的操作
        sc = new StorageClient(ts, ss);
    }

    /**
     * 删除 时使用
     * @param groupName 组名
     * @param remotePath 文件在fastdfs所在计算机的路径
     * @throws FileException 无参构造获取客户端失败
     */
    public FileUtil(String groupName, String remotePath) throws FileException {
        this();
        this.groupName = groupName;
        this.remotePath = remotePath;
    }

    /**
     * 下载 时使用的构造方法, 传入多个参数
     * @param groupName 组名
     * @param remotePath 文件在fastdfs所在计算机的路径
     * @param fileSize 大小, 用来显示进度条
     * @param oldFileName 下载时恢复文件旧有名称
     * @throws FileException 无参构造获取客户端失败
     */
    public FileUtil(String groupName, String remotePath, Long fileSize, String oldFileName) throws FileException {
        this();
        this.groupName = groupName;
        this.remotePath = remotePath;
        this.fileSize = fileSize;
        this.oldFileName = oldFileName;

        System.err.println("groupName:" + groupName);
        System.err.println("remotePath:" + remotePath);
        System.err.println("fileSize:" + fileSize);
        System.err.println("oldFileName:" + oldFileName);
    }

    //setter/getter

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getRemotePath() {
        return remotePath;
    }
    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }
    public Long getFileSize() {
        return fileSize;
    }
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    public String getOldFileName() {
        return oldFileName;
    }
    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }
    public String getExtName() {
        return extName;
    }
    public void setExtName(String extName) {
        this.extName = extName;
    }
    public byte[] getFileBytes() {
        return fileBytes;
    }
    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public TrackerClient getTc() {
        return tc;
    }
    public void setTc(TrackerClient tc) {
        this.tc = tc;
    }
    public TrackerServer getTs() {
        return ts;
    }
    public void setTs(TrackerServer ts) {
        this.ts = ts;
    }
    public StorageServer getSs() {
        return ss;
    }
    public void setSs(StorageServer ss) {
        this.ss = ss;
    }
    public StorageClient getSc() {
        return sc;
    }
    public void setSc(StorageClient sc) {
        this.sc = sc;
    }

    //操作

    /**
     * 上传文件, 传入文件封装类, 为以下成员属性赋值
     * fileBytes
     * oldFileName
     * extName
     * groupName
     * remotePath
     * fileBytes
     * url
     */
    public void upload(MultipartFile file) throws FileException {

        try {
            fileBytes = file.getBytes();
            oldFileName = file.getOriginalFilename();
            extName = getExtName(oldFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (sc != null) {
            try {

                String[] msg = sc.upload_file(fileBytes, extName, null);
                if (msg == null) {
                    throw new FileException("上传文件失败");
                }

                groupName = msg[0];
                remotePath = msg[1];
                url = getAddress();
                //System.out.println(msg[0] + "/" + msg[1]);

            } catch (IOException | MyException e) {
                e.printStackTrace();
            }  /*finally {
                this.close();
            }*/
        }

    }

    /**
     * 将 下载文件的结果 封装到ResponseEntity<byte[]>中, 并返回
     *
     * @return 封装好的 ResponseEntity<byte[]>
     */
    public ResponseEntity<byte[]> getResponse() throws FileException {

        byte[] body = download();

        HttpHeaders h = getHeader();

        return new ResponseEntity<>(body, h, HttpStatus.OK);
    }
    /**
     * 下载文件, 根据此对象的 groupName, remotePath
     * @return 文件流 byte[]
     */
    public byte[] download() throws FileException {

        //download 只有返回值为0时,表示成功
        /*String group = "group1";
        String remotePath = "M00/00/00/wKhIgV_M0B2EcAFkAAAAAIyfFq8783.txt";
        String localPath = "G:\\java\\fdfs_download.txt";*/

        byte[] body = null;

        if (sc != null) {
            try {

                body = sc.download_file(groupName, remotePath);

            } catch (IOException | MyException e) {

                e.printStackTrace();
                throw new FileException("文件下载失败");

            }  /*finally {
                this.close();
            }*/
        }

        if (body == null) {
            throw new FileException("此文件不存在");
        }

        return body;

    }
    /**
     * 将此对象的 fileSize,oldFileName 封装到 HttpHeader 中
     * @return HttpHeaders
     */
    private HttpHeaders getHeader() {

        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        if (fileSize != null) {
            h.setContentLength(fileSize);
        }

        if (oldFileName != null) {
            h.setContentDispositionFormData("attachment", oldFileName);
        }

        return h;
    }

    /**
     * 删除文件, 根据此对象的 groupName, remotePath
     * @throws FileException 返回结果不为 0 (删除失败)时抛出异常
     */
    public void delete() throws FileException {

        //download 只有返回值为0时,表示成功
        /*String group = "group1";
        String remotePath = "M00/00/00/wKhIgV_M0B2EcAFkAAAAAIyfFq8783.txt";*/

        Integer res = null;

        if (sc != null) {
            try {

                res = sc.delete_file(groupName, remotePath);


            } catch (IOException | MyException e) {

                e.printStackTrace();
                throw new FileException("删除文件失败");

            } /*finally {
                this.close();
            }*/
        }

        if (res != 0) {
            throw new FileException("删除文件失败");
        }
    }

    //辅助功能

    /**
     * 获得文件的url
     *
     * @return url
     * @throws IOException 异常
     */
    public String getAddress() {

        String ip = ts.getInetSocketAddress().getHostString();
        int port = ClientGlobal.getG_tracker_http_port();

        return "http://" + ip + ":" + port + "/" + groupName + "/" + remotePath;
    }

    /**
     * 获得后缀名
     *
     * @param oldFileName 文件名
     * @return 后缀名
     */
    public static String getExtName(String oldFileName) {

        if (oldFileName != null) {
            return oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
        }

        return null;
    }

    /**
     * 关闭 StorageClient
     */
    public void close() {
        if (sc != null) {
            try {
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}