import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
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
////    local.deleteFileOrDirectory("cvele");
//    local.createFolderOnSpecifiedPath("cvele", "cvele2");
//    local.moveFileFromDirectoryToAnother("dest\\2.txt", "cvele\\cvele2");
//    local.moveFileFromDirectoryToAnother("dest\\1.txt", "cvele\\cvele2");
//    System.out.println(local.folderNameByFileName("2.txt"));
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


    //ODAVDE SAM JA TESTIRAO
    //A ovde ja
    /*LocalStorageImplementation storageSpecification=new LocalStorageImplementation();
    storageSpecification.setRootFolderPathInitialization("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation");
    storageSpecification.createRootFolder();
    storageSpecification.createFolderOnSpecifiedPath(".", "cvele");
    storageSpecification.createFolderOnSpecifiedPath("/cvele", "cvele2");
    storageSpecification.addLimitForFolder("cvele/cvele2", 12345);
    storageSpecification.addLimitForFolder("cvele", 54321);
    List<String> here = new ArrayList<>();
    here.add("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation\\p1\\1.txt");
    here.add("C:\\Users\\cvlad\\Desktop\\SKProjekat\\LocalStorageImplementation\\p1\\2.txt");
    storageSpecification.createFolderOnSpecifiedPath("", "dest");
    storageSpecification.createFolderOnSpecifiedPath("/dest", "ovde");
    storageSpecification.putFilesOnSpecifiedPath(here, "/dest/ovde");
    System.out.println(storageSpecification.folderNameByFileName("1.txt"));
    System.out.println(storageSpecification.folderNameByFileName("2.txt"));*/
//    storageSpecification.getConfiguration().setSize(2000);
//    List<String> list=new ArrayList<>();
//    list.add(".exe");
//    list.add(".pdf");
//    list.add(".docx");
//    Map<String,Integer> map=new HashMap<>();
//    map.put("/Zarko",2);
//    storageSpecification.getConfiguration().setForbiddenExtensions(list);
//    storageSpecification.getConfiguration().setNumberOfFilesInFolder(map);
//    storageSpecification.createRootFolder();
    //System.out.println(storageSpecification.getConfiguration().getSize());
