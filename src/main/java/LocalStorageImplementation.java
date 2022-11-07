import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.net.MalformedURLException;
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

  static {
    StorageManager.registerStorage(new LocalStorageImplementation());
  }

  public LocalStorageImplementation() {
    super();
  }
  public static void main(String[] args) {
//    LocalStorageImplementation local = new LocalStorageImplementation();
//    local.createRootFolder();
//    local.createFolderOnSpecifiedPath("", "cvele");
//    List<String> here = new ArrayList<>();
//    here.add("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation\\p1\\1.txt");
//    here.add("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation\\p1\\2.txt");
//    local.createFolderOnSpecifiedPath("", "dest");
//    local.putFilesOnSpecifiedPath(here, "dest");
//    //local.deleteFileOrDirectory("cvele");
//    local.createFolderOnSpecifiedPath("cvele", "cvele2");
//    local.moveFileFromDirectoryToAnother("dest\\2.txt", "cvele\\cvele2");
//    local.moveFileFromDirectoryToAnother("dest\\1.txt", "cvele\\cvele2");
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
    StorageSpecification storageSpecification=new LocalStorageImplementation();
    System.out.println(storageSpecification.setRootFolderPathInitialization("C:/Users/mega/Radna površina/Test"));
    storageSpecification.createRootFolder();
    System.out.println(storageSpecification.getRootFolderPath());
//    storageSpecification.createFolderOnSpecifiedPath("Zarko","Zarko123");

  }

  private String getFullStoragePath(String path) {
    return super.getRootFolderPath() + "\\\\" + this.storageName + (path.length() > 0 ? "\\\\" + path : "");
  }

  boolean isThePathOutsideOfStorage(String path)
  {
      if(path.contains(super.getRootFolderPath()))
        return false;
      return true;
  }

  private void createConfigurationFile(String fullPath) throws MyException {
    String destPath = fullPath + "\\\\" + "configuration.txt";
    File dest = new File(destPath);
    try {
      FileWriter myWriter = new FileWriter(dest.getAbsolutePath());
      myWriter.write(super.getConfiguration().toString());
      myWriter.close();
    } catch (IOException e) {
      throw new MyException("An error occurred while creating a configuration file.");
    }
  }

  private void setConfigurationFile(File file) throws IOException {
    BufferedReader bf = new BufferedReader(new FileReader(file));
    String line;
    int lineCounter = 0;
    while ((line = bf.readLine()) != null) {
      lineCounter++;
      if (lineCounter == 1) {
        int size = Integer.parseInt(line);
        super.getConfiguration().setSize(size);
      } else if (lineCounter == 2) {
        String[] extensions = line.split(" ");
        for (String ext : extensions) {
          super.getConfiguration().getForbiddenExtensions().add(ext);
        }
      } else {
        String[] parts = line.split(" ");
        super.getConfiguration().getNumberOfFilesInFolder().put(parts[0], Integer.parseInt(parts[1]));
      }
    }
  }

