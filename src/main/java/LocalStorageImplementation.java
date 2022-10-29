import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter

public class LocalStorageImplementation extends StorageSpecification {
  public static void main(String[] args) {
    LocalStorageImplementation local = new LocalStorageImplementation();
    local.createRootFolder();
    local.createFolderOnSpecifiedPath("", "cvele");
    List<String> here = new ArrayList<>();
    here.add("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation\\p1\\1.txt");
    here.add("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation\\p1\\2.txt");
    local.createFolderOnSpecifiedPath("", "dest");
    local.putFilesOnSpecifiedPath(here, "dest");
    //local.deleteFileOrDirectory("cvele");
    local.moveFileFromDirectoryToAnother("dest\\2.txt", "cvele");
    //local.downloadFileOrDirectory("cvele\\2.txt", "C:\\Users\\cvlad\\Downloads");
  }

  private String getFullStoragePath(String path) {
    return super.getRootFolderPath() + (path.length() > 0 ? "\\\\" + path : "");
  }

  @Override
  void createRootFolder() {
//    File rootFile = new File(super.getRootFolderPath());
    File rootFile = new File("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation\\proba");
    boolean created = rootFile.mkdir();
    if (created) {
      System.out.println("Root folder successfully created.");
      super.setRootFolderPath(rootFile.getAbsolutePath());
    } else {
      System.out.println("Error during creation root file.");
    }
  }

  @Override
  boolean setRootFolderPathInitialization(String s) {
    File file = new File(s);
    if (file.exists() && file.isDirectory()) {
      super.setRootFolderPath(s);
      return true;
    }
    return false;
  }

  @Override
  boolean createFolderOnSpecifiedPath(String path, String name) {
    String fullPath = this.getFullStoragePath(path) + "\\\\" + name;
    File file = new File(fullPath);
    boolean created = file.mkdir();
    if (created) {
      System.out.println("Directory \"" + name + "\" successfully created.");
    } else {
      System.out.println("Error during creation of directory.");
    }
    return false;
  }

  // Uploads a file "fileName" to a "path"
  private boolean uploadFileToPath(String fileName, String path) {
    File srcDir = new File(path);
    if (!srcDir.exists() || !srcDir.isDirectory()) {
      System.out.println("Directory \"" + path + "\" doesn't exist.");
      return false;
    }
    File source = new File(fileName);
    if (!source.exists()) return false;
    String destPath = path + "\\\\" + source.getName();
    File dest = new File(destPath);
    try {
      FileUtils.copyFile(source, dest);
    } catch (IOException e) {
      System.out.println("Error while uploading files.");
    }
    return true;
  }

  @Override
  boolean putFilesOnSpecifiedPath(List<String> listFiles, String path) {
    for (String filePath : listFiles) {
      this.uploadFileToPath(filePath, this.getFullStoragePath(path));
    }
    return true;
  }

  @Override
  void deleteFileOrDirectory(String path) {
    String fullPath = this.getFullStoragePath(path);
    File file = new File(fullPath);
    if (file.delete()) {
      System.out.println("File \"" + file.getName() + "\" successfully deleted.");
    } else {
      System.out.println("Failed to delete the file.");
    }
  }

  @Override
  boolean moveFileFromDirectoryToAnother(String filePath, String pathTo) {
    String fullFilePath = this.getFullStoragePath(filePath);
    String fullPathTo = this.getFullStoragePath(pathTo);

    File file = new File(fullFilePath);
    if (!file.isFile()) {
      System.out.println(fullFilePath + " is not a file type.");
      return false;
    }

    File fileTo = new File(fullPathTo);
    if (!fileTo.exists()) {
      System.out.println("Destination " + fullPathTo + " doesn't exist.");
      return false;
    }
    if (!fileTo.isDirectory()) {
      System.out.println("Not a directory destination.");
      return false;
    }

    fullPathTo += "\\\\" + file.getName();
    fileTo = new File(fullPathTo);

    if (file.renameTo(fileTo)) {
      System.out.println("File moved successfully.");
      return true;
    } else {
      System.out.println("Error while moving a file.");
      return false;
    }
  }

  @Override
  void downloadFileOrDirectory(String pathFrom, String pathTo) {
    String fullPathFrom = this.getFullStoragePath(pathFrom);
    this.uploadFileToPath(fullPathFrom, pathTo);
  }

  @Override
  void renameFileOrDirectory(String pathFrom, String pathTo) {

  }

  @Override
  Map<String, FileMetadata> filesFromDirectory(String s) {
    return null;
  }

  @Override
  Map<String, FileMetadata> filesFromChildrenDirectory(String s) {
    return null;
  }

  @Override
  Map<String, FileMetadata> allFilesFromDirectoryAndSubdirectory(String s) {
    return null;
  }

  @Override
  Map<String, FileMetadata> filesFromDirectoryExt(String s, List<String> list) {
    return null;
  }

  @Override
  Map<String, FileMetadata> filesFromChildrenDirectoryExt(String s, List<String> list) {
    return null;
  }

  @Override
  Map<String, FileMetadata> allFilesFromDirectoryAndSubdirectoryExt(String s, List<String> list) {
    return null;
  }

  @Override
  Map<String, FileMetadata> filesFromDirectorySubstring(String s, String s1) {
    return null;
  }

  @Override
  Map<String, FileMetadata> filesFromChildrenDirectorySubstring(String s, String s1) {
    return null;
  }

  @Override
  Map<String, FileMetadata> filesFromDirectoryAndSubdirectorySubstring(String s, String s1) {
    return null;
  }

  @Override
  String doesDiretoryContainsFiles(String s, List<String> list) {
    return null;
  }

  @Override
  String folderNameByFileName(String s) {
    return null;
  }

  @Override
  Map<String, FileMetadata> sortFilesByName(Map<String, FileMetadata> hashMap, boolean b) {
    return null;
  }

  @Override
  Map<String, FileMetadata> sortFilesByCreatedDate(Map<String, FileMetadata> hashMap, boolean b) {
    return null;
  }

  @Override
  Map<String, FileMetadata> sortFilesBySize(Map<String, FileMetadata> hashMap, boolean b) {
    return null;
  }

  @Override
  Map<String, FileMetadata> returnCreatedFilesInDateInterval(String s, Date date, Date date1) {
    return null;
  }

  @Override
  Map<String, FileMetadata> returnModifiedFilesInDateInterval(String s, Date date, Date date1) {
    return null;
  }
}
