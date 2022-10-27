import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
//    local.deleteFileOrDirectory("cvele");
    local.moveFileFromDirectoryToAnother("dest\\2.txt", "cvele");
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

  @Override
  boolean putFilesOnSpecifiedPath(List<String> listFiles, String path) {
    File srcDir = new File(this.getFullStoragePath(path));
    if (!srcDir.exists()) {
      System.out.println("Directory \"" + path + "\" doesn't exist.");
      return false;
    }
    for (String filePath : listFiles) {
      File source = new File(filePath);
      if (!source.exists()) continue;
      String fullPath = this.getFullStoragePath(path) + "\\\\" + source.getName();
      File dest = new File(fullPath);
      try {
        FileUtils.copyFile(source, dest);
      } catch (IOException e) {
        System.out.println("Error while copying files.");
      }
    }
    return false;
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

    System.out.println(super.getRootFolderPath());
    System.out.println(fullFilePath);
    System.out.println(fullPathTo);

    File file = new File(fullFilePath);
    System.out.println(file.getName());
    if (!file.isFile()) {
      System.out.println("Not a file type.");
      return false;
    }
    if (file.renameTo(new File(fullPathTo))) {
      if (file.delete()) {
        System.out.println("File moved successfully.");
        return true;
      } else {
        System.out.println("Error while moving a file.");
        return false;
      }
    } else {
      System.out.println("Error while moving a file.");
      return false;
    }
  }

  @Override
  void downloadFileOrDirectory(String s, String s1) {

  }

  @Override
  void renameFileOrDirectory(String s, String s1) {

  }

  @Override
  HashMap<String, FileMetadata> filesFromDirectory(String s) {
    return null;
  }

  @Override
  HashMap<String, FileMetadata> filesFromChildrenDirectory(String s) {
    return null;
  }

  @Override
  HashMap<String, FileMetadata> allFilesFromDirectoryAndSubdirectory(String s) {
    return null;
  }

  @Override
  HashMap<String, String> filesFromDirectoryExt(String s, List<String> list) {
    return null;
  }

  @Override
  HashMap<String, String> filesFromChildrenDirectoryExt(String s, List<String> list) {
    return null;
  }

  @Override
  HashMap<String, String> allFilesFromDirectoryAndSubdirectoryExt(String s, List<String> list) {
    return null;
  }

  @Override
  HashMap<String, String> filesFromDirectorySubstring(String s, String s1) {
    return null;
  }

  @Override
  String folderNameByFileName(String s) {
    return null;
  }

  @Override
  List<String> returnFilesInDateInterval(String s, Date date, Date date1) {
    return null;
  }
}
