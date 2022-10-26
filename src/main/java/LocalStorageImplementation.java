import java.io.File;
import java.util.HashMap;
import java.util.List;

public class LocalStorageImplementation extends StorageSpecification {
  public static void main(String[] args) {

  }

  @Override
  void createRootFolder(Configuration configuration) {

  }

  @Override
  void setConfigurationSizeOfStorage(int i) {

  }

  @Override
  void setConfigurationExtensions(List<String> list) {

  }

  @Override
  void setConfigurationNumberOfFiles(int i) {

  }

  @Override
  void createFolderOnSpecifiedPath(String s) {

  }

  @Override
  void putFileOnSpecifiedPath(List<File> list, String s) {

  }

  @Override
  void deleteFileOrDirectory(String s) {

  }

  @Override
  void deleteFileOrDirectory(String s, String s1) {

  }

  @Override
  void moveFileFromDirectoryToAnother(String s, String s1, String s2) {

  }

  @Override
  void downloadFileOrDirectory(String s, String s1) {

  }

  @Override
  void renameFileOrDirectory(String s, String s1, String s2) {

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
