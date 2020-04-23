package com.demo.model;


import javax.persistence.*;

@Table(name = "data_export_task_info")
public class DataExportTaskInfo {
    /**
     * 任务ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private String updateTime;

    /**
     * 解密标识
     */
    @Column(name = "encry_flag")
    private String encryFlag;

    /**
     * 查询条件（JSON存储）
     */
    @Column(name = "query_info")
    private String queryInfo;

    /**
     * 任务状态
     */
    @Column(name = "task_stat")
    private String taskStat;

    /**
     * 任务描述
     */
    @Column(name = "task_desc")
    private String taskDesc;

    @Column(name = "exec_info")
    private String execInfo;

    /**
     * 下载地址
     */
    @Column(name = "download_url")
    private String downloadUrl;

    /**
     * 文件地址
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 文件格式（以”|”分割文件所需字段）
     */
    @Column(name = "file_format")
    private String fileFormat;

    /**
     * 压缩包密码
     */
    @Column(name = "zip_word")
    private String zipWord;

   
    /**
     * 操作员
     */
    @Column(name = "oper_id")
    private String operId;

    /**
     * 保留域1
     */
    private String resv1;

    /**
     * 保留域2
     */
    private String resv2;

    /**
     * 保留域3
     */
    private String resv3;

    /**
     * 获取任务ID
     *
     * @return id - 任务ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置任务ID
     *
     * @param id 任务ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取解密标识
     *
     * @return encry_flag - 解密标识
     */
    public String getEncryFlag() {
        return encryFlag;
    }

    /**
     * 设置解密标识
     *
     * @param encryFlag 解密标识
     */
    public void setEncryFlag(String encryFlag) {
        this.encryFlag = encryFlag;
    }

    /**
     * 获取查询条件（JSON存储）
     *
     * @return query_info - 查询条件（JSON存储）
     */
    public String getQueryInfo() {
        return queryInfo;
    }

    /**
     * 设置查询条件（JSON存储）
     *
     * @param queryInfo 查询条件（JSON存储）
     */
    public void setQueryInfo(String queryInfo) {
        this.queryInfo = queryInfo;
    }

    /**
     * 获取任务状态
     *
     * @return task_stat - 任务状态
     */
    public String getTaskStat() {
        return taskStat;
    }

    /**
     * 设置任务状态
     *
     * @param taskStat 任务状态
     */
    public void setTaskStat(String taskStat) {
        this.taskStat = taskStat;
    }

    /**
     * 获取任务描述
     *
     * @return task_desc - 任务描述
     */
    public String getTaskDesc() {
        return taskDesc;
    }

    /**
     * 设置任务描述
     *
     * @param taskDesc 任务描述
     */
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     * @return exec_info
     */
    public String getExecInfo() {
        return execInfo;
    }

    /**
     * @param execInfo
     */
    public void setExecInfo(String execInfo) {
        this.execInfo = execInfo;
    }

    /**
     * 获取下载地址
     *
     * @return download_url - 下载地址
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * 设置下载地址
     *
     * @param downloadUrl 下载地址
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    /**
     * 获取文件地址
     *
     * @return file_path - 文件地址
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件地址
     *
     * @param filePath 文件地址
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取文件格式（以”|”分割文件所需字段）
     *
     * @return file_format - 文件格式（以”|”分割文件所需字段）
     */
    public String getFileFormat() {
        return fileFormat;
    }

    /**
     * 设置文件格式（以”|”分割文件所需字段）
     *
     * @param fileFormat 文件格式（以”|”分割文件所需字段）
     */
    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    /**
     * 获取压缩包密码
     *
     * @return zip_word - 压缩包密码
     */
    public String getZipWord() {
        return zipWord;
    }

    /**
     * 设置压缩包密码
     *
     * @param zipWord 压缩包密码
     */
    public void setZipWord(String zipWord) {
        this.zipWord = zipWord;
    }

    
    

    /**
     * 获取操作员
     *
     * @return oper_id - 操作员
     */
    public String getOperId() {
        return operId;
    }

    /**
     * 设置操作员
     *
     * @param operId 操作员
     */
    public void setOperId(String operId) {
        this.operId = operId;
    }

    /**
     * 获取保留域1
     *
     * @return resv1 - 保留域1
     */
    public String getResv1() {
        return resv1;
    }

    /**
     * 设置保留域1
     *
     * @param resv1 保留域1
     */
    public void setResv1(String resv1) {
        this.resv1 = resv1;
    }

    /**
     * 获取保留域2
     *
     * @return resv2 - 保留域2
     */
    public String getResv2() {
        return resv2;
    }

    /**
     * 设置保留域2
     *
     * @param resv2 保留域2
     */
    public void setResv2(String resv2) {
        this.resv2 = resv2;
    }

    /**
     * 获取保留域3
     *
     * @return resv3 - 保留域3
     */
    public String getResv3() {
        return resv3;
    }

    /**
     * 设置保留域3
     *
     * @param resv3 保留域3
     */
    public void setResv3(String resv3) {
        this.resv3 = resv3;
    }
}