//    System.out.println(storageSpecification.getConfiguration().toString());
//
//
//    System.out.println(storageSpecification.getRootFolderPath());
//    storageSpecification.createFolderOnSpecifiedPath("","Zarko1234");
//    List<String> fajlovi=new ArrayList<>();
//    fajlovi.add("C:\\Users\\mega\\Radna površina\\b.txt");
//    fajlovi.add("C:\\Users\\mega\\Radna površina\\Zadaci.docx");
//    fajlovi.add("C:\\Users\\mega\\Radna površina\\c.txt");
//    storageSpecification.putFilesOnSpecifiedPath(fajlovi,".");
//    storageSpecification.deleteFileOrDirectory("/Zarko1234");
//    storageSpecification.deleteFileOrDirectory("\\b.txt");
//    storageSpecification.moveFileFromDirectoryToAnother("/c.txt","/Zarko");
//    List<String> list=new ArrayList<>();
//    list.add(".txt");
//    Map<String,FileMetadata>map=storageSpecification.allFilesFromDirectoryAndSubdirectoryExt(".",list);
//    for(Map.Entry<String,FileMetadata> e:map.entrySet())
//    {
//      System.out.println(e.getKey());
//    }
  }

  private String getFullStoragePath(String path) {
    return this.turnSlashes(super.getRootFolderPath()) + "/" + this.storageName+(path.equals(".") ? "" : this.turnSlashes(path));
  }

  private void createConfigurationFile(String fullPath) throws MyException {
    fullPath = this.turnSlashes(fullPath);
    String destPath = fullPath + "/" + "configuration.txt";
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
    super.getConfiguration().setForbiddenExtensions(new ArrayList<>());
    super.getConfiguration().setNumberOfFilesInFolder(new HashMap<>());
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

@Override
  void createRootFolder() throws MyException {
    File rootFile = new File(super.getRootFolderPath() + "/" + this.storageName);
    if(rootFile.exists())
    {
      File[] files = Objects.requireNonNull(rootFile.listFiles());
      for (File f : files) {
        if (f.getName().equalsIgnoreCase("configuration.txt")) {
          try {
            this.setConfigurationFile(f);
            return;
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
  }

  @Override
  void setRootFolderPathInitialization(String s) throws MyException{
    s = this.turnSlashes(s);
    File file = new File(s);
    if (file.exists() && file.isDirectory()) {
      super.setRootFolderPath(s);
      return;
    }
    throw new MyException("Bad path");
  }

  String turnSlashes(String path) {
    if (path.contains("\\")) {
      String[] str = path.split("\\+");
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < str.length; i++) {
        if (i != str.length - 1) {
          stringBuilder.append(str[i]).append("/");
        }
        else {
          stringBuilder.append(str[i]);
        }
      }
      return stringBuilder.toString();
    }
    return path;
  }


  @Override
  void createFolderOnSpecifiedPath(String path, String name) throws MyException {
    File checkFile = new File(this.getFullStoragePath(path));
    if (!checkFile.exists()) {
      throw new MyException("Path doesn't exist in storage.");
    }
    if(checkFile.isFile())
    {
      throw new MyException("This is file not folder.");
    }
    String fullPath = this.getFullStoragePath(path) + "/" + name;
    File file = new File(fullPath);
    if(file.exists())
      throw new MyException("Folder already exist");
    boolean created = file.mkdir();
    if (!created) {
      throw new MyException("Error during creation of directory: " + name + ".");
    }
  }

  private void uploadFileToPath(String fileName, String path, boolean check) throws MyException { //FileName je fajl sa celom putanjom, path je putanja do odredjenog direktorijuma
    fileName = this.turnSlashes(fileName);
    path = this.turnSlashes(path);
    File srcDir = new File(path);
    if (!srcDir.exists()) {
      throw new MyException("Directory \"" + path + "\" doesn't exist.");
    }
    File source = new File(fileName);
    if (!source.exists() && !source.isFile()) {
      throw new MyException("Source file doesn't exist.");
    }
    String destPath = path + "/" + source.getName();  //OVO OVDE MOZE DA BUDE PROBLEM
    File dest = new File(destPath);
    if (check) {
      this.checkForUploadFileErrors(srcDir, source);
    }
    try {
      FileUtils.copyFile(source, dest);
    } catch (IOException e) {
      throw new MyException("Error while uploading files.");
    }
  }

  private String getRelativePath(String path) {
    path = this.turnSlashes(path);
    if (path.indexOf("Skladiste") + 10 < path.length()) {
      return path.substring(path.indexOf("Skladiste") + 10);
    }
    return "";
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

      if (ext.equalsIgnoreCase("."+FilenameUtils.getExtension(file.getName()))) {
        throw new MyException("This extension file is forbidden.");
      }
    }
    String relativePath = getRelativePath(source.getAbsolutePath());
    File relFile = new File(source.getAbsolutePath());
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
  void putFilesOnSpecifiedPath(List<String> listFiles, String path) throws MyException {
    StringBuilder sb = new StringBuilder();
    for (String filePath : listFiles) {
      try {
        filePath = this.turnSlashes(filePath);
        this.uploadFileToPath(filePath, this.getFullStoragePath(path), true);
      } catch (MyException exc) {
        sb.append(exc);
      }
    }
    if (sb.length() > 0) {
      throw new MyException(sb.toString());
    }
  }

  private boolean isPathInStorage(String path) {
    return (path.toLowerCase().contains("skladiste"));
  }

  @Override
  void deleteFileOrDirectory(String path) throws MyException {
    if (path.equals(this.getFullStoragePath(path))) {
      throw new MyException("Cannot delete root folder");
    }
    String fullPath = this.getFullStoragePath(path);
    File file = new File(fullPath);
    if (!file.delete()) {
      throw new MyException("Failed to delete the file.");
    }
  }

  @Override
  void moveFileFromDirectoryToAnother(String filePath, String pathTo) throws MyException {
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

    fullPathTo += "/" + file.getName();
    fileTo = new File(fullPathTo);

    if (!file.renameTo(fileTo)) {
      throw new MyException("Error while moving a file.");
    }
  }

  @Override
  void downloadFileOrDirectory(String pathFrom, String pathTo) throws MyException {
    pathTo = this.turnSlashes(pathTo);
    pathFrom = this.turnSlashes(pathFrom);
    if (this.isPathInStorage(pathTo)) {
      throw new MyException("Path to is in storage.");
    }

    String fullPathFrom = this.getFullStoragePath(pathFrom);
    File file = new File(fullPathFrom);
    if (!file.exists()) {
      throw new MyException("Path from is in not correct.");
    }
    try {
      this.uploadFileToPath(fullPathFrom, pathTo, false);
    } catch (MyException e) {
      throw new MyException(e.getMessage());
    }
  }

  @Override
  void renameFileOrDirectory(String path, String nameAfter) throws MyException {
    String fullPath = this.getFullStoragePath(path);
    File oldFile = new File(fullPath);
    if (this.isPathInStorage(path)) {
      throw new MyException("Path to is in storage.");
    }
    if (!oldFile.exists()) {
      throw new MyException(path + " does not exist.");
    }
    String newPath = oldFile.getParent() + "/" + nameAfter;
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
    fullPath=this.turnSlashes(fullPath);
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
    fullPath = this.turnSlashes(fullPath);
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

  // returns null if path is invalid, returns "No files exist",
  // returns "Found files are: test.txt image.jpg"
  @Override
  String doesDirectoryContainFiles(String pathToFolder, List<String> namesOfFiles) {
    String fullPath = this.getFullStoragePath(pathToFolder);
    File sourceFolder = new File(fullPath);
    if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
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

  private String searchForFile(String path, String fileName, String returnPath) {
    path = this.turnSlashes(path);
    File sourceFolder = new File(path);
    if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
      return null;
    }
    File[] files = Objects.requireNonNull(new File(path).listFiles());
    for (File f : files) {
      if (f.isDirectory()) {
        String result = searchForFile(f.getAbsolutePath(), fileName, returnPath + "/" + f.getName());
        if (result != null) {
          return result;
        }
      } else {
        if (f.getName().equalsIgnoreCase(fileName)) {
          return returnPath;
        }
      }
    }
    return null;
  }

  @Override
  String folderNameByFileName(String fileName) throws MyException {
    return this.searchForFile(this.getFullStoragePath("."), fileName, this.storageName);
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
    pathToDirectory = this.getFullStoragePath(pathToDirectory);
    return this.returnFilesInDateInterval(pathToDirectory, fromDate, toDate, true, false);
  }

  @Override
  Map<String, FileMetadata> returnModifiedFilesInDateInterval(
          String pathToDirectory, Date fromDate, Date toDate) throws MyException {
    pathToDirectory = this.getFullStoragePath(pathToDirectory);
    return this.returnFilesInDateInterval(pathToDirectory, fromDate, toDate, false, false);
  }

  @Override
  Map<String, FileMetadata> returnModifiedFilesFromDate(String pathToDirectory, Date fromDate) throws MyException {
    pathToDirectory = this.getFullStoragePath(pathToDirectory);
    return this.returnFilesInDateInterval(pathToDirectory, fromDate, null, false, true);
  }

  @Override
  Map<String, FileMetadata> returnModifiedFilesBeforeDate(String pathToDirectory, Date toDate) throws MyException {
    pathToDirectory = this.getFullStoragePath(pathToDirectory);
    return this.returnFilesInDateInterval(pathToDirectory, null, toDate, false, true);
  }

  @Override
  void addLimitForFolder(String path, int number) throws MyException {
    String filePath = this.turnSlashes(path);
    String confPath = super.getRootFolderPath() + "/" + this.storageName + "/" + "configuration.txt";
    File confFile = new File(this.turnSlashes(confPath));
    if (!confFile.exists()) {
      throw new MyException("Configuration file not found.");
    }
    try {
      FileWriter myWriter = new FileWriter(confPath, true);
      myWriter.write(path + " " + number + "\n");
      super.getConfiguration().getNumberOfFilesInFolder().put(filePath, number);
      myWriter.close();
    } catch (IOException e) {
      throw new MyException("An error occurred while writing in configuration file.");
    }
  }
}
