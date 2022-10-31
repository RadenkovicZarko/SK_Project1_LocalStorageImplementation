import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter

public class LocalStorageImplementation extends StorageSpecification {
  private Map<String, FileMetadata> map = new HashMap<>();
  private String storageName = "Skladiste";

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
    local.createFolderOnSpecifiedPath("cvele", "cvele2");
    local.moveFileFromDirectoryToAnother("dest\\2.txt", "cvele\\cvele2");
    local.moveFileFromDirectoryToAnother("dest\\1.txt", "cvele\\cvele2");
    //System.out.println(local.folderNameByFileName("2.txt"));
//    System.out.println("from dir: ");
//    local.filesFromDirectory("cvele");
//    System.out.println("from child dir: ");
//    local.filesFromChildrenDirectory("cvele");
//    for (String name : local.getMap().keySet()) {
//      System.out.println(name);
//    }
    //local.downloadFileOrDirectory("cvele\\2.txt", "C:\\Users\\cvlad\\Downloads");
    //local.renameFileOrDirectory("cvele\\\\2.txt", "novifajl.txt");
//    here = new ArrayList<>();
//    here.add("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation\\p1\\3.txt");
//    here.add("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation\\p1\\4.txt");
//    local.createFolderOnSpecifiedPath("cvele\\cvele2", "cvele3");
//    local.putFilesOnSpecifiedPath(here, "cvele\\cvele2\\cvele3");
//    local.allFilesFromDirectoryAndSubdirectory("");

//    List<String> here2 = new ArrayList<>();
//    here2.add("1.txt");
//    here2.add("2.txt");
//    System.out.println(local.doesDirectoryContainFiles("cvele\\cvele2", here2));
  }

  private String getFullStoragePath(String path) {
    return super.getRootFolderPath() + "\\\\" + this.storageName + (path.length() > 0 ? "\\\\" + path : "");
  }

  private void createConfigurationFile(String fullPath) {
    String destPath = fullPath + "\\\\" + "configuration.txt";
    File dest = new File(destPath);
    try {
      FileWriter myWriter = new FileWriter(dest.getAbsolutePath());
      myWriter.write(super.getConfiguration().toString());
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred while creating a configuration file.");
    }
  }

  @Override
  void createRootFolder() {
    //super.setRootFolderPath("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation");
    File rootFile = new File(super.getRootFolderPath() + "\\\\" + this.storageName);
    boolean hasConfiguration = false;
    if (super.getRootFolderPath().length() > 0) {
      File[] files = Objects.requireNonNull(new File(super.getRootFolderPath()).listFiles());
      for (File f : files) {
        if (f.getName().equalsIgnoreCase(this.storageName)) {
          hasConfiguration = true;
          break;
        }
      }
    }
    System.out.println("Has configuration " + hasConfiguration);
    boolean created = rootFile.mkdir();
    if (hasConfiguration) {
      System.out.println("Root folder already exists.");
    } else if (created) {
      System.out.println("Root folder successfully created.");
      this.createConfigurationFile(rootFile.getAbsolutePath());
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
      System.out.println("Error during creation of directory: " + name + ".");
    }
    return false;
  }

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
  boolean downloadFileOrDirectory(String pathFrom, String pathTo) {
    String fullPathFrom = this.getFullStoragePath(pathFrom);
    return this.uploadFileToPath(fullPathFrom, pathTo);
  }

  @Override
  void renameFileOrDirectory(String path, String nameAfter) {
    String fullPath = this.getFullStoragePath(path);
    File oldFile = new File(fullPath);
    if (!oldFile.exists()) {
      System.out.println(path + " does not exist.");
      return;
    }
    String newPath = oldFile.getParent() + "\\\\" + nameAfter;
    File newFile = new File(newPath);
    if (oldFile.renameTo(newFile)) {
      System.out.println("File renamed successfully.");
    } else {
      System.out.println("Error while renaming a file.");
    }
  }

  private FileTime returnCreationTime(File f) {
    try {
      BasicFileAttributes attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
      return attr.creationTime();
    } catch (IOException ex) {
      System.out.println("Error occurred during file search.");
      return null;
    }
  }

  private FileTime returnModificationTime(File f) {
    try {
      BasicFileAttributes attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
      return attr.lastModifiedTime();
    } catch (IOException ex) {
      System.out.println("Error occurred during file search.");
      return null;
    }
  }

  private void addFilesFromDirectoryToMap(String fullPath) {
    File sourceFolder = new File(fullPath);
    if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
      System.out.println(fullPath + " is not a directory.");
      return;
    }
    File[] files = Objects.requireNonNull(new File(fullPath).listFiles());
    for (File f : files) {
      if (f.isDirectory()) continue;
      FileTime creationTime = returnCreationTime(f);
      FileTime modificationTime = returnModificationTime(f);
      if (creationTime == null || modificationTime == null) continue;
      this.map.put(f.getName(), new FileMetadata(f.getAbsolutePath(), f.getTotalSpace(), new Date(creationTime.toMillis()),
              new Date(modificationTime.toMillis()), FilenameUtils.getExtension(f.getName()), f.getName()));
    }
  }

  private boolean initialFileDirectoryError(String fullPath) {
    this.map.clear();
    File sourceFolder = new File(fullPath);
    if (!sourceFolder.exists()) {
      System.out.println(fullPath + " does not a exist.");
      return true;
    }
    if (!sourceFolder.isDirectory()) {
      System.out.println(fullPath + " is not a directory.");
      return true;
    }
    return false;
  }
  @Override
  Map<String, FileMetadata> filesFromDirectory(String path) {
    String fullPath = this.getFullStoragePath(path);
    if (this.initialFileDirectoryError(fullPath)) return null;
    this.addFilesFromDirectoryToMap(fullPath);
    return this.map;
  }

  @Override
  Map<String, FileMetadata> filesFromChildrenDirectory(String path) {
    String fullPath = this.getFullStoragePath(path);
    if (this.initialFileDirectoryError(fullPath)) return null;
    File[] files = Objects.requireNonNull(new File(fullPath).listFiles());
    for (File f : files) {
      if (!f.isDirectory()) continue;
      this.addFilesFromDirectoryToMap(f.getAbsolutePath());
    }
    return this.map;
  }

  private void allFilesDfs(String fullPath) {
    File[] files = Objects.requireNonNull(new File(fullPath).listFiles());
    this.addFilesFromDirectoryToMap(fullPath);
    for (File f : files) {
      if (f.isDirectory()) {
        this.allFilesDfs(f.getAbsolutePath());
      }
    }
  }

  @Override
  Map<String, FileMetadata> allFilesFromDirectoryAndSubdirectory(String path) {
    String fullPath = this.getFullStoragePath(path);
    if (this.initialFileDirectoryError(fullPath)) return null;
    this.allFilesDfs(fullPath);
    for (String name : this.map.keySet()) {
      System.out.println(name);
    }
    return this.map;
  }

  //endsWith = 1: Filtering: ends with word
  //endsWith = 0: Filtering: contains word
  private Map<String, FileMetadata> createNewFilteredMap(List <String> filtersWords, boolean endsWith) {
    Map<String, FileMetadata> newMap = new HashMap<>();
    for (Map.Entry<String, FileMetadata> it : this.map.entrySet()) {
      boolean found = false;
      for (String w : filtersWords) {
        if (endsWith) {
          if (it.getKey().endsWith(w)) {
            found = true;
            break;
          }
        } else {
          if (it.getKey().contains(w)) {
            found = true;
            break;
          }
        }
      }
      if (found) newMap.put(it.getKey(), it.getValue());
    }
    return newMap;
  }
  @Override
  Map<String, FileMetadata> filesFromDirectoryExt(String path, List<String> extensions) {
    this.filesFromDirectory(path);
    return this.map = this.createNewFilteredMap(extensions, true);
  }

  @Override
  Map<String, FileMetadata> filesFromChildrenDirectoryExt(String path, List<String> extensions) {
    this.filesFromChildrenDirectory(path);
    return this.map = this.createNewFilteredMap(extensions, true);
  }

  @Override
  Map<String, FileMetadata> allFilesFromDirectoryAndSubdirectoryExt(String path, List<String> extensions) {
    this.allFilesFromDirectoryAndSubdirectory(path);
    return this.map = this.createNewFilteredMap(extensions, true);
  }

  @Override
  Map<String, FileMetadata> filesFromDirectorySubstring(String path, String substring) {
    this.filesFromDirectory(path);
    List<String> list = new ArrayList<>();
    list.add(substring);
    return this.map = this.createNewFilteredMap(list, false);
  }

  @Override
  Map<String, FileMetadata> filesFromChildrenDirectorySubstring(String path, String substring) {
    this.filesFromChildrenDirectory(path);
    List<String> list = new ArrayList<>();
    list.add(substring);
    return this.map = this.createNewFilteredMap(list, false);
  }

  @Override
  Map<String, FileMetadata> filesFromDirectoryAndSubdirectorySubstring(String path, String substring) {
    this.allFilesFromDirectoryAndSubdirectory(path);
    List<String> list = new ArrayList<>();
    list.add(substring);
    return this.map = this.createNewFilteredMap(list, false);
  }

  // Vraca null ukoliko nije dobar path, vraca "Ne postoji takvi fajlovi u ovom folderu",
  // vraca "Postoje fajlovi: test.txt image.jpg"
  @Override
  String doesDirectoryContainFiles(String pathToFolder, List<String> namesOfFiles) {
    String fullPath = this.getFullStoragePath(pathToFolder);
    File sourceFolder = new File(fullPath);
    if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
      System.out.println(fullPath + " is not a directory.");
      return null;
    }
    File[] files = Objects.requireNonNull(new File(fullPath).listFiles());
    String ans = "";
    List<String> list =
            Arrays.stream(files)
            .filter(File::isFile)
            .map(File::getName)
            .collect(Collectors.toList());
    if (list.size() > 0) {
      ans += "Found files are:";
      ans += list.toString();
    } else {
      ans = "No files exist";
    }
    return ans;
  }

  private String searchForFile(String path, String fileName) {
    File sourceFolder = new File(path);
    if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
      return null;
    }
    File[] files = Objects.requireNonNull(new File(path).listFiles());
    for (File f : files) {
      if (f.isDirectory()) {
        String result = searchForFile(f.getAbsolutePath(), fileName);
        if (result != null) {
          return result;
        }
      } else {
        if (f.getName().equalsIgnoreCase(fileName)) {
          return sourceFolder.getName();
        }
      }
    }
    return null;
  }

  @Override
  String folderNameByFileName(String fileName) {
    return this.searchForFile(super.getRootFolderPath(), fileName);
  }

  private Map<String, FileMetadata> returnFilesInDateInterval(String fullPath, Date fromDate, Date toDate, boolean created) {
    Map<String, FileMetadata> ans = new HashMap<>();
    File sourceFolder = new File(fullPath);
    if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
      System.out.println(fullPath + " is not a directory.");
      return null;
    }
    File[] files = Objects.requireNonNull(new File(fullPath).listFiles());
    for (File f : files) {
      if (f.isDirectory()) continue;
      FileTime creationTime = returnCreationTime(f);
      FileTime modificationTime = returnModificationTime(f);
      if (creationTime == null || modificationTime == null) continue;
      Date newCreationDate = new Date(creationTime.toMillis());
      Date newModificationDate = new Date(modificationTime.toMillis());
      if (created) {
        if (newCreationDate.after(fromDate) && newCreationDate.before(toDate)) {
          ans.put(f.getName(), new FileMetadata(f.getAbsolutePath(), f.getTotalSpace(), newCreationDate,
                  newModificationDate, FilenameUtils.getExtension(f.getName()), f.getName()));
        }
      } else {
        if (newModificationDate.after(fromDate) && newModificationDate.before(toDate)) {
          ans.put(f.getName(), new FileMetadata(f.getAbsolutePath(), f.getTotalSpace(), newCreationDate,
                  newModificationDate, FilenameUtils.getExtension(f.getName()), f.getName()));
        }
      }
    }
    return ans;
  }

  @Override
  Map<String, FileMetadata> returnCreatedFilesInDateInterval(String pathToDirectory, Date fromDate, Date toDate) {
    return this.returnFilesInDateInterval(pathToDirectory, fromDate, toDate, true);
  }

  @Override
  Map<String, FileMetadata> returnModifiedFilesInDateInterval(String pathToDirectory, Date fromDate, Date toDate) {
    return this.returnFilesInDateInterval(pathToDirectory, fromDate, toDate, false);
  }
}
