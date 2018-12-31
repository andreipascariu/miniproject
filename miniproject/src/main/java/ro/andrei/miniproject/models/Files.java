package ro.andrei.miniproject.models;

public class Files {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public Files(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

}