//  @Override
//  boolean createRootFolder() throws MyException {
//    //super.setRootFolderPath("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation");
//    File rootFile = new File(super.getRootFolderPath() + "\\\\" + this.storageName);
//    System.out.println("UDJE1");
//    boolean hasConfiguration = false;
//    if (super.getRootFolderPath().length() > 0) {
//      System.out.println("UDJE2");
//      File[] files = Objects.requireNonNull(new File(super.getRootFolderPath()).listFiles());
//      for (File f : files) {
//        if (f.getName().equalsIgnoreCase(this.storageName)) {
//          System.out.println("UDJE5");
//          hasConfiguration = true;
//          try {
//            System.out.println(f.getName());
//            this.setConfigurationFile(f);
//          } catch (IOException e) {
//            System.out.println("UDJE6");
//            throw new MyException("Error while reading a root file.");
//          }
//          System.out.println("UDJE4");
//          break;
//        }
//      }
//    }
//    System.out.println("UDJE3");
//
//    boolean created = rootFile.mkdir();
//    if (hasConfiguration) {
//      throw new MyException("Root folder already exists.");
//    } else if (created) {
//      this.createConfigurationFile(rootFile.getAbsolutePath());
//    } else {
//      throw new MyException("Error during creation root file.");
//    }
//    return true;
//  }
@Override
  boolean createRootFolder() throws MyException {  //Mozda bi trebalo da se doda /Skladiste na putanju
    File rootFile = new File(super.getRootFolderPath() + "\\\\" + this.storageName);
    boolean hasConfiguration = false;
    if(rootFile.exists())
    {
      File[] files = Objects.requireNonNull(rootFile.listFiles());
      for (File f : files) {
        if (f.getName().equalsIgnoreCase("configuration.txt")) {
          hasConfiguration = true;
          try {
            this.setConfigurationFile(f);
            return true;
          } catch (IOException e) {

            throw new MyException("Error while reading a root file.");
          }
        }
      }
    }
    boolean created = rootFile.mkdir();
    if (created) {
      this.createConfigurationFile(rootFile.getAbsolutePath());
    } else {
      throw new MyException("Error during creation root file.");
    }
    return true;
  }//Okreni samo linije

  @Override
  boolean setRootFolderPathInitialization(String s) throws MyException{
    File file = new File(s);
    if (file.exists() && file.isDirectory()) {
      super.setRootFolderPath(s);
      return true;
    }
    throw new MyException("Bad path");
//    return false;
  } //TEST OK




  @Override
  boolean createFolderOnSpecifiedPath(String path, String name) throws MyException {
    File checkFile = new File(this.getFullStoragePath(path));
    if (!checkFile.exists()) {
      throw new MyException("Path doesn't exist in storage.");
    }
    if(checkFile.isFile())
    {
      throw new MyException("This is file not folder.");
    }
    String fullPath = this.getFullStoragePath(path) + "\\\\" + name;
    File file = new File(fullPath);
    boolean created = file.mkdir();
    if (created) {
      return true;
    } else {
      throw new MyException("Error during creation of directory: " + name + ".");
    }
  }//TEST OK



  private boolean uploadFileToPath(String fileName, String path, boolean check) throws MyException { //FileName je fajl sa celom putanjom, path je putanja do odredjenog direktorijuma
    File srcDir = new File(path);
    if (!srcDir.exists()) {
      throw new MyException("Directory \"" + path + "\" doesn't exist.");
    }

    File source = new File(fileName);
    if (!source.exists() && !source.isFile()) return false;
    String destPath = path + "\\\\" + source.getName();  //OVO OVDE MOZE DA BUDE PROBLEM
    File dest = new File(destPath);
    if (check) {
      this.checkForUploadFileErrors(srcDir, source);  //
    }
    try {
      FileUtils.copyFile(source, dest);
    } catch (IOException e) {
      throw new MyException("Error while uploading files.");
    }
    return true;
  }

  private String getRelativePath(String path) {
    return path.substring(path.indexOf("Skladiste"));
  }

  private void checkForUploadFileErrors(File source, File file) throws MyException {
    File rootDir = new File(this.getFullStoragePath(""));
    if (!rootDir.exists() || !rootDir.isDirectory()) {
      throw new MyException("Action invalid.");
    }
    if (rootDir.length() + file.length() > super.getConfiguration().getSize()) {
      throw new MyException("File size exceeded the root size.");
    }
    for (String ext : super.getConfiguration().getForbiddenExtensions()) {
      if (ext.equalsIgnoreCase(FilenameUtils.getExtension(file.getName()))) {
        throw new MyException("This extension file is forbidden.");
      }
    }
    String relativePath = getRelativePath(source.getAbsolutePath());
    File relFile = new File(relativePath);
    if (!relFile.exists() || !relFile.isDirectory()) {
      throw new MyException("Action invalid.");
    }
    int fileCount = Objects.requireNonNull(relFile.list()).length;

    if (super.getConfiguration().getNumberOfFilesInFolder().get(relativePath) != null && fileCount + 1 >
            super.getConfiguration().getNumberOfFilesInFolder().get(relativePath)) {
      throw new MyException("Maximum number of files exceeded");
    }
  }

  @Override
  boolean putFilesOnSpecifiedPath(List<String> listFiles, String path) throws MyException {
    StringBuilder sb = new StringBuilder();
    for (String filePath : listFiles) {
      try {
        this.uploadFileToPath(filePath, this.getFullStoragePath(path), true);
      } catch (MyException exc) {
        sb.append(exc);
      }
    }
    if (sb.length() > 0) {
      throw new MyException(sb.toString());
    }
    return true;
  }




  private boolean isPathInStorage(String path) {
    return (path.toLowerCase().contains("skladiste"));
  }

  @Override
  void deleteFileOrDirectory(String path) throws MyException {
    String fullPath = this.getFullStoragePath(path);
    File file = new File(fullPath);
    if (!file.delete()) {
      throw new MyException("Failed to delete the file.");
    }
  }

  @Override
  boolean moveFileFromDirectoryToAnother(String filePath, String pathTo) throws MyException {
    String fullFilePath = this.getFullStoragePath(filePath);
    String fullPathTo = this.getFullStoragePath(pathTo);

    File file = new File(fullFilePath);
    if (!file.isFile()) {
      throw new MyException(fullFilePath + " is not a file type.");
    }

    File fileTo = new File(fullPathTo);
    if (!fileTo.exists()) {
      throw new MyException("Destination " + fullPathTo + " doesn't exist.");
    }
    if (!fileTo.isDirectory()) {
      throw new MyException("Not a directory destination.");
    }

    String relativePath = this.getRelativePath(fileTo.getAbsolutePath());
    int fileCount = Objects.requireNonNull(fileTo.list()).length;
    if (super.getConfiguration().getNumberOfFilesInFolder().get(relativePath) != null && fileCount + 1 >
            super.getConfiguration().getNumberOfFilesInFolder().get(relativePath)) {
      throw new MyException("Maximum number of files exceeded");
    }

    fullPathTo += "\\\\" + file.getName();
    fileTo = new File(fullPathTo);

    if (file.renameTo(fileTo)) {
      return true;
    } else {
      throw new MyException("Error while moving a file.");
    }
  }

  @Override
  boolean downloadFileOrDirectory(String pathFrom, String pathTo) throws MyException {
    if (this.isPathInStorage(pathFrom) && this.isPathInStorage(pathTo)) {
      throw new MyException("Both paths are in storage.");
    }
    String fullPathFrom = this.getFullStoragePath(pathFrom);
    return this.uploadFileToPath(fullPathFrom, pathTo, false);
  }

  @Override
  void renameFileOrDirectory(String path, String nameAfter) throws MyException {
    String fullPath = this.getFullStoragePath(path);
    File oldFile = new File(fullPath);
    if (!oldFile.exists()) {
      throw new MyException(path + " does not exist.");
    }
    String newPath = oldFile.getParent() + "\\\\" + nameAfter;
    File newFile = new File(newPath);
    if (!oldFile.renameTo(newFile)) {
      throw new MyException("Error while renaming a file.");
    }
  }




  private FileTime returnCreationTime(File f) throws MyException {
    try {
      BasicFileAttributes attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
      return attr.creationTime();
    } catch (IOException ex) {
      throw new MyException("Error occurred during file search.");
    }
  }

  private FileTime returnModificationTime(File f) throws MyException {
    try {
      BasicFileAttributes attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
      return attr.lastModifiedTime();
    } catch (IOException ex) {
      throw new MyException("Error occurred during file search.");
    }
  }

  private void addFilesFromDirectoryToMap(String fullPath) throws MyException {
    File sourceFolder = new File(fullPath);
    if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
      throw new MyException(fullPath + " is not a directory.");
    }
    File[] files = Objects.requireNonNull(new File(fullPath).listFiles());
    for (File f : files) {
      if (f.isDirectory()) continue;
      FileTime creationTime = this.returnCreationTime(f);
      FileTime modificationTime = this.returnModificationTime(f);
      if (creationTime == null || modificationTime == null) continue;
      this.map.put(f.getName(), new FileMetadata(f.getAbsolutePath(), f.getTotalSpace(), new Date(creationTime.toMillis()),
              new Date(modificationTime.toMillis()), FilenameUtils.getExtension(f.getName()), f.getName()));
    }
  }

  private boolean initialFileDirectoryError(String fullPath) throws MyException {
    this.map.clear();
    File sourceFolder = new File(fullPath);
    if (!sourceFolder.exists()) {
      throw new MyException(fullPath + " does not a exist.");
    }
    if (!sourceFolder.isDirectory()) {
      throw new MyException(fullPath + " is not a directory.");
    }
    return false;
  }
  @Override
  Map<String, FileMetadata> filesFromDirectory(String path) throws MyException {
    String fullPath = this.getFullStoragePath(path);
    if (this.initialFileDirectoryError(fullPath)) return null;
    this.addFilesFromDirectoryToMap(fullPath);
    return this.map;
  }

  @Override
  Map<String, FileMetadata> filesFromChildrenDirectory(String path) throws MyException {
    String fullPath = this.getFullStoragePath(path);
    if (this.initialFileDirectoryError(fullPath)) return null;
    File[] files = Objects.requireNonNull(new File(fullPath).listFiles());
    for (File f : files) {
      if (!f.isDirectory()) continue;
      this.addFilesFromDirectoryToMap(f.getAbsolutePath());
    }
    return this.map;
  }

  private void allFilesDfs(String fullPath) throws MyException {
    File[] files = Objects.requireNonNull(new File(fullPath).listFiles());
    this.addFilesFromDirectoryToMap(fullPath);
    for (File f : files) {
      if (f.isDirectory()) {
        this.allFilesDfs(f.getAbsolutePath());
      }
    }
  }

  @Override
  Map<String, FileMetadata> allFilesFromDirectoryAndSubdirectory(String path) throws MyException {
    String fullPath = this.getFullStoragePath(path);
    if (this.initialFileDirectoryError(fullPath)) return null;
    this.allFilesDfs(fullPath);
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
  Map<String, FileMetadata> filesFromDirectoryExt(String path, List<String> extensions) throws MyException {
    this.filesFromDirectory(path);
    return this.map = this.createNewFilteredMap(extensions, true);
  }

  @Override
  Map<String, FileMetadata> filesFromChildrenDirectoryExt(String path, List<String> extensions) throws MyException {
    this.filesFromChildrenDirectory(path);
    return this.map = this.createNewFilteredMap(extensions, true);
  }

  @Override
  Map<String, FileMetadata> allFilesFromDirectoryAndSubdirectoryExt(String path, List<String> extensions) throws MyException {
    this.allFilesFromDirectoryAndSubdirectory(path);
    return this.map = this.createNewFilteredMap(extensions, true);
  }

  @Override
  Map<String, FileMetadata> filesFromDirectorySubstring(String path, String substring) throws MyException {
    this.filesFromDirectory(path);
    List<String> list = new ArrayList<>();
    list.add(substring);
    return this.map = this.createNewFilteredMap(list, false);
  }

  @Override
  Map<String, FileMetadata> filesFromChildrenDirectorySubstring(String path, String substring) throws MyException {
    this.filesFromChildrenDirectory(path);
    List<String> list = new ArrayList<>();
    list.add(substring);
    return this.map = this.createNewFilteredMap(list, false);
  }

  @Override
  Map<String, FileMetadata> filesFromDirectoryAndSubdirectorySubstring(String path, String substring) throws MyException {
    this.allFilesFromDirectoryAndSubdirectory(path);
    List<String> list = new ArrayList<>();
    list.add(substring);
    return this.map = this.createNewFilteredMap(list, false);
  }

  // Vraca null ukoliko nije dobar path, vraca "Ne postoji takvi fajlovi u ovom folderu",
  // vraca "Postoje fajlovi: test.txt image.jpg"
  @Override
  String doesDirectoryContainFiles(String pathToFolder, List<String> namesOfFiles) throws MyException {
    String fullPath = this.getFullStoragePath(pathToFolder);
    File sourceFolder = new File(fullPath);
    if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
      throw new MyException(fullPath + " is not a directory.");
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
  String folderNameByFileName(String fileName) throws MyException {
    String result = this.searchForFile(super.getRootFolderPath(), fileName);
    if (result == null) {
      throw new MyException("Folder not found.");
    }
    return result;
  }

  private Map<String, FileMetadata> returnFilesInDateInterval(
          String fullPath, Date fromDate, Date toDate, boolean created, boolean upTo) throws MyException {
    Map<String, FileMetadata> ans = new HashMap<>();
    File sourceFolder = new File(fullPath);
    if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
      throw new MyException(fullPath + " is not a directory.");
    }
    File[] files = Objects.requireNonNull(new File(fullPath).listFiles());
    for (File f : files) {
      if (f.isDirectory()) continue;
      FileTime creationTime = this.returnCreationTime(f);
      FileTime modificationTime = this.returnModificationTime(f);
      if (creationTime == null || modificationTime == null) continue;
      Date newCreationDate = new Date(creationTime.toMillis());
      Date newModificationDate = new Date(modificationTime.toMillis());
      if (created) {
        if (newCreationDate.after(fromDate) && newCreationDate.before(toDate)) {
          ans.put(f.getName(), new FileMetadata(f.getAbsolutePath(), f.getTotalSpace(), newCreationDate,
                  newModificationDate, FilenameUtils.getExtension(f.getName()), f.getName()));
        }
      } else {
        if (upTo) {
          if (fromDate != null) {
            if (newModificationDate.after(fromDate)) {
              ans.put(f.getName(), new FileMetadata(f.getAbsolutePath(), f.getTotalSpace(), newCreationDate,
                      newModificationDate, FilenameUtils.getExtension(f.getName()), f.getName()));
            }
          } else {
            if (newModificationDate.before(toDate)) {
              ans.put(f.getName(), new FileMetadata(f.getAbsolutePath(), f.getTotalSpace(), newCreationDate,
                      newModificationDate, FilenameUtils.getExtension(f.getName()), f.getName()));
            }
          }
        } else {
          if (newModificationDate.after(fromDate) && newModificationDate.before(toDate)) {
            ans.put(f.getName(), new FileMetadata(f.getAbsolutePath(), f.getTotalSpace(), newCreationDate,
                    newModificationDate, FilenameUtils.getExtension(f.getName()), f.getName()));
          }
        }
      }
    }
    return ans;
  }

  @Override
  Map<String, FileMetadata> returnCreatedFilesInDateInterval(
          String pathToDirectory, Date fromDate, Date toDate) throws MyException {
    return this.returnFilesInDateInterval(pathToDirectory, fromDate, toDate, true, false);
  }

  @Override
  Map<String, FileMetadata> returnModifiedFilesInDateInterval(
          String pathToDirectory, Date fromDate, Date toDate) throws MyException {
    return this.returnFilesInDateInterval(pathToDirectory, fromDate, toDate, false, false);
  }

  @Override
  Map<String, FileMetadata> returnModifiedFilesFromDate(String pathToDirectory, Date fromDate) throws MyException {
    return this.returnFilesInDateInterval(pathToDirectory, fromDate, null, false, true);
  }

  @Override
  Map<String, FileMetadata> returnModifiedFilesBeforeDate(String pathToDirectory, Date toDate) throws MyException {
    return this.returnFilesInDateInterval(pathToDirectory, null, toDate, false, true);
  }
